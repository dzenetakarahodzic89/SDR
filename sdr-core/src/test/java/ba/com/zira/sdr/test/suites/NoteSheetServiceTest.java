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
import ba.com.zira.sdr.api.NoteSheetService;
import ba.com.zira.sdr.api.model.notesheet.NoteSheet;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetCreateRequest;
import ba.com.zira.sdr.api.model.notesheet.NoteSheetUpdateRequest;
import ba.com.zira.sdr.core.impl.NoteSheetServiceImpl;
import ba.com.zira.sdr.core.mapper.NoteSheetMapper;
import ba.com.zira.sdr.core.validation.NoteSheetRequestValidation;
import ba.com.zira.sdr.dao.NoteSheetDAO;
import ba.com.zira.sdr.dao.model.NoteSheetEntity;
import ba.com.zira.sdr.test.configuration.ApplicationTestConfiguration;
import ba.com.zira.sdr.test.configuration.BasicTestConfiguration;

@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class NoteSheetServiceTest extends BasicTestConfiguration {

    @Autowired
    private NoteSheetMapper noteSheetMapper;

    private NoteSheetDAO noteSheetDAO;
    private RequestValidator requestValidator;
    private NoteSheetRequestValidation noteSheetRequestValidation;
    private NoteSheetService noteSheetService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.noteSheetDAO = Mockito.mock(NoteSheetDAO.class);
        this.noteSheetRequestValidation = Mockito.mock(NoteSheetRequestValidation.class);
        this.noteSheetService = new NoteSheetServiceImpl(noteSheetDAO, noteSheetMapper, noteSheetRequestValidation, null, null, null, null,
                null);
    }

    @Test(enabled = false)
    public void testFindNoteSheet() {
        try {

            List<NoteSheetEntity> entities = new ArrayList<>();
            NoteSheetEntity firstNoteSheetEntity = new NoteSheetEntity();
            // firstNoteSheetEntity.setSheetContent("Test note sheet");
            firstNoteSheetEntity.setId(1L);

            NoteSheetEntity secondNoteSheetEntity = new NoteSheetEntity();
            // secondNoteSheetEntity.setSheetContent("Test note sheet");
            secondNoteSheetEntity.setId(2L);

            NoteSheetEntity thirdNoteSheetEntity = new NoteSheetEntity();
            // thirdNoteSheetEntity.setSheetContent("Test note sheet");
            thirdNoteSheetEntity.setId(3L);

            entities.add(firstNoteSheetEntity);
            entities.add(secondNoteSheetEntity);
            entities.add(thirdNoteSheetEntity);

            PagedData<NoteSheetEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);

            List<NoteSheet> response = new ArrayList<>();

            NoteSheet firstResponse = new NoteSheet();
            firstResponse.setId(1L);
            // firstResponse.setSheetContent("Test note sheet");

            NoteSheet secondResponse = new NoteSheet();
            secondResponse.setId(2L);
            // secondResponse.setSheetContent("Test note sheet");

            NoteSheet thirdResponse = new NoteSheet();
            // thirdResponse.setSheetContent("Test note sheet");
            thirdResponse.setId(3L);

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<NoteSheet> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);

            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);

            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(noteSheetDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);

            List<NoteSheet> noteSheetFindResponse = noteSheetService.find(filterRequest).getPayload();

            Assertions.assertThat(noteSheetFindResponse).as("Check all elements").hasSameElementsAs(response);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = false)
    public void testCreateNoteSheet() {
        try {

            EntityRequest<NoteSheetCreateRequest> req = new EntityRequest<>();

            var newNoteSheetRequest = new NoteSheetCreateRequest();
            // newNoteSheetRequest.setSheetContent("Test note sheet");
            newNoteSheetRequest.setNotationType("Some type");

            Mockito.when(noteSheetRequestValidation.validateCreateNoteSheetRequest(req)).thenReturn(null);

            req.setEntity(newNoteSheetRequest);

            var newNoteSheetEnt = new NoteSheetEntity();
            // newNoteSheetEnt.setSheetContent("Test note sheet");
            newNoteSheetEnt.setCreatedBy("SOMEONE");
            newNoteSheetEnt.setNotationType("Some type");
            newNoteSheetEnt.setStatus("Active");

            var newNoteSheet = new NoteSheet();
            // newNoteSheet.setSheetContent("Test note sheet");
            newNoteSheet.setCreatedBy("SOMEONE");
            newNoteSheet.setNotationType("Some type");
            newNoteSheet.setStatus("Active");

            Mockito.when(noteSheetDAO.persist(newNoteSheetEnt)).thenReturn(null);

            PayloadResponse<NoteSheet> noteSheetFindResponse = noteSheetService.create(req);

            Assertions.assertThat(noteSheetFindResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy", "imageUrl").isEqualTo(newNoteSheet);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = false)
    public void testUpdateNoteSheet() {
        try {

            EntityRequest<NoteSheetUpdateRequest> request = new EntityRequest<>();

            NoteSheetEntity noteSheetEntity = new NoteSheetEntity();
            // noteSheetEntity.setSheetContent("Old Test Name 1");
            noteSheetEntity.setId(22L);

            NoteSheet noteSheetResponse = new NoteSheet();
            // noteSheetResponse.setSheetContent("Update Test Name 1");
            noteSheetResponse.setId(22L);

            NoteSheetUpdateRequest NoteSheetRequest = new NoteSheetUpdateRequest();
            // NoteSheetRequest.setSheetContent("Update Test Name 1");
            NoteSheetRequest.setId(22L);
            request.setEntity(NoteSheetRequest);

            Mockito.when(noteSheetRequestValidation.validateUpdateNoteSheetRequest(request)).thenReturn(null);

            Mockito.when(noteSheetDAO.findByPK(request.getEntity().getId())).thenReturn(noteSheetEntity);

            Mockito.doNothing().when(noteSheetDAO).merge(noteSheetEntity);

            var noteSheetUpdateResponse = noteSheetService.update(request);
            Assertions.assertThat(noteSheetUpdateResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(noteSheetResponse);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = false)
    public void testDeleteNoteSheet() {
        try {
            var req = new EntityRequest<Long>();

            req.setEntity(1L);

            Mockito.when(requestValidator.validate(req)).thenReturn(null);

            Mockito.doNothing().when(noteSheetDAO).removeByPK(req.getEntity());

            var noteSheetFindResponse = noteSheetService.delete(req);

            Assertions.assertThat(noteSheetFindResponse.getPayload()).isEqualTo("Note Sheet with id 1 is successfully deleted!");

        } catch (Exception e) {
            Assert.fail();
        }
    }

}
