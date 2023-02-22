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
import ba.com.zira.sdr.api.LyricService;
import ba.com.zira.sdr.api.model.lyric.Lyric;
import ba.com.zira.sdr.api.model.lyric.LyricCreateRequest;
import ba.com.zira.sdr.api.model.lyric.LyricUpdateRequest;
import ba.com.zira.sdr.core.impl.LyricServiceImpl;
import ba.com.zira.sdr.core.mapper.LyricMapper;
import ba.com.zira.sdr.core.validation.LyricRequestValidation;
import ba.com.zira.sdr.dao.LyricDAO;
import ba.com.zira.sdr.dao.model.LyricEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class LyricServiceTest extends BasicTestConfiguration {

    @Autowired
    private LyricMapper lyricMapper;

    private LyricDAO lyricDAO;
    private RequestValidator requestValidator;
    private LyricRequestValidation lyricRequestValidation;
    private LyricService lyricService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.lyricDAO = Mockito.mock(LyricDAO.class);
        this.lyricRequestValidation = Mockito.mock(LyricRequestValidation.class);
        this.lyricService = new LyricServiceImpl(lyricDAO, lyricMapper, lyricRequestValidation);
    }

    @Test(enabled = true)
    public void testFindLyric() {
        try {

            List<LyricEntity> entities = new ArrayList<>();
            LyricEntity firstLyricEntity = new LyricEntity();
            firstLyricEntity.setId(1L);
            firstLyricEntity.setLanguage("Test Language");
            firstLyricEntity.setText("Test Type 1");

            LyricEntity secondLyricEntity = new LyricEntity();
            secondLyricEntity.setId(1L);
            secondLyricEntity.setLanguage("Test Language");
            secondLyricEntity.setText("Test Type 1");

            LyricEntity thirdLyricEntity = new LyricEntity();
            thirdLyricEntity.setId(1L);
            thirdLyricEntity.setLanguage("Test Language");
            thirdLyricEntity.setText("Test Type 1");

            entities.add(firstLyricEntity);
            entities.add(secondLyricEntity);
            entities.add(thirdLyricEntity);

            PagedData<LyricEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<Lyric> response = new ArrayList<>();

            Lyric firstResponse = new Lyric();
            firstResponse.setLanguage("Test Language");
            firstResponse.setText("Test Type 1");

            Lyric secondResponse = new Lyric();
            secondResponse.setLanguage("Test Language");
            secondResponse.setText("Test Type 1");

            Lyric thirdResponse = new Lyric();
            thirdResponse.setLanguage("Test Language");
            thirdResponse.setText("Test Type 1");

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<Lyric> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(lyricDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<Lyric> lyricFindResponse = lyricService.find(filterRequest).getPayload();

            Assertions.assertThat(lyricFindResponse).as("Check all elements").overridingErrorMessage("All elements should be equal.")
                    .hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateLyric() {
        try {

            EntityRequest<LyricCreateRequest> req = new EntityRequest<>();

            var newLyricRequest = new LyricCreateRequest();

            newLyricRequest.setLanguage("Test Language");
            newLyricRequest.setText("Test Type 1");
            newLyricRequest.setSongId(1L);

            req.setEntity(newLyricRequest);

            var newLyricEnt = new LyricEntity();
            newLyricEnt.setId(1L);
            newLyricEnt.setLanguage("Test Language");
            newLyricEnt.setText("Test Type 1");
            // newLyricEnt.setSong(1L);
            newLyricEnt.setStatus(Status.ACTIVE.getValue());

            var newLyric = new Lyric();
            newLyric.setId(1L);
            newLyric.setLanguage("Test Language");
            newLyric.setText("Test Type 1");
            // newLyric.setSongId(1L);
            newLyric.setStatus(Status.ACTIVE.getValue());

            Mockito.when(lyricDAO.persist(newLyricEnt)).thenReturn(null);

            PayloadResponse<Lyric> lyricFindResponse = lyricService.create(req);

            Assertions.assertThat(lyricFindResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy", "songId").isEqualTo(newLyric);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdateLyric() {
        try {

            EntityRequest<LyricUpdateRequest> req = new EntityRequest<>();

            LyricUpdateRequest updateLyricRequest = new LyricUpdateRequest();
            updateLyricRequest.setId(1L);
            updateLyricRequest.setLanguage("Test Language");
            updateLyricRequest.setText("Test Type 1");
            updateLyricRequest.setSongId(1L);

            req.setEntity(updateLyricRequest);

            var lyricEnt = new LyricEntity();
            lyricEnt.setId(1L);
            lyricEnt.setLanguage("Test Language");
            lyricEnt.setText("Test Type 1");
            // newLyricEnt.setSongId(1L);
            lyricEnt.setStatus(Status.ACTIVE.getValue());

            var lyric = new Lyric();
            lyric.setId(1L);
            lyric.setLanguage("Test Language");
            lyric.setText("Test Type 1");
            // lyric.setSongId(1L);
            lyric.setStatus(Status.ACTIVE.getValue());

            Mockito.when(lyricRequestValidation.validateUpdateLyricRequest(req)).thenReturn(null);

            Mockito.when(lyricDAO.findByPK(req.getEntity().getId())).thenReturn(lyricEnt);

            Mockito.doNothing().when(lyricDAO).merge(lyricEnt);

            var lyricUpdateResponse = lyricService.update(req);
            Assertions.assertThat(lyricUpdateResponse.getPayload()).as("Check all fields")
                    .overridingErrorMessage("All fields should be equal.").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy", "songId").isEqualTo(lyric);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeleteLyric() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(lyricRequestValidation.validateExistsLyricRequest(req)).thenReturn(null);

            Mockito.doNothing().when(lyricDAO).removeByPK(req.getEntity());

            var lyricDeleteResponse = lyricService.delete(req);

            Assertions.assertThat(lyricDeleteResponse.getPayload()).isEqualTo("Lyric removed successfully!");

        } catch (Exception e) {
            Assert.fail();
        }
    }

}
