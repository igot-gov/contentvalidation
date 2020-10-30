package com.eagle.contentvalidation.service;

import java.io.IOException;
import java.util.Map;

import com.eagle.contentvalidation.model.ContentPdfValidation;
import com.eagle.contentvalidation.model.ProfanityResponseWrapper;

public interface ContentValidationService {

	public ProfanityResponseWrapper validateContent(String rootOrg, String org, String contentId, String userId)
			throws IOException;

	public ProfanityResponseWrapper validatePdfContent(ContentPdfValidation contentPdfValidation) throws IOException;
}
