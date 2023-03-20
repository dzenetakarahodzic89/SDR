package ba.com.zira.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.jmusixmatch.MusixMatch;
import org.jmusixmatch.MusixMatchException;
import org.jmusixmatch.entity.lyrics.Lyrics;
import org.jmusixmatch.entity.track.Track;
import org.jmusixmatch.entity.track.TrackData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.JsonSyntaxException;

import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.MusicMatchService;
import ba.com.zira.sdr.api.enums.MusicMatchStatus;
import ba.com.zira.sdr.core.mapper.LyricMapper;
import ba.com.zira.sdr.dao.LyricDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.model.ArtistEntity;
import ba.com.zira.sdr.dao.model.LanguageEntity;
import ba.com.zira.sdr.dao.model.LyricEntity;
import ba.com.zira.sdr.dao.model.SongArtistEntity;
import ba.com.zira.sdr.dao.model.SongEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MusicMatchServiceImpl implements MusicMatchService {
    @Autowired
    private SongDAO songDAO;
    @Autowired
    private LyricDAO lyricDAO;
    @Autowired
    LyricMapper lyricMapper;

    private final MusixMatch musixMatch;
    private static final Logger LOGGER = LoggerFactory.getLogger(MusicMatchServiceImpl.class);

    public MusicMatchServiceImpl() {
        String apiKey = "be3d1d0919c2c7026f62f21e97058587";
        this.musixMatch = new MusixMatch(apiKey);
    }

    @Scheduled(cron = "0 0 * * * *")
    public void createLyricsScheduled() throws MusixMatchException {
        var request = new EmptyRequest();
        insertLyric(request);
    }

    @Override
    public PayloadResponse<String> insertLyric(EmptyRequest request) throws MusixMatchException {
        List<SongEntity> songList = songDAO.getSongsForMusicMatch();
        int counter = 0;
        for (SongEntity song : songList) {
            try {
                List<SongArtistEntity> songArtists = song.getSongArtists();
                String artistName = null;
                for (SongArtistEntity songArtist : songArtists) {
                    ArtistEntity artist = songArtist.getArtist();
                    artistName = artist.getName();
                }
                Track track;
                track = musixMatch.getMatchingTrack(song.getName(), artistName);
                TrackData data = track.getTrack();
                int trackID = data.getTrackId();
                Lyrics lyrics = musixMatch.getLyrics(trackID);

                var lyricEntity = new LyricEntity();

                lyricEntity.setStatus(Status.ACTIVE.value());
                lyricEntity.setCreated(LocalDateTime.now());
                lyricEntity.setCreatedBy("AutoInserted");
                lyricEntity.setModified(LocalDateTime.now());
                lyricEntity.setText(lyrics.getLyricsBody());
                var language = new LanguageEntity();
                language.setName(lyrics.getLyricsLang());
                language.setId(1L);
                lyricEntity.setLanguage(language);
                lyricEntity.setSong(song);

                SongEntity songEntity = songDAO.findByPK(song.getId());
                songEntity.setMusicMatchStatus(MusicMatchStatus.COMPLETED.getValue());

                lyricDAO.persist(lyricEntity);
                songDAO.merge(songEntity);
                counter++;

            } catch (MusixMatchException e) {
                LOGGER.error("{} <-- This song doesn't exist in MusicMatch db", song.getName());
                SongEntity songEntity = songDAO.findByPK(song.getId());
                songEntity.setMusicMatchStatus(MusicMatchStatus.NOTFOUND.getValue());
                songDAO.merge(songEntity);

            } catch (JsonSyntaxException e) {
                LOGGER.error("Error processing JSON response for {}: {}", song.getName(), e.getMessage());
                SongEntity songEntity = songDAO.findByPK(song.getId());
                songEntity.setMusicMatchStatus(MusicMatchStatus.FORMATNOTSUPPORTED.getValue());
                songDAO.merge(songEntity);
            }
        }
        LOGGER.info("Successfully inserted lyric for {} Songs", counter);

        return new PayloadResponse<>(request, ResponseCode.OK, "Successfull inserted lyric for " + counter + " Songs");
    }
}