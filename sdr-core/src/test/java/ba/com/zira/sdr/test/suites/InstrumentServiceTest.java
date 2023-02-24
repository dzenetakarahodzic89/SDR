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
import ba.com.zira.sdr.api.InstrumentService;
import ba.com.zira.sdr.api.MediaService;
import ba.com.zira.sdr.api.instrument.InstrumentCreateRequest;
import ba.com.zira.sdr.api.instrument.InstrumentResponse;
import ba.com.zira.sdr.api.instrument.InstrumentUpdateRequest;
import ba.com.zira.sdr.core.impl.InstrumentServiceImpl;
import ba.com.zira.sdr.core.mapper.InstrumentMapper;
import ba.com.zira.sdr.core.mapper.SongInstrumentMapper;
import ba.com.zira.sdr.core.utils.LookupService;
import ba.com.zira.sdr.core.validation.InstrumentRequestValidation;
import ba.com.zira.sdr.dao.InstrumentDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import ba.com.zira.sdr.dao.SongDAO;
import ba.com.zira.sdr.dao.SongInstrumentDAO;
import ba.com.zira.sdr.dao.model.InstrumentEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class InstrumentServiceTest extends BasicTestConfiguration {
    private InstrumentDAO instrumentDAO;

    @Autowired
    private InstrumentMapper instrumentMapper;

    private SongInstrumentMapper songInstrumentMapper;

    private RequestValidator requestValidator;

    private InstrumentRequestValidation instrumentRequestValidation;
    private InstrumentService instrumentService;
    private SongInstrumentDAO songInstrumentDAO;
    private SongDAO songDAO;
    private PersonDAO personDAO;
    // new
    private MediaService mediaService;
    private LookupService lookupService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.instrumentDAO = Mockito.mock(InstrumentDAO.class);
        this.lookupService = Mockito.mock(LookupService.class);
        this.mediaService = Mockito.mock(MediaService.class);

        this.instrumentRequestValidation = Mockito.mock(InstrumentRequestValidation.class);
        this.instrumentService = new InstrumentServiceImpl(instrumentDAO, songInstrumentDAO, personDAO, songDAO, instrumentMapper,
                songInstrumentMapper, instrumentRequestValidation);
    }

    @Test(enabled = true)
    public void testFindInstrument() {
        try {

            List<InstrumentEntity> entities = new ArrayList<>();
            InstrumentEntity firstInstrumentEntity = new InstrumentEntity();
            firstInstrumentEntity.setInformation("Information about instrument");
            firstInstrumentEntity.setName("Instrument name");
            firstInstrumentEntity.setStatus(Status.ACTIVE.getValue());
            firstInstrumentEntity.setType("Instrument type");
            firstInstrumentEntity.setOutlineText("String outlineText");

            InstrumentEntity secondInstrumentEntity = new InstrumentEntity();
            secondInstrumentEntity.setInformation("Information about instrument");
            secondInstrumentEntity.setName("Instrument name");
            secondInstrumentEntity.setStatus(Status.ACTIVE.getValue());
            secondInstrumentEntity.setType("Instrument type");
            secondInstrumentEntity.setOutlineText("String outlineText");

            InstrumentEntity thirdInstrumentEntity = new InstrumentEntity();
            thirdInstrumentEntity.setInformation("Information about instrument");
            thirdInstrumentEntity.setName("Instrument name");
            thirdInstrumentEntity.setStatus(Status.ACTIVE.getValue());
            thirdInstrumentEntity.setType("Instrument type");
            thirdInstrumentEntity.setOutlineText("String outlineText");

            entities.add(firstInstrumentEntity);
            entities.add(secondInstrumentEntity);
            entities.add(thirdInstrumentEntity);

            PagedData<InstrumentEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<InstrumentResponse> response = new ArrayList<>();

            InstrumentResponse firstResponse = new InstrumentResponse();
            firstResponse.setInformation("Information about instrument");
            firstResponse.setName("Instrument name");
            firstResponse.setStatus(Status.ACTIVE.getValue());
            firstResponse.setType("Instrument type");
            firstResponse.setOutlineText("String outlineText");

            InstrumentResponse secondResponse = new InstrumentResponse();
            secondResponse.setInformation("Information about instrument");
            secondResponse.setName("Instrument name");
            secondResponse.setStatus(Status.ACTIVE.getValue());
            secondResponse.setType("Instrument type");
            secondResponse.setOutlineText("String outlineText");

            InstrumentResponse thirdResponse = new InstrumentResponse();
            thirdResponse.setInformation("Information about instrument");
            thirdResponse.setName("Instrument name");
            thirdResponse.setStatus(Status.ACTIVE.getValue());
            thirdResponse.setType("Instrument type");
            thirdResponse.setOutlineText("String outlineText");

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<InstrumentResponse> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(instrumentDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<InstrumentResponse> instrumentFindResponse = instrumentService.get(filterRequest).getPayload();

            Assertions.assertThat(instrumentFindResponse).as("Check all elements").overridingErrorMessage("All elements should be equal.")
                    .hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateInstrument() {
        try {

            EntityRequest<InstrumentCreateRequest> req = new EntityRequest<>();

            var newInstrumentRequest = new InstrumentCreateRequest();
            newInstrumentRequest.setName("Test Name");
            newInstrumentRequest.setInformation("Instrument Information");
            newInstrumentRequest.setType("Instrument Type");
            newInstrumentRequest.setOutlineText("Instrument OutlineText");

            req.setEntity(newInstrumentRequest);

            var newInstrumentEnt = new InstrumentEntity();
            newInstrumentEnt.setName("Test Name");
            newInstrumentEnt.setInformation("Instrument Information");
            newInstrumentEnt.setType("Instrument Type");
            newInstrumentEnt.setOutlineText("Instrument OutlineText");

            var newInstrument = new InstrumentResponse();
            newInstrument.setName("Test Name");
            newInstrument.setInformation("Instrument Information");
            newInstrument.setType("Instrument Type");
            newInstrument.setOutlineText("Instrument OutlineText");

            Mockito.when(instrumentDAO.persist(newInstrumentEnt)).thenReturn(null);

            PayloadResponse<InstrumentResponse> instrumentFindResponse = instrumentService.create(req);

            Assertions.assertThat(instrumentFindResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy", "imageUrl", "status").isEqualTo(newInstrument);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdateInstrument() {
        try {

            EntityRequest<InstrumentUpdateRequest> request = new EntityRequest<>();

            InstrumentEntity instrumentEntity = new InstrumentEntity();
            instrumentEntity.setName("Update name");
            instrumentEntity.setInformation("Instrument information");
            instrumentEntity.setType("Update type");
            instrumentEntity.setOutlineText("Update OutlineText");

            InstrumentResponse instrumentResponse = new InstrumentResponse();
            instrumentEntity.setName("Update name");
            instrumentEntity.setInformation("Instrument information");
            instrumentEntity.setType("Update type");
            instrumentEntity.setOutlineText("Update OutlineText");

            InstrumentUpdateRequest updateInstrumentRequest = new InstrumentUpdateRequest();
            instrumentEntity.setName("Update name");
            instrumentEntity.setInformation("Instrument information");
            instrumentEntity.setType("Update type");
            instrumentEntity.setOutlineText("Update OutlineText");

            request.setEntity(updateInstrumentRequest);

            Mockito.when(instrumentRequestValidation.validateUpdateInstrumentRequest(request)).thenReturn(null);

            Mockito.when(instrumentDAO.findByPK(request.getEntity().getId())).thenReturn(instrumentEntity);

            Mockito.doNothing().when(instrumentDAO).merge(instrumentEntity);

            var instrumentUpdateResponse = instrumentService.update(request);
            Assertions.assertThat(instrumentUpdateResponse.getPayload()).as("Check all fields")
                    .overridingErrorMessage("All fields should be equal.").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(instrumentResponse);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeleteInstrument() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(requestValidator.validate(req)).thenReturn(null);

            Mockito.doNothing().when(instrumentDAO).removeByPK(req.getEntity());

            var instrumentFindResponse = instrumentService.delete(req);

            Assertions.assertThat(instrumentFindResponse.getPayload()).isEqualTo("Instrument deleted");

        } catch (Exception e) {
            Assert.fail();
        }
    }

}