package ba.com.zira.sdr.test.suites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.commons.validation.RequestValidator;
import ba.com.zira.sdr.api.SongInstrumentService;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrument;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrumentCreateRequest;
import ba.com.zira.sdr.api.model.songinstrument.SongInstrumentUpdateRequest;
import ba.com.zira.sdr.core.impl.SongInstrumentServiceImpl;
import ba.com.zira.sdr.core.mapper.SongInstrumentMapper;
import ba.com.zira.sdr.core.validation.SongInstrumentValidation;
import ba.com.zira.sdr.dao.SongInstrumentDAO;
import ba.com.zira.sdr.dao.model.SongInstrumentEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class SongInstrumentServiceTest extends BasicTestConfiguration {

    @Autowired
    private SongInstrumentMapper songInstrumentMapper;

    private SongInstrumentDAO songInstrumentDAO;
    private RequestValidator requestValidator;
    private SongInstrumentValidation songInstrumentRequestValidation;
    private SongInstrumentService songInstrumentService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.songInstrumentDAO = Mockito.mock(SongInstrumentDAO.class);
        this.songInstrumentRequestValidation = Mockito.mock(SongInstrumentValidation.class);
        this.songInstrumentService = new SongInstrumentServiceImpl(songInstrumentDAO, songInstrumentMapper, songInstrumentRequestValidation);
    }

    @Test(enabled = true)
    public void testFindSongInstrument() {
        try {

            List<SongInstrumentEntity> entities = new ArrayList<>();
            SongInstrumentEntity firstSongInstrumentEntity = new SongInstrumentEntity();
            firstSongInstrumentEntity.setName("Test1");
            firstSongInstrumentEntity.setId(1L);

            SongInstrumentEntity secondSongInstrumentEntity = new SongInstrumentEntity();
            secondSongInstrumentEntity.setName("Test2");
            secondSongInstrumentEntity.setId(2L);

            SongInstrumentEntity thirdSongInstrumentEntity = new SongInstrumentEntity();
            thirdSongInstrumentEntity.setName("Test3");
            thirdSongInstrumentEntity.setId(3L);

            entities.add(firstSongInstrumentEntity);
            entities.add(secondSongInstrumentEntity);
            entities.add(thirdSongInstrumentEntity);
            
            //Lazni response of DAo.find metode
            PagedData<SongInstrumentEntity> pagedEntites = new PagedData<SongInstrumentEntity>();
            pagedEntites.setRecords(entities);

            List<SongInstrument> response = new ArrayList<>();

            SongInstrument firstResponse = new SongInstrument();
            firstResponse.setName("Test1");
            firstResponse.setId(1L);

            SongInstrument secondResponse = new SongInstrument();
            secondResponse.setName("Test2");
            secondResponse.setId(2L);

            SongInstrument thirdResponse = new SongInstrument();
            thirdResponse.setName("Test3");
            thirdResponse.setId(3L);

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);
            //Ocekivani izlaz
            PagedData<SongInstrument> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);
            //Ulazni parametri
            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(songInstrumentDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<SongInstrument> songInstrumentFindResponse = songInstrumentService.find(filterRequest).getPayload();
            Assertions.assertThat(songInstrumentFindResponse).as("Check all elements").hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateSongInstrument() {
        try {

            EntityRequest<SongInstrumentCreateRequest> req = new EntityRequest<>();

            var newSongInstrumentRequest = new SongInstrumentCreateRequest();
            newSongInstrumentRequest.setName("Some name");
            newSongInstrumentRequest.setOutlineText("text");
           
            
 
            req.setEntity(newSongInstrumentRequest);

            var newSongInstrumentEnt = new SongInstrumentEntity();
            newSongInstrumentEnt.setName("Some name");
            newSongInstrumentEnt.setCreatedBy("SOMEONE");
            newSongInstrumentEnt.setModifiedBy("Someone");
            newSongInstrumentEnt.setOutlineText("text");
           

            var newSongInstrument = new SongInstrument();
            newSongInstrument.setName("Some name");
            newSongInstrument.setCreatedBy("SOMEONE");
            newSongInstrument.setModifiedBy("Someone");
            newSongInstrument.setOutlineText("text");

            Mockito.when(songInstrumentDAO.persist(newSongInstrumentEnt)).thenReturn(null);
            Mockito.when(songInstrumentRequestValidation.validateCreateSongInstrumentRequest(req)).thenReturn(null);

            PayloadResponse<SongInstrument> songInstrumentFindResponse = songInstrumentService.create(req);

            Assertions.assertThat(songInstrumentFindResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy", "imageUrl").isEqualTo(newSongInstrument);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdateSongInstrument() {
        try {

            EntityRequest<SongInstrumentUpdateRequest> request = new EntityRequest<>();

            SongInstrumentEntity songInstrumentEntity = new SongInstrumentEntity();
            songInstrumentEntity.setName("Old Test Name 1");
            songInstrumentEntity.setId(22L);
            
            SongInstrument songInstrumentResponse = new SongInstrument();
            songInstrumentResponse.setName("Update Test Name 1");
            songInstrumentResponse.setId(22L);

            SongInstrumentUpdateRequest SongInstrumentRequest = new SongInstrumentUpdateRequest();
            SongInstrumentRequest.setName("Update Test Name 1");
            SongInstrumentRequest.setId(22L);
            request.setEntity(SongInstrumentRequest);

            Mockito.when(songInstrumentRequestValidation.validateUpdateSongInstrumentRequest(request)).thenReturn(null);

            Mockito.when(songInstrumentDAO.findByPK(request.getEntity().getId())).thenReturn(songInstrumentEntity);

            Mockito.doNothing().when(songInstrumentDAO).merge(songInstrumentEntity);

            var songInstrumentUpdateResponse = songInstrumentService.update(request);
            Assertions.assertThat(songInstrumentUpdateResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(songInstrumentResponse);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeleteSongInstrument() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(requestValidator.validate(req)).thenReturn(null);

            Mockito.doNothing().when(songInstrumentDAO).removeByPK(req.getEntity());

            var songInstrumentFindResponse = songInstrumentService.delete(req);

            Assertions.assertThat(songInstrumentFindResponse.getPayload()).isEqualTo("Record removed successfully!");

        } catch (Exception e) {
            Assert.fail();
        }
    }

}
