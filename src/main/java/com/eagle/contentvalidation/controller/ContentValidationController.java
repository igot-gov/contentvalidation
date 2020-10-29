package com.eagle.contentvalidation.controller;

import com.eagle.contentvalidation.model.ProfanityResponseWrapper;
import com.eagle.contentvalidation.service.ContentValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
}
