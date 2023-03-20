package ba.com.zira.sdr.core.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.PlaylistGAService;
import ba.com.zira.sdr.api.enums.ServiceType;
import ba.com.zira.sdr.api.model.playlistga.PlaylistRequestGA;
import ba.com.zira.sdr.api.model.playlistga.PlaylistResponseGA;
import ba.com.zira.sdr.api.model.playlistga.WeightGenerator;
import ba.com.zira.sdr.core.mapper.SongScoreMapper;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.SongScoreDAO;
import ba.com.zira.sdr.dao.model.SongScoresEntity;
import ba.com.zira.sdr.ga.impl.GeneticAlgorithmImpl;
import ba.com.zira.sdr.ga.impl.LinearGenerator;
import ba.com.zira.sdr.ga.impl.PlaylistChromosome;
import ba.com.zira.sdr.ga.impl.PopulationImpl;
import ba.com.zira.sdr.ga.impl.Randomizer;
import ba.com.zira.sdr.ga.impl.SongGene;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PlaylistGAServiceImpl implements PlaylistGAService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaylistGAServiceImpl.class);
    SongScoreDAO songScoreDAO;
    SongDAO songDAO;
    SongScoreMapper songScoreMapper;

    @Override
    public PagedPayloadResponse<PlaylistResponseGA> generatePlaylist(final EntityRequest<PlaylistRequestGA> request) throws ApiException {
        var parameters = request.getEntity();
        var allValidSongScores = songScoreDAO.findSongScoresByGenreIdArray(parameters.getGenrePriorities());
        var songNamesMap = songDAO.getSongNames(allValidSongScores.stream().map(SongScoresEntity::getSongId).collect(Collectors.toList()));

        var allValidSongGenes = allValidSongScores.stream()
                .map(score -> generateSongGene(score, songNamesMap.get(score.getSongId()), parameters)).collect(Collectors.toList());

        var population = new PopulationImpl();

        // creating first population
        for (var i = 0; i < parameters.getPopulationSize(); i++) {
            var playlistChromosome = generateRandomPlaylistChromosome(allValidSongGenes, parameters);
            population.addChromosome(playlistChromosome);
        }

        population.setMaxPlaytimeInSeconds(parameters.getTotalPlaytime());
        population.setGenreIdWeights(generateGenreIdWeights(parameters.getGenrePriorities(), new LinearGenerator()));
        population.setServiceWeights(generateServiceWeights(parameters.getServicePriorities(), new LinearGenerator()));

        var geneticAlgorithm = new GeneticAlgorithmImpl(parameters);
        geneticAlgorithm.setAllValidGenes(allValidSongGenes);

        for (var i = 0; i < parameters.getNumberOfGenerations(); i++) {
            population.calculateFitnessForAllChromosomes();

            LOGGER.info("Iteracija: ");
            LOGGER.info(String.valueOf(i));
            LOGGER.info("Fitness optimalnog elementa za ovu iteraciju:");

            var currentOptimalChromosome = (PlaylistChromosome) population.findFittestChromosome();

            LOGGER.info(String.valueOf(currentOptimalChromosome.getFitness()));

            population = (PopulationImpl) geneticAlgorithm.reproduction(population);
        }

        population.calculateFitnessForAllChromosomes();

        PagedData<SongGene> responseData = new PagedData<>();
        responseData.setRecords(((PlaylistChromosome) population.findFittestChromosome()).getGenes());

        LOGGER.info("Naj-fit kromosom je: ");
        LOGGER.info(((PlaylistChromosome) population.findFittestChromosome()).toString());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, responseData, songScoreMapper::entitiesToDtos);
    }

    private PlaylistChromosome generateRandomPlaylistChromosome(List<SongGene> allValidSongGenes, PlaylistRequestGA params) {
        // generate numberOfGenes random numbers, between 0 and
        // allValidSongScores.size
        var randomPlaylistChromosome = new PlaylistChromosome();

        for (var i = 0; i < params.getNumberOfGenes(); i++) {
            var validSongGenesIndex = Randomizer.getRandomNumber(0, allValidSongGenes.size());
            while (Boolean.TRUE.equals(
                    PlaylistChromosome.geneExistsInChromosome(randomPlaylistChromosome, allValidSongGenes.get(validSongGenesIndex)))) {
                validSongGenesIndex = Randomizer.getRandomNumber(0, allValidSongGenes.size());
            }
            var songGene = allValidSongGenes.get(validSongGenesIndex);
            randomPlaylistChromosome.addGene(songGene);
        }

        return randomPlaylistChromosome;
    }

    private SongGene generateSongGene(SongScoresEntity songScore, String songName, PlaylistRequestGA params) {
        var songGene = new SongGene();

        songGene.setGenreId(songScore.getGenre().getId());
        songGene.setGenreName(songScore.getGenre().getName());
        songGene.setSongId(songScore.getSongId());
        songGene.setSongName(songName);
        songGene.setPlaytimeInSeconds(songScore.getPlaytimeInSeconds());

        songGene.setServiceScores(generateServiceScores(songScore));
        return songGene;
    }

    private Map<Long, Double> generateGenreIdWeights(List<Long> genrePriorities, WeightGenerator weightGenerator) {
        HashMap<Long, Double> genreIdWeights = new HashMap<>();
        var weights = weightGenerator.generate(genrePriorities.size());

        for (var i = 0; i < genrePriorities.size(); i++) {
            genreIdWeights.put(genrePriorities.get(i), weights.get(i));
        }

        return genreIdWeights;
    }

    private Map<ServiceType, Double> generateServiceWeights(List<ServiceType> servicePriorities, WeightGenerator weightGenerator) {
        HashMap<ServiceType, Double> serviceWeights = new HashMap<>();
        var weights = weightGenerator.generate(servicePriorities.size());

        for (var i = 0; i < servicePriorities.size(); i++) {
            serviceWeights.put(servicePriorities.get(i), weights.get(i));
        }

        return serviceWeights;
    }

    private Map<ServiceType, Double> generateServiceScores(SongScoresEntity songScore) {
        var serviceScores = new HashMap<ServiceType, Double>();
        serviceScores.put(ServiceType.DEEZER, songScore.getDeezerScore().doubleValue());
        serviceScores.put(ServiceType.GOOGLE_PLAY, songScore.getGooglePlayScore().doubleValue());
        serviceScores.put(ServiceType.ITUNES, songScore.getItunesScore().doubleValue());
        serviceScores.put(ServiceType.SDR, songScore.getSdrScore().doubleValue());
        serviceScores.put(ServiceType.SPOTIFY, songScore.getSpotifyScore().doubleValue());
        serviceScores.put(ServiceType.TIDAL, songScore.getTidalScore().doubleValue());
        serviceScores.put(ServiceType.YT_MUSIC, songScore.getYoutubeMusicScore().doubleValue());
        return serviceScores;
    }

}
