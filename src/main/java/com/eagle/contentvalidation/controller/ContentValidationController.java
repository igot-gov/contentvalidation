package com.eagle.contentvalidation.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.contentvalidation.model.ContentPdfValidation;
import com.eagle.contentvalidation.model.ContentPdfValidationResponse;
import com.eagle.contentvalidation.model.ProfanityResponseWrapper;
import com.eagle.contentvalidation.repo.ContentValidationRepoServiceImpl;
import com.eagle.contentvalidation.repo.model.PdfDocValidationResponse;
import com.eagle.contentvalidation.service.ContentProviderRestHandlerService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/v1")
public class ContentValidationController {

	@Autowired
	private ContentValidationRepoServiceImpl contentValidationRepoService;

	@Autowired
	private ContentProviderRestHandlerService restHandler;

	@GetMapping("/checkProfanity/{contentId}/{userId}")
	public ResponseEntity<ProfanityResponseWrapper> checkContentProfanity(@RequestHeader("rootOrg") String rootOrg,
			@RequestHeader("org") String org, @PathVariable("contentId") String contentId,
			@PathVariable("userId") String userId) throws IOException {
		// ProfanityResponseWrapper response =
		// contentValidationService.validateContent(rootOrg, org, contentId, userId);
		return new ResponseEntity<>(new ProfanityResponseWrapper(), HttpStatus.OK);
	}

	@PostMapping(value = "/checkPdfProfanity", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<PdfDocValidationResponse> checkContentPdfProfanity(
			@RequestBody ContentPdfValidation contentPdfValidation) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		// PdfDocValidationResponse response = contentValidationService
		// .validatePdfContent(mapper.convertValue(contentPdfValidation,
		// ContentPdfValidation.class));
		return new ResponseEntity<>(new PdfDocValidationResponse(), HttpStatus.OK);
	}

	@PostMapping(value = "/checkLocalPdfProfanity", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<PdfDocValidationResponse> checkLocalContentPdfProfanity(
			@RequestBody ContentPdfValidation contentPdfValidation) throws IOException {
		// PdfDocValidationResponse response =
		// contentValidationService.validateLocalPdfContent(contentPdfValidation);
		return new ResponseEntity<>(new PdfDocValidationResponse(), HttpStatus.OK);
	}

	@PostMapping(value = "/startPdfProfanity", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ContentPdfValidationResponse> startContentPdfProfanity(
			@RequestBody ContentPdfValidation contentPdfValidation) throws IOException {
		restHandler.handleStartContentValidationRequest(contentPdfValidation);
		return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/getPdfProfanity", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<PdfDocValidationResponse> getContentPdfProfanity(
			@RequestBody ContentPdfValidation contentPdfValidation) {
		return new ResponseEntity<>(contentValidationRepoService.getContentValidationResponse(
				contentPdfValidation.getContentId(), contentPdfValidation.getFileName()), HttpStatus.OK);
	}

	@PostMapping(value = "/startLocalPdfProfanity", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ContentPdfValidationResponse> startLocalContentPdfProfanity(
			@RequestBody ContentPdfValidation contentPdfValidation) throws IOException {
		restHandler.handleStartContentValidationRequest(contentPdfValidation);
		return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/getLocalPdfProfanity", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<PdfDocValidationResponse> getLocalContentPdfProfanity(
			@RequestBody ContentPdfValidation contentPdfValidation) {
		return new ResponseEntity<>(contentValidationRepoService.getContentValidationResponse(
				contentPdfValidation.getContentId(), contentPdfValidation.getFileName()), HttpStatus.OK);
	}
}
