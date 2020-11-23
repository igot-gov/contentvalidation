package com.eagle.contentvalidation.service;

import java.io.IOException;

import com.eagle.contentvalidation.model.ContentPdfValidation;
import com.eagle.contentvalidation.model.ProfanityResponseWrapper;
import com.eagle.contentvalidation.repo.model.PdfDocValidationResponse;

public interface ContentValidationService {

	public ProfanityResponseWrapper validateContent(String rootOrg, String org, String contentId, String userId)
			throws IOException;

	public PdfDocValidationResponse validatePdfContent(ContentPdfValidation contentPdfValidation)
			throws IOException;

	public PdfDocValidationResponse validateLocalPdfContent(ContentPdfValidation contentPdfValidation)
			throws IOException;
}
