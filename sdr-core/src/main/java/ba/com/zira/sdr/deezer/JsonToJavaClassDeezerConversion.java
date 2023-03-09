package ba.com.zira.sdr.deezer;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.codemodel.JCodeModel;

public class JsonToJavaClassDeezerConversion {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonToJavaClassDeezerConversion.class);

    public static void main(final String[] args) {
        String packageName = "ba.com.zira.sdr.deezer";
        String basePath = "src/main/resources/ba/com/zira/sdr/deezer";
        String baseClassPath = "src/main/java";

        File artistSearchJson = new File(basePath + File.separator + "artistSearch.json");
        File outputArtistSearchDirectory = new File(baseClassPath + File.separator + "deezerArtistSearchResponse");
        outputArtistSearchDirectory.mkdirs();

        try {

            new JsonToJavaClassDeezerConversion().convertJsonToJavaClass(artistSearchJson.toURI().toURL(), outputArtistSearchDirectory,
                    packageName, artistSearchJson.getName().replace(".json", ""));

        } catch (IOException e) {
            LOGGER.error("Encountered issue while converting to pojo: {} ", e.getMessage());
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
