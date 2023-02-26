package ba.com.zira.sdr.test.suites;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.request.FilterRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.commons.model.PagedData;
import ba.com.zira.commons.model.QueryConditionPage;
import ba.com.zira.commons.model.enums.Status;
import ba.com.zira.commons.validation.RequestValidator;
import ba.com.zira.sdr.api.SongSimilarityDetailService;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetail;
import ba.com.zira.sdr.api.model.songsimilaritydetail.SongSimilarityDetailCreateRequest;
import ba.com.zira.sdr.core.impl.SongSimilarityDetailServiceImpl;
import ba.com.zira.sdr.core.mapper.SongSimilarityDetailMapper;
import ba.com.zira.sdr.core.validation.SongSimilarityDetailRequestValidation;
import ba.com.zira.sdr.dao.SongSimilarityDAO;
import ba.com.zira.sdr.dao.SongSimilarityDetailDAO;
import ba.com.zira.sdr.dao.model.SongSimilarityDetailEntity;
import ba.com.zira.sdr.dao.model.SongSimilarityEntity;
import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class SongSimilarityDetailServiceTest extends BasicTestConfiguration {

    @Autowired
    private SongSimilarityDetailMapper songSimilarityDetailMapper;

    private SongSimilarityDetailDAO songSimilarityDetailDAO;
    private RequestValidator requestValidator;
    private SongSimilarityDetailRequestValidation songSimilarityDetailRequestValidation;
    private SongSimilarityDetailService songSimilarityDetailService;

    private SongSimilarityDAO songSimilarityDAO;

    private List<SongSimilarityEntity> songSimilarityEntities = new ArrayList<>();

    private void FK() {


        songSimilarityEntities.add(new SongSimilarityEntity());
        songSimilarityEntities.add(new SongSimilarityEntity());
        songSimilarityEntities.add(new SongSimilarityEntity());


    }

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.songSimilarityDetailDAO = Mockito.mock(SongSimilarityDetailDAO.class);
        this.songSimilarityDetailRequestValidation = Mockito.mock(SongSimilarityDetailRequestValidation.class);
        this.songSimilarityDetailService = new SongSimilarityDetailServiceImpl(songSimilarityDetailDAO, songSimilarityDetailMapper,
                songSimilarityDetailRequestValidation);
        this.songSimilarityDAO = new SongSimilarityDAO();
        this.FK();
    }

    @Test(enabled = true)
    public void testFindSongSimilarityDetail() {
        try {
            List<SongSimilarityDetailEntity> entities = new ArrayList<>();

            SongSimilarityDetailEntity firstSongSimilarityDetail = new SongSimilarityDetailEntity();
            firstSongSimilarityDetail.setSongSimilarity(songSimilarityEntities.get(0));


            SongSimilarityDetailEntity secondSongSimilarityDetail = new SongSimilarityDetailEntity();
            firstSongSimilarityDetail.setSongSimilarity(songSimilarityEntities.get(1));

            SongSimilarityDetailEntity thirdSongSimilarityDetail = new SongSimilarityDetailEntity();
            firstSongSimilarityDetail.setSongSimilarity(songSimilarityEntities.get(2));

            entities.add(firstSongSimilarityDetail);
            entities.add(secondSongSimilarityDetail);
            entities.add(thirdSongSimilarityDetail);

            PagedData<SongSimilarityDetailEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<SongSimilarityDetail> response = new ArrayList<>();

            SongSimilarityDetail firstResponse = new SongSimilarityDetail();

            firstResponse.setStatus(null);


            SongSimilarityDetail secondResponse = new SongSimilarityDetail();

            secondResponse.setStatus(null);


            SongSimilarityDetail thirdResponse = new SongSimilarityDetail();

            secondResponse.setStatus(null);

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<SongSimilarityDetail> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();

            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);
            Mockito.when(songSimilarityDetailDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<SongSimilarityDetail> songArtistFindResponse = songSimilarityDetailService.get(filterRequest).getPayload();

            Assertions.assertThat(songArtistFindResponse).as("Check all elements").overridingErrorMessage("All elements should be equal.")
                    .hasSameElementsAs(response);
        } catch (Exception e) {
            Assert.fail();
        }
    }


    @Test(enabled = true)
    public void testCreateSongSimilarityDetail() {
        try {

            EntityRequest<SongSimilarityDetailCreateRequest> req = new EntityRequest<>();

            var newSongSimilarityreq = new SongSimilarityDetailCreateRequest();
            newSongSimilarityreq.setSongSimilarityId(null);




            req.setEntity(newSongSimilarityreq);

            SongSimilarityDetailEntity songSimilarityDetailEntity = new SongSimilarityDetailEntity();
            songSimilarityDetailEntity.setSongSimilarity(songSimilarityEntities.get(0));



            var songsimilaritydetail = new SongSimilarityDetail();
            songsimilaritydetail.setSongSampleId(null);
            songsimilaritydetail.setStatus(null);


            Mockito.when(songSimilarityDetailDAO.persist(songSimilarityDetailEntity)).thenReturn(null);
            Mockito.when(songSimilarityDetailRequestValidation.validateCreateSongSimilarityDetailRequest(req)).thenReturn(null);




            PayloadResponse<SongSimilarityDetail> songArtistCreateResponse = songSimilarityDetailService.create(req);

            Assertions.assertThat(songArtistCreateResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy", "status").isEqualTo(songsimilaritydetail);

        } catch (Exception e) {
            Assert.fail();
        }
    }



    @Test(enabled = true)
    public void testDeleteSongSimilarityDetail() {

        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(songSimilarityDetailRequestValidation.validateExistsSongSimilartyDetailRequest(req)).thenReturn(null);
            Mockito.doNothing().when(songSimilarityDetailDAO).removeByPK(req.getEntity());

            var songSimilarityDetailDeleteResponse = songSimilarityDetailService.delete(req);

            Assertions.assertThat(songSimilarityDetailDeleteResponse.getPayload())

                    .isEqualTo(null);

        } catch (Exception e) {
            Assert.fail();

        }
    }

}
