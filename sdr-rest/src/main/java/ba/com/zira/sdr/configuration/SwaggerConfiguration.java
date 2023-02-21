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
    public GroupedOpenApi sampleApi() {
        return GroupedOpenApi.builder().group("sample-api").packagesToScan("ba.com.zira.sdr.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi commentApi() {
        return GroupedOpenApi.builder().group("comment-api").packagesToScan("ba.com.zira.sdr.comment.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi genreApi() {
        return GroupedOpenApi.builder().group("genre-api").packagesToScan("ba.com.zira.sdr.genre.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi albumApi() {
        return GroupedOpenApi.builder().group("album-api").packagesToScan("ba.com.zira.sdr.album.rest")
                .addOperationCustomizer(ziraOperationCustomizer).build();
    }

    @Bean
    public GroupedOpenApi labelApi() {
        return GroupedOpenApi.builder().group("label-api").packagesToScan("ba.com.zira.sdr.label.rest")
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
    public OpenAPI ziraOpenAPI() {
        return new OpenAPI().info(new Info().title("ZIRA API").description("ZIRA API").version("0.0.1-SNAPSHOT")
                .contact(new Contact().name("ZIRA").url("http://www.zira.com.ba").email("info@zira.com.ba"))
                .license(new License().name("ZIRA proprietary service").url("http://www.zira.com.ba/")));
    }

}