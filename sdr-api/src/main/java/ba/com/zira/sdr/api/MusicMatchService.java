package ba.com.zira.sdr.api;

import org.jmusixmatch.MusixMatchException;

import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.PayloadResponse;

public interface MusicMatchService {

    // Insert lyric from MusicMatch - api
    PayloadResponse<String> insertLyric(EmptyRequest request) throws MusixMatchException;

}
