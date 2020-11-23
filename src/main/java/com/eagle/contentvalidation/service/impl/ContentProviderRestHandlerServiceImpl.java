package com.eagle.contentvalidation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eagle.contentvalidation.config.Configuration;
import com.eagle.contentvalidation.model.ContentPdfValidation;
import com.eagle.contentvalidation.producer.StartContentValidationProducer;
import com.eagle.contentvalidation.service.ContentProviderRestHandlerService;

@Service
public class ContentProviderRestHandlerServiceImpl implements ContentProviderRestHandlerService {

	@Autowired
	StartContentValidationProducer producer;

	@Autowired
	Configuration config;

	@Override
	public void handleStartContentValidationRequest(ContentPdfValidation contentPdfValidation) {
		producer.push(config.getStartContentValidationTopic(), contentPdfValidation);
	}
}
