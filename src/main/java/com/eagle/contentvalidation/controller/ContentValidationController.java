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
import com.eagle.contentvalidation.service.ContentValidationService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/v1")
public class ContentValidationController {

	@Autowired
	private ContentValidationService contentValidationService;

	@GetMapping("/checkProfanity/{contentId}/{userId}")
	public ResponseEntity<ProfanityResponseWrapper> checkContentProfanity(@RequestHeader("rootOrg") String rootOrg,
			@RequestHeader("org") String org, @PathVariable("contentId") String contentId,
			@PathVariable("userId") String userId) throws IOException {
		ProfanityResponseWrapper response = contentValidationService.validateContent(rootOrg, org, contentId, userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/checkPdfProfanity", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ContentPdfValidationResponse> checkContentPdfProfanity(
			@RequestBody ContentPdfValidation contentPdfValidation) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		ContentPdfValidationResponse response = contentValidationService
				.validatePdfContent(mapper.convertValue(contentPdfValidation, ContentPdfValidation.class));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
