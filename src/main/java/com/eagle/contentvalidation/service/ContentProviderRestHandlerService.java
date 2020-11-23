package com.eagle.contentvalidation.service;

import org.springframework.stereotype.Service;

import com.eagle.contentvalidation.model.ContentPdfValidation;

@Service
public interface ContentProviderRestHandlerService {

	public void handleStartContentValidationRequest(ContentPdfValidation contentPdfValidation);
}
