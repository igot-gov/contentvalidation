package org.sunbird.contentvalidation.service.impl;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.sunbird.contentvalidation.config.Constants;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OutboundRequestHandlerServiceImpl {
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * @param uri
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Object fetchResultUsingPost(String uri, Object request) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		Object response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<Object> entity = new HttpEntity<>(request, headers);
			response = restTemplate.postForObject(uri, entity, Map.class);
			if (log.isDebugEnabled()) {
				StringBuilder str = new StringBuilder(this.getClass().getCanonicalName())
						.append(Constants.FETCH_RESULT_CONSTANT).append(System.lineSeparator());
				str.append(Constants.URI_CONSTANT).append(uri).append(System.lineSeparator());
				str.append(Constants.REQUEST_CONSTANT).append(mapper.writeValueAsString(request))
						.append(System.lineSeparator());
				str.append(Constants.RESPONSE_CONSTANT).append(mapper.writeValueAsString(response))
						.append(System.lineSeparator());
				log.debug(str.toString());
			}
		} catch (HttpClientErrorException e) {
			log.error(Constants.SERVICE_ERROR_CONSTANT, e);
		} catch (Exception e) {
			log.error(Constants.EXTERNAL_SERVICE_ERROR_CODE, e);
		}
		return response;
	}

	/**
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public Object fetchResult(String uri) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		Object response = null;
		try {
			if (log.isDebugEnabled()) {
				StringBuilder str = new StringBuilder(this.getClass().getCanonicalName())
						.append(Constants.FETCH_RESULT_CONSTANT).append(System.lineSeparator());
				str.append(Constants.URI_CONSTANT).append(uri).append(System.lineSeparator());
				log.debug(str.toString());
			}
			response = restTemplate.getForObject(uri, Map.class);
		} catch (HttpClientErrorException e) {
			log.error(Constants.SERVICE_ERROR_CONSTANT, e);
		} catch (Exception e) {
			log.error(Constants.EXTERNAL_SERVICE_ERROR_CODE, e);
		}
		return response;
	}

	/**
	 * @param uri
	 * @return Return the byte stream
	 */
	public byte[] fetchByteStream(String uri) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		ResponseEntity<byte[]> responseEntity = null;
		try {
			if (log.isDebugEnabled()) {
				StringBuilder str = new StringBuilder(this.getClass().getCanonicalName())
						.append(Constants.FETCH_RESULT_CONSTANT).append(System.lineSeparator());
				str.append(Constants.URI_CONSTANT).append(uri).append(System.lineSeparator());
				log.debug(str.toString());
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
			HttpEntity<String> entity = new HttpEntity<>(headers);
			responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, byte[].class);
		} catch (HttpClientErrorException e) {
			log.error(Constants.SERVICE_ERROR_CONSTANT, e);
		} catch (Exception e) {
			log.error(Constants.EXTERNAL_SERVICE_ERROR_CODE, e);
		}
		if (ObjectUtils.isEmpty(responseEntity))
			return new byte[0];
		return responseEntity.getBody();
	}
}
