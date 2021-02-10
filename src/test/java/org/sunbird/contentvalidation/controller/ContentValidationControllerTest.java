package org.sunbird.contentvalidation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.sunbird.contentvalidation.model.ContentPdfValidation;
import org.sunbird.contentvalidation.repo.ContentValidationRepoServiceImpl;
import org.sunbird.contentvalidation.repo.PdfDocValidationRepository;
import org.sunbird.contentvalidation.repo.model.PdfDocValidationResponse;
import org.sunbird.contentvalidation.service.ContentProviderRestHandlerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ContentValidationController.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContentValidationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ContentValidationRepoServiceImpl contentValidationRepoService;

    @MockBean
    PdfDocValidationRepository pdfDocValidationRepository;

    @MockBean
    private ContentProviderRestHandlerService restHandler;

    ContentPdfValidation contentPdfValidation;

    PdfDocValidationResponse response;

    final String ROOT_ORG = "rootOrg";
    final String WID = "wid";
    final String CONTENT_ID = "contentId";

    @BeforeAll
    void loadConfigs(){
        contentPdfValidation = new ContentPdfValidation();
        contentPdfValidation.setFileName("fileName");
        contentPdfValidation.setContentId("contentId");
        contentPdfValidation.setPdfDownloadUrl("pdfDownloadUrl");
        response = new PdfDocValidationResponse();
    }

    @Test
    void getContentPdfsProfanity() throws Exception {
        List<PdfDocValidationResponse> responseList = new ArrayList<>();
        when(pdfDocValidationRepository.findProgressByContentIds(Arrays.asList(CONTENT_ID))).thenReturn(responseList);
        when(contentValidationRepoService.getContentValidationResponse(ROOT_ORG, WID, CONTENT_ID)).thenReturn(responseList);
        this.mockMvc.perform(get("/v1/getPdfProfanityForContent/{contentId}", CONTENT_ID).contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("rootOrg", ROOT_ORG).header("wid", WID))
                .andExpect(status().isOk());
    }

    @Test
    void getContentPdfProfanity() throws Exception {
        when(pdfDocValidationRepository.findProgressByContentIdAndPdfFileName(contentPdfValidation.getContentId(), contentPdfValidation.getFileName())).thenReturn(response);
        this.mockMvc.perform(post("/v1/getPdfProfanity").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(contentPdfValidation)))
                .andExpect(status().isOk());
    }

    @Test
    void startContentPdfProfanity() throws Exception {
        doNothing().when(restHandler).handleStartContentValidationRequest(contentPdfValidation);
        this.mockMvc.perform(post("/v1/startPdfProfanity").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(contentPdfValidation)))
                .andExpect(status().isAccepted());
    }

    @Test
    void getLocalPdfProfanity() throws Exception {
        when(pdfDocValidationRepository.findProgressByContentIdAndPdfFileName(contentPdfValidation.getContentId(), contentPdfValidation.getFileName())).thenReturn(response);
        when(contentValidationRepoService.getContentValidationResponseForFile(contentPdfValidation.getContentId(), contentPdfValidation.getFileName())).thenReturn(response);
        this.mockMvc.perform(post("/v1/getLocalPdfProfanity").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(contentPdfValidation)))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}