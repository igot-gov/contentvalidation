package com.eagle.contentvalidation.service;

import com.eagle.contentvalidation.model.ContentPdfValidation;
import com.eagle.contentvalidation.model.ContentPdfValidationResponse;
import com.eagle.contentvalidation.model.ProfanityResponseWrapper;

import java.io.IOException;

public interface ContentValidationService {

	public ProfanityResponseWrapper validateContent(String rootOrg, String org, String contentId, String userId)
			throws IOException;

	public ContentPdfValidationResponse validatePdfContent(ContentPdfValidation contentPdfValidation)
			throws IOException;

	public ContentPdfValidationResponse validateLocalPdfContent(ContentPdfValidation contentPdfValidation)
			throws IOException;
}
