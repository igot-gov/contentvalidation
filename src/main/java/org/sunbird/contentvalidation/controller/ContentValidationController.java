package org.sunbird.contentvalidation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sunbird.contentvalidation.model.ContentPdfValidation;
import org.sunbird.contentvalidation.model.ContentPdfValidationResponse;
import org.sunbird.contentvalidation.repo.ContentValidationRepoServiceImpl;
import org.sunbird.contentvalidation.repo.model.PdfDocValidationResponse;
import org.sunbird.contentvalidation.service.ContentProviderRestHandlerService;

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
	public ResponseEntity<List<PdfDocValidationResponse>> getContentPdfProfanity(@RequestHeader("rootOrg") String rootOrg, @RequestHeader("wid") String wid, @PathVariable("contentId") String contentId) {
		return new ResponseEntity<>(contentValidationRepoService.getContentValidationResponse(rootOrg, wid, contentId), HttpStatus.OK);
	}
}
