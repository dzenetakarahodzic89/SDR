package ba.com.zira.sdr.spotify;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;

import com.sun.codemodel.JCodeModel;

public class JsonToJavaClassConversion {

    public static void main(final String[] args) {
        String packageName = "ba.com.zira.sdr.spotify";
        String basePath = "src/main/resources/ba/com/zira/sdr/spotify";
        String baseClassPath = "src/main/java";

        File albumSearchJson = new File(basePath + File.separator + "albumSearch.json");
        File outputAlbumSearchDirectory = new File(baseClassPath + File.separator + "SpotifyAlbumSearchResponse");
        outputAlbumSearchDirectory.mkdirs();

        File artistSearchJson = new File(basePath + File.separator + "artistSearch.json");
        File outputArtistSearchDirectory = new File(baseClassPath + File.separator + "SpotifyArtistSearchResponse");
        outputArtistSearchDirectory.mkdirs();

        File trackSearchJson = new File(basePath + File.separator + "trackSearch.json");
        File outputTrackSearchDirectory = new File(baseClassPath + File.separator + "SpotifySongSearchResponse");
        outputTrackSearchDirectory.mkdirs();

        try {
            new JsonToJavaClassConversion().convertJsonToJavaClass(albumSearchJson.toURI().toURL(), outputAlbumSearchDirectory, packageName,
                    albumSearchJson.getName().replace(".json", ""));
            new JsonToJavaClassConversion().convertJsonToJavaClass(artistSearchJson.toURI().toURL(), outputArtistSearchDirectory,
                    packageName, artistSearchJson.getName().replace(".json", ""));
            new JsonToJavaClassConversion().convertJsonToJavaClass(trackSearchJson.toURI().toURL(), outputTrackSearchDirectory, packageName,
                    trackSearchJson.getName().replace(".json", ""));
        } catch (IOException e) {
            System.out.println("Encountered issue while converting to pojo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void convertJsonToJavaClass(URL inputJsonUrl, File outputJavaClassDirectory, String packageName, String javaClassName)
            throws IOException {
        JCodeModel jcodeModel = new JCodeModel();

        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() {
                return true;
            }

            @Override
            public SourceType getSourceType() {
                return SourceType.JSON;
            }
        };

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()),
                new SchemaGenerator());
        mapper.generate(jcodeModel, javaClassName, packageName, inputJsonUrl);

        jcodeModel.build(outputJavaClassDirectory);
    }

}
