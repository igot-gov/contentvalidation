package com.eagle.contentvalidation.controller;

import com.eagle.contentvalidation.model.ContentPdfValidation;
import com.eagle.contentvalidation.model.ContentPdfValidationResponse;
import com.eagle.contentvalidation.repo.ContentValidationRepoServiceImpl;
import com.eagle.contentvalidation.repo.model.PdfDocValidationResponse;
import com.eagle.contentvalidation.service.ContentProviderRestHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class ContentValidationController {

	@Autowired
	private ContentValidationRepoServiceImpl contentValidationRepoService;

	@Autowired
	private ContentProviderRestHandlerService restHandler;

	@PostMapping(value = "/startPdfProfanity", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ContentPdfValidationResponse> startContentPdfProfanity(
			@RequestBody ContentPdfValidation contentPdfValidation){
		restHandler.handleStartContentValidationRequest(contentPdfValidation);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/getPdfProfanity", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<PdfDocValidationResponse> getContentPdfProfanity(
			@RequestBody ContentPdfValidation contentPdfValidation) {
		return new ResponseEntity<>(contentValidationRepoService.getContentValidationResponseForFile(contentPdfValidation.getContentId(), contentPdfValidation.getFileName()), HttpStatus.OK);
	}
	
	@PostMapping(value = "/getLocalPdfProfanity", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<PdfDocValidationResponse> getLocalContentPdfProfanity(
			@RequestBody ContentPdfValidation contentPdfValidation) {
		return new ResponseEntity<>(contentValidationRepoService.getContentValidationResponseForFile(
				contentPdfValidation.getContentId(), contentPdfValidation.getFileName()), HttpStatus.OK);
	}

	@GetMapping(value = "/getPdfProfanityForContent/{contentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PdfDocValidationResponse>> getContentPdfProfanity(@PathVariable("contentId") String contentId) {
		return new ResponseEntity<>(contentValidationRepoService.getContentValidationResponse(contentId), HttpStatus.OK);
	}
}
