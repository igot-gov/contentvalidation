package com.eagle.contentvalidation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class Configuration {
	@Value("${notify.service.host}")
	private String notifyServiceHost;

	@Value("${notify.service.path}")
	private String notifyServicePath;

	@Value("${s3.service.host}")
	private String contentServiceHost;

	@Value("${s3.service.path}")
	private String contentServicePath;

	@Value("${profanity.service.host}")
	private String profanityServiceHost;

	@Value("${profanity.service.path}")
	private String profanityServicePath;
}
