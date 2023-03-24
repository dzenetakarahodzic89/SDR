package ba.com.zira.sdr.language.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EmptyRequest;
import ba.com.zira.commons.message.response.ListPayloadResponse;
import ba.com.zira.sdr.api.LanguageService;
import ba.com.zira.sdr.api.model.lov.LoV;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "language")
@RestController
@RequestMapping(value = "language")
public class LanguageRestService {

    @Autowired
    private LanguageService languageService;

    @Operation(summary = "All languages and their names")
    @GetMapping
    public ListPayloadResponse<LoV> get() throws ApiException {
        return languageService.get(new EmptyRequest());
    }
}
