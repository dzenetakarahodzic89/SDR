package ba.com.zira.sdr.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ba.com.zira.commons.configuration.ZiraOperationCustomizer;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfiguration {

    private final ZiraOperationCustomizer ziraOperationCustomizer;

    public SwaggerConfiguration(final ZiraOperationCustomizer ziraOperationCustomizer) {
        super();
        this.ziraOperationCustomizer = ziraOperationCustomizer;
    }

    @Bean
    public GroupedOpenApi playlistGAApi() {
        return GroupedOpenApi.builder().group("playlistga-api").packagesToScan("ba.com.zira.sdr.playlistga.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi eventApi() {
        return GroupedOpenApi.builder().group("event-api").packagesToScan("ba.com.zira.sdr.event.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi languageApi() {
        return GroupedOpenApi.builder().group("language-api").packagesToScan("ba.com.zira.sdr.language.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi instrumentApi() {
        return GroupedOpenApi.builder().group("instrument-api").packagesToScan("ba.com.zira.sdr.instrument.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi audioDBIntegrationApi() {
        return GroupedOpenApi.builder().group("audioDBIntegration-api").packagesToScan("ba.com.zira.sdr.audiodbintegration.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi personApi() {
        return GroupedOpenApi.builder().group("person-api").packagesToScan("ba.com.zira.sdr.person.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi commentApi() {
        return GroupedOpenApi.builder().group("comment-api").packagesToScan("ba.com.zira.sdr.comment.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi songInstrumentApi() {
        return GroupedOpenApi.builder().group("songInstrument-api").packagesToScan("ba.com.zira.sdr.songinstrument.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi noteSheetApi() {
        return GroupedOpenApi.builder().group("notesheet-api").packagesToScan("ba.com.zira.sdr.notesheet.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi labelApi() {
        return GroupedOpenApi.builder().group("label-api").packagesToScan("ba.com.zira.sdr.label.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi albumApi() {
        return GroupedOpenApi.builder().group("album-api").packagesToScan("ba.com.zira.sdr.album.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi genreApi() {
        return GroupedOpenApi.builder().group("genre-api").packagesToScan("ba.com.zira.sdr.genre.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi songfftresultApi() {
        return GroupedOpenApi.builder().group("songfftresult-api").packagesToScan("ba.com.zira.sdr.songfftresult.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi chordProgressionApi() {
        return GroupedOpenApi.builder().group("chordprogression-api").packagesToScan("ba.com.zira.sdr.chordprogression.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi moritsIntegrationApi() {
        return GroupedOpenApi.builder().group("moritsintegration-api").packagesToScan("ba.com.zira.sdr.moritsintegration.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi playlistApi() {
        return GroupedOpenApi.builder().group("playlist-api").packagesToScan("ba.com.zira.sdr.playlist.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi multisearchApi() {
        return GroupedOpenApi.builder().group("multisearch-api").packagesToScan("ba.com.zira.sdr.multisearch.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi artistSongApi() {
        return GroupedOpenApi.builder().group("songartist-api").packagesToScan("ba.com.zira.sdr.songartist.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi sampleApi() {
        return GroupedOpenApi.builder().group("sample-api").packagesToScan("ba.com.zira.sdr.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi userRecommendationApi() {
        return GroupedOpenApi.builder().group("userrecommendation-api").packagesToScan("ba.com.zira.sdr.userrecommendation.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi countryApi() {
        return GroupedOpenApi.builder().group("country-api").packagesToScan("ba.com.zira.sdr.country.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi lyricApi() {
        return GroupedOpenApi.builder().group("lyric-api").packagesToScan("ba.com.zira.sdr.lyric.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi spotifyIntegrationApi() {
        return GroupedOpenApi.builder().group("spotify-integration-api").packagesToScan("ba.com.zira.sdr.spotifyintegration.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi shazamIntegrationApi() {
        return GroupedOpenApi.builder().group("shazam-integration-api").packagesToScan("ba.com.zira.sdr.shazamintegration.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi connectedMediaApi() {
        return GroupedOpenApi.builder().group("connectedmedia-api").packagesToScan("ba.com.zira.sdr.connectedmedia.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi mediaApi() {
        return GroupedOpenApi.builder().group("media-api").packagesToScan("ba.com.zira.sdr.media.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi artistApi() {
        return GroupedOpenApi.builder().group("artist-api").packagesToScan("ba.com.zira.sdr.artist.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi artistPersonApi() {
        return GroupedOpenApi.builder().group("personartist-api").packagesToScan("ba.com.zira.sdr.personartist.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi songApi() {
        return GroupedOpenApi.builder().group("song-api").packagesToScan("ba.com.zira.sdr.song.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi songSimilarityApi() {
        return GroupedOpenApi.builder().group("songSimilarity-api").packagesToScan("ba.com.zira.sdr.songsimilarity.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi songSimilarityDetailApi() {
        return GroupedOpenApi.builder().group("songSimilarityDetail-api").packagesToScan("ba.com.zira.sdr.songsimilaritydetail.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi deezerIntegrationApi() {
        return GroupedOpenApi.builder().group("deezer-integration-api").packagesToScan("ba.com.zira.sdr.deezerintegration.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi userRecommendationDetailApi() {
        return GroupedOpenApi.builder().group("userrecommendationdetail-api")
                .packagesToScan("ba.com.zira.sdr.userrecommendationdetail.rest").addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi multiSearchHistoryApi() {
        return GroupedOpenApi.builder().group("multi-search-history-api").packagesToScan("ba.com.zira.sdr.multisearchhistory.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi connectedMediaDetailApi() {
        return GroupedOpenApi.builder().group("connectedmediadetail-api").packagesToScan("ba.com.zira.sdr.connectedmediadetail.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi playlistGenerateApi() {
        return GroupedOpenApi.builder().group("playlist-generate-api").packagesToScan("ba.com.zira.sdr.generateplaylist.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();

    }

    @Bean
    public GroupedOpenApi eraApi() {
        return GroupedOpenApi.builder().group("era-api").packagesToScan("ba.com.zira.sdr.era.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();

    }

    @Bean
    public GroupedOpenApi fileUploadSegmentApi() {
        return GroupedOpenApi.builder().group("file-upload-segment").packagesToScan("ba.com.zira.sdr.fileuploadsegment.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi songPlaylistApi() {
        return GroupedOpenApi.builder().group("songplaylist-api").packagesToScan("ba.com.zira.sdr.songplaylist.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi itunesIntegrationApi() {
        return GroupedOpenApi.builder().group("itunesintegration-api").packagesToScan("ba.com.zira.sdr.itunesintegration.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi newsArticlesApi() {
        return GroupedOpenApi.builder().group("newsarticles-api").packagesToScan("ba.com.zira.sdr.newsarticles.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi battleApi() {
        return GroupedOpenApi.builder().group("battle-api").packagesToScan("ba.com.zira.sdr.battle.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi battleTurnApi() {
        return GroupedOpenApi.builder().group("battle-turn-api").packagesToScan("ba.com.zira.sdr.battleturn.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi countryRelationApi() {
        return GroupedOpenApi.builder().group("countryrelation-api").packagesToScan("ba.com.zira.sdr.countryrelation.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi releaseApi() {
        return GroupedOpenApi.builder().group("release-api").packagesToScan("ba.com.zira.sdr.release.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi chatApi() {
        return GroupedOpenApi.builder().group("chat-api").packagesToScan("ba.com.zira.sdr.chat.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public OpenAPI ziraOpenAPI() {
        return new OpenAPI().info(new Info().title("ZIRA API").description("ZIRA API").version("0.0.1-SNAPSHOT")
                .contact(new Contact().name("ZIRA").url("http://www.zira.com.ba").email("info@zira.com.ba"))
                .license(new License().name("ZIRA proprietary service").url("http://www.zira.com.ba/")));
    }

}
