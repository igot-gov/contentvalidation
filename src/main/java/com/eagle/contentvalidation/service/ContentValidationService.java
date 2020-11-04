package com.eagle.contentvalidation.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.eagle.contentvalidation.model.ContentPdfValidation;
import com.eagle.contentvalidation.model.ContentPdfValidationResponse;
import com.eagle.contentvalidation.model.ProfanityResponseWrapper;

public interface ContentValidationService {

	public ProfanityResponseWrapper validateContent(String rootOrg, String org, String contentId, String userId)
			throws IOException;

	public ContentPdfValidationResponse validatePdfContent(ContentPdfValidation contentPdfValidation)
			throws IOException;

	public ContentPdfValidationResponse validateLocalPdfContent(ContentPdfValidation contentPdfValidation)
			throws IOException;
}
