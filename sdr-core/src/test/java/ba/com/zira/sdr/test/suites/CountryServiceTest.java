package ba.com.zira.sdr.test.suites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.validation.RequestValidator;
import ba.com.zira.sdr.api.CountryService;
import ba.com.zira.sdr.api.model.country.CountryCreateRequest;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.country.CountryUpdateRequest;
import ba.com.zira.sdr.core.impl.CountryServiceImpl;
import ba.com.zira.sdr.core.mapper.CountryMapper;
import ba.com.zira.sdr.core.validation.CountryRequestValidation;
import ba.com.zira.sdr.dao.CountryDAO;
import ba.com.zira.sdr.dao.model.CountryEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class CountryServiceTest extends BasicTestConfiguration {

    @Autowired
    private CountryMapper countryMapper;

    private CountryDAO countryDAO;
    private RequestValidator requestValidator;
    private CountryRequestValidation countryRequestValidation;
    private CountryService countryService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.countryDAO = Mockito.mock(CountryDAO.class);
        this.countryRequestValidation = Mockito.mock(CountryRequestValidation.class);
        this.countryService = new CountryServiceImpl(countryDAO, countryMapper, countryRequestValidation);
    }

    @Test(enabled = true)
    public void testFindCountry() {
        try {

            List<CountryEntity> entities = new ArrayList<>();
            CountryEntity firstCountryEntity = new CountryEntity();
            firstCountryEntity.setFlagAbbriviation("Test Information");
            firstCountryEntity.setName("Test Type 1");

            CountryEntity secondCountryEntity = new CountryEntity();
            firstCountryEntity.setFlagAbbriviation("Test Information");
            firstCountryEntity.setName("Test Type 2");

            CountryEntity thirdCountryEntity = new CountryEntity();
            firstCountryEntity.setFlagAbbriviation("Test Information");
            firstCountryEntity.setName("Test Type 3");

            entities.add(firstCountryEntity);
            entities.add(secondCountryEntity);
            entities.add(thirdCountryEntity);

            PagedData<CountryEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<CountryResponse> response = new ArrayList<>();

            CountryResponse firstResponse = new CountryResponse();
            firstResponse.setFlagAbbriviation("Test Information");
            firstResponse.setName("Test Type 1");

            CountryResponse secondResponse = new CountryResponse();
            firstResponse.setFlagAbbriviation("Test Information");
            firstResponse.setName("Test Type 2");

            CountryResponse thirdResponse = new CountryResponse();
            firstResponse.setFlagAbbriviation("Test Information");
            firstResponse.setName("Test Type 3");

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<CountryResponse> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(countryDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<CountryResponse> countryFindResponse = countryService.get(filterRequest).getPayload();

            Assertions.assertThat(countryFindResponse).as("Check all elements").overridingErrorMessage("All elements should be equal.")
                    .hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateCountry() {
        try {

            EntityRequest<CountryCreateRequest> req = new EntityRequest<>();

            var newCountryRequest = new CountryCreateRequest();
            newCountryRequest.setFlagAbbriviation("Test Information");
            newCountryRequest.setName("Test Type 1");
            newCountryRequest.setRegion("Region 1");

            req.setEntity(newCountryRequest);

            var newCountryEnt = new CountryEntity();
            newCountryEnt.setFlagAbbriviation("Test Information");
            newCountryEnt.setName("Test Type 1");
            newCountryEnt.setRegion("Region 1");

            var newCountry = new CountryResponse();
            newCountry.setFlagAbbriviation("Test Information");
            newCountry.setName("Test Type 1");
            newCountry.setRegion("Region 1");
            newCountry.setStatus(Status.ACTIVE.getValue());

            Mockito.when(countryDAO.persist(newCountryEnt)).thenReturn(null);

            PayloadResponse<CountryResponse> countryFindResponse = countryService.create(req);

            Assertions.assertThat(countryFindResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy", "imageUrl").isEqualTo(newCountry);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdateCountry() {
        try {

            EntityRequest<CountryUpdateRequest> request = new EntityRequest<>();

            CountryEntity countryEntity = new CountryEntity();
            countryEntity.setName("Old Test Name 1");
            countryEntity.setFlagAbbriviation("Old Test Information");

            CountryResponse countryResponse = new CountryResponse();
            countryResponse.setName("Update Test Name 1");
            countryResponse.setFlagAbbriviation("Update Test Information");

            CountryUpdateRequest updateCountryRequest = new CountryUpdateRequest();
            updateCountryRequest.setName("Update Test Name 1");
            updateCountryRequest.setFlagAbbriviation("Update Test Information");
            request.setEntity(updateCountryRequest);

            Mockito.when(countryRequestValidation.validateUpdateCountryRequest(request)).thenReturn(null);

            Mockito.when(countryDAO.findByPK(request.getEntity().getId())).thenReturn(countryEntity);

            Mockito.doNothing().when(countryDAO).merge(countryEntity);

            var countryUpdateResponse = countryService.update(request);
            Assertions.assertThat(countryUpdateResponse.getPayload()).as("Check all fields")
                    .overridingErrorMessage("All fields should be equal.").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(countryResponse);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeleteCountry() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(requestValidator.validate(req)).thenReturn(null);

            Mockito.doNothing().when(countryDAO).removeByPK(req.getEntity());

            var countryFindResponse = countryService.delete(req);

            Assertions.assertThat(countryFindResponse.getPayload()).isEqualTo("Country removed successfully!");

        } catch (Exception e) {
            Assert.fail();
        }
    }

}
