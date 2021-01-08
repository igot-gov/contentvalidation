package org.sunbird.contentvalidation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunbird.contentvalidation.config.Configuration;
import org.sunbird.contentvalidation.model.ContentPdfValidation;
import org.sunbird.contentvalidation.producer.StartContentValidationProducer;
import org.sunbird.contentvalidation.service.ContentProviderRestHandlerService;

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
