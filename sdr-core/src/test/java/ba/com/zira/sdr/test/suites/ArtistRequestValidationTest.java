package ba.com.zira.sdr.test.suites;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.sdr.core.validation.ArtistValidation;
import ba.com.zira.sdr.dao.ArtistDAO;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

public class ArtistRequestValidationTest extends BasicTestConfiguration {
    private static final String TEMPLATE_CODE = "TEST_1";

    private ArtistDAO atistDAO;
    private ArtistValidation validation;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.atistDAO = Mockito.mock(ArtistDAO.class);
        this.validation = new ArtistValidation(atistDAO);
    }

}
