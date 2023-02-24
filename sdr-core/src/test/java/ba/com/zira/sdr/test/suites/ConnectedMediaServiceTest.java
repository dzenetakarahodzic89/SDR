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
import ba.com.zira.sdr.api.ConnectedMediaService;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMedia;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMediaCreateRequest;
import ba.com.zira.sdr.api.model.connectedmedia.ConnectedMediaUpdateRequest;
import ba.com.zira.sdr.core.impl.ConnectedMediaServiceImpl;
import ba.com.zira.sdr.core.mapper.ConnectedMediaMapper;
import ba.com.zira.sdr.core.validation.ConnectedMediaRequestValidation;
import ba.com.zira.sdr.dao.ConnectedMediaDAO;
import ba.com.zira.sdr.dao.model.ConnectedMediaEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class ConnectedMediaServiceTest extends BasicTestConfiguration {

    @Autowired
    private ConnectedMediaMapper connectedMediaMapper;

    private ConnectedMediaDAO connectedMediaDAO;
    private RequestValidator requestValidator;
    private ConnectedMediaRequestValidation connectedMediaRequestValidation;
    private ConnectedMediaService connectedMediaService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.connectedMediaDAO = Mockito.mock(ConnectedMediaDAO.class);
        this.connectedMediaRequestValidation = Mockito.mock(ConnectedMediaRequestValidation.class);
        this.connectedMediaService = new ConnectedMediaServiceImpl(connectedMediaDAO, connectedMediaMapper, connectedMediaRequestValidation);
    }

    @Test(enabled = true)
    public void testFindConnectedMedia() {
        try {

            List<ConnectedMediaEntity> entities = new ArrayList<>();
            ConnectedMediaEntity firstConnectedMediaEntity = new ConnectedMediaEntity();
            firstConnectedMediaEntity.setObjectType(" Some type");
            firstConnectedMediaEntity.setId(1L);

            ConnectedMediaEntity secondConnectedMediaEntity = new ConnectedMediaEntity();
            secondConnectedMediaEntity.setObjectType(" Some type");
            secondConnectedMediaEntity.setId(2L);

            ConnectedMediaEntity thirdConnectedMediaEntity = new ConnectedMediaEntity();
            thirdConnectedMediaEntity.setObjectType(" Some type");
            thirdConnectedMediaEntity.setId(3L);

            entities.add(firstConnectedMediaEntity);
            entities.add(secondConnectedMediaEntity);
            entities.add(thirdConnectedMediaEntity);

            PagedData<ConnectedMediaEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<ConnectedMedia> response = new ArrayList<>();

            ConnectedMedia firstResponse = new ConnectedMedia();
            firstResponse.setId(1L);
            firstResponse.setObjectType(" Some type");

            ConnectedMedia secondResponse = new ConnectedMedia();
            secondResponse.setId(2L);
            secondResponse.setObjectType(" Some type");

            ConnectedMedia thirdResponse = new ConnectedMedia();
            thirdResponse.setObjectType(" Some type");
            thirdResponse.setId(3L);

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<ConnectedMedia> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(connectedMediaDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<ConnectedMedia> connectedMediaFindResponse = connectedMediaService.find(filterRequest).getPayload();

            Assertions.assertThat(connectedMediaFindResponse).as("Check all elements").hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateConnectedMedia() {
        try {

            EntityRequest<ConnectedMediaCreateRequest> req = new EntityRequest<>();

            var newConnectedMediaRequest = new ConnectedMediaCreateRequest();
           
            newConnectedMediaRequest.setObjectId(3L);

            req.setEntity(newConnectedMediaRequest);

            var newConnectedMediaEnt = new ConnectedMediaEntity();
           
            newConnectedMediaEnt.setCreatedBy("SOMEONE");
            newConnectedMediaEnt.setObjectId(3L);

            var newConnectedMedia = new ConnectedMedia();
          
            newConnectedMedia.setCreatedBy("SOMEONE");
            newConnectedMedia.setObjectId(3L);
            newConnectedMedia.setStatus("Active");

            Mockito.when(connectedMediaDAO.persist(newConnectedMediaEnt)).thenReturn(null);

            PayloadResponse<ConnectedMedia> connectedMediaFindResponse = connectedMediaService.create(req);

            Assertions.assertThat(connectedMediaFindResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy", "imageUrl").isEqualTo(newConnectedMedia);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdateConnectedMedia() {
        try {

            EntityRequest<ConnectedMediaUpdateRequest> request = new EntityRequest<>();

            ConnectedMediaEntity connectedMediaEntity = new ConnectedMediaEntity();
            connectedMediaEntity.setModifiedBy("Someone");
            connectedMediaEntity.setId(22L);
      

            ConnectedMedia connectedMediaResponse = new ConnectedMedia();
            connectedMediaEntity.setModifiedBy("Someone");
            connectedMediaResponse.setId(22L);

            ConnectedMediaUpdateRequest updateConnectedMediaRequest = new ConnectedMediaUpdateRequest();
            connectedMediaEntity.setModifiedBy("Someone");
            updateConnectedMediaRequest.setId(22L);
            request.setEntity(updateConnectedMediaRequest);

            Mockito.when(connectedMediaRequestValidation.validateConnectedMediaUpdateRequest(request)).thenReturn(null);

            Mockito.when(connectedMediaDAO.findByPK(request.getEntity().getId())).thenReturn(connectedMediaEntity);

            Mockito.doNothing().when(connectedMediaDAO).merge(connectedMediaEntity);

            var connectedMediaUpdateResponse = connectedMediaService.update(request);
            Assertions.assertThat(connectedMediaUpdateResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(connectedMediaResponse);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeleteConnectedMedia() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(requestValidator.validate(req)).thenReturn(null);

            Mockito.doNothing().when(connectedMediaDAO).removeByPK(req.getEntity());

            var connectedMediaFindResponse = connectedMediaService.delete(req);

            Assertions.assertThat(connectedMediaFindResponse.getPayload()).isEqualTo("Connected Media with id 1 is successfully deleted!");

        } catch (Exception e) {
            Assert.fail();
        }
    }

}
