package org.sunbird.contentvalidation.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.core.io.ClassPathResource;
import org.sunbird.contentvalidation.config.Configuration;
import org.sunbird.contentvalidation.model.ContentPdfValidation;
import org.sunbird.contentvalidation.model.Profanity;
import org.sunbird.contentvalidation.model.ProfanityClassification;
import org.sunbird.contentvalidation.repo.ContentValidationRepoServiceImpl;
import org.sunbird.contentvalidation.repo.PdfDocValidationRepository;
import org.sunbird.contentvalidation.repo.model.PdfDocValidationResponse;
import org.sunbird.contentvalidation.service.ContentProviderService;
import org.sunbird.contentvalidation.util.CommonUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContentValidationServiceImplTest {

    @Mock
    private Configuration configuration;

    @Mock
    private CommonUtils commonUtils;

    @Mock
    private OutboundRequestHandlerServiceImpl outboundRequestHandlerService;

    @Mock
    private ObjectMapper mapper;

    @Mock
    ContentProviderService contentProviderService;

    @Mock
    ContentValidationRepoServiceImpl contentValidationRepoService;

    @Mock
    PdfDocValidationRepository pdfRepo;

    @InjectMocks
    private ContentValidationServiceImpl contentValidationService;

    final String profanityText = "1. Scumbag 2. Shit 3. Badmashi 4. Bad (Context*) 5. lazy fools 6. Liars 7. bloody liar 8. bloody\n" +
            "Chair 9. bloody fellow 10. Damn 11. Deceive 12. Darling 13. dacoits 14. bucket of shit";
    final String profanityServiceHost = "http://52.173.240.27:4001";
    final String profanityServicePath = "/checkProfanity";
    final String pdfDownloadURL = "https://igot.blob.core.windows.net/content/content/do_113155331519225856127/artifact/sample.pdf";


    @Test
    void getProfanityCheckForText() {
        HashMap<String, Object> requestObject = new HashMap<>();
        requestObject.put("text", profanityText);
        Profanity profanity = new Profanity();
        profanity.setText_original(profanityText);
        profanity.setText_tagged(profanityText);
        profanity.setPossible_profanity(new ArrayList<>());
        given(configuration.getProfanityServiceHost()).willReturn(profanityServiceHost);
        given(configuration.getProfanityServicePath()).willReturn(profanityServicePath);
        given(outboundRequestHandlerService.fetchResultUsingPost(profanityServiceHost.concat(profanityServicePath), requestObject)).willReturn(profanity);
        when(mapper.convertValue(any(), eq(Profanity.class))).thenReturn(profanity);
        assertEquals(contentValidationService.getProfanityCheckForText(profanityText).getText_original(), profanityText);
        assertEquals(contentValidationService.getProfanityCheckForText(profanityText).getText_tagged(), profanityText);
        assertTrue(contentValidationService.getProfanityCheckForText(profanityText).getPossible_profanity().isEmpty());
    }

//    @Test
//    void validatePdfContentTest() throws IOException {
//        PdfDocValidationResponse
//                pdfDocFirstPageResponse = new PdfDocValidationResponse();
//        pdfDocFirstPageResponse.setCompleted(false);
//        pdfDocFirstPageResponse.setNoOfPagesCompleted(1);
//        pdfDocFirstPageResponse.setTotal_page_images(1);
//        pdfDocFirstPageResponse.setScore(0.0);
//        pdfDocFirstPageResponse.setImage_occurances("1");
//        ContentPdfValidation contentPdfValidation = new ContentPdfValidation();
//        PdfDocValidationResponse pdfDocValidationResponse = new PdfDocValidationResponse();
//        Profanity profanity = new Profanity();
//        profanity.setPossible_profanity_categorical(new HashMap<>());
//        ProfanityClassification classification = new ProfanityClassification();
//        classification.setProbability(1.0);
//        profanity.setOverall_text_classification(classification);
//        ClassPathResource classPathResource = new ClassPathResource("offensive_images.pdf");
//        InputStream inputStream = classPathResource.getInputStream();
//        pdfDocValidationResponse.setCompleted(true);
//        contentPdfValidation.setPdfDownloadUrl(pdfDownloadURL);
//        contentPdfValidation.setContentId("do_113155331519225856127");
//        contentPdfValidation.setFileName("sample.pdf");
//        InputStream anyInputStream = new ByteArrayInputStream("test data".getBytes());
//        when(contentProviderService.getContentFile(pdfDownloadURL)).thenReturn(anyInputStream);
//        //  doNothing().when(contentValidationService).enrichTotalPages(contentPdfValidation.getContentId(), contentPdfValidation.getFileName(), 3);
//        when(commonUtils.emptyCheck(anyString())).thenReturn(false);
//        when(contentValidationService.getProfanityCheckForText(profanityText)).thenReturn(profanity);
//        doNothing().when(contentValidationRepoService).updateContentValidationResult(any(), anyBoolean());
//        when(contentValidationService.performProfanityAnalysis(inputStream, contentPdfValidation.getFileName(), contentPdfValidation.getContentId())).thenReturn(pdfDocValidationResponse);
//        assertEquals(contentValidationService.validatePdfContent(contentPdfValidation).isCompleted(), true);
//    }

    @Test
    void enrichTotalPages() {
        ContentValidationServiceImpl mockObj = mock(ContentValidationServiceImpl.class);
        mockObj.enrichTotalPages("do_113155331519225856127", "sample.pdf", 3);
        verify(mockObj, times(1)).enrichTotalPages("do_113155331519225856127", "sample.pdf", 3);
    }
}
