package com.eagle.contentvalidation.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.eagle.contentvalidation.config.Constants;
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
	public Object fetchResultUsingPost(String uri, Object request, Class classType) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		Object response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<Object> entity = new HttpEntity<>(request, headers);
			response = restTemplate.postForObject(uri, entity, classType);
//			if (log.isDebugEnabled()) {
				StringBuilder str = new StringBuilder(this.getClass().getCanonicalName())
						.append(Constants.FETCH_RESULT_CONSTANT).append(System.lineSeparator());
				str.append(Constants.URI_CONSTANT).append(uri).append(System.lineSeparator());
				str.append(Constants.REQUEST_CONSTANT).append(mapper.writeValueAsString(request))
						.append(System.lineSeparator());
				str.append(Constants.RESPONSE_CONSTANT).append(mapper.writeValueAsString(response))
						.append(System.lineSeparator());
				log.info(str.toString());
//			}
		} catch (HttpClientErrorException e) {
			log.error(Constants.SERVICE_ERROR_CONSTANT, e);
		} catch (Exception e) {
			log.error(Constants.EXTERNAL_SERVICE_ERROR_CODE, e);
		}
		return response;
	}
	
	public Object fetchResultUsingPost(String uri, Object request) {
		return fetchResultUsingPost(uri, request, Map.class);
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

	public Object fetchResultsForImages(String uri, File imageFile, String fileName) {
		Object response = null;
		try {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.MULTIPART_FORM_DATA); // Also tried with multipart...

			MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();

			ByteArrayResource bytes = new ByteArrayResource(Files.readAllBytes(imageFile.toPath())) {
				@Override
				public String getFilename() {
					return fileName;
				}
			};

			HttpHeaders pictureHeader = new HttpHeaders();
			ContentDisposition contentDisposition = ContentDisposition.builder("form-data").name("image")
					.filename(fileName).build();
			pictureHeader.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
			pictureHeader.setContentType(MediaType.IMAGE_PNG);
			HttpEntity<ByteArrayResource> picturePart = new HttpEntity<>(bytes, pictureHeader);
			multipartRequest.add("file", picturePart);
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity(multipartRequest, header);

			HttpMessageConverter<Object> jackson = new MappingJackson2HttpMessageConverter();
			ByteArrayHttpMessageConverter resource = new ByteArrayHttpMessageConverter();
			FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
			formHttpMessageConverter.addPartConverter(resource);

			RestTemplate restTemplate = new RestTemplate(Arrays.asList(jackson, resource, formHttpMessageConverter));

			log.info("URL -> " + uri + ", Request --> " + requestEntity);

			response = restTemplate.postForEntity(uri, requestEntity, Map.class);

			if (log.isDebugEnabled()) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
				StringBuilder str = new StringBuilder(this.getClass().getCanonicalName())
						.append(Constants.FETCH_RESULT_CONSTANT).append(System.lineSeparator());
				str.append(Constants.URI_CONSTANT).append(uri).append(System.lineSeparator());
				str.append(Constants.REQUEST_CONSTANT).append(mapper.writeValueAsString(fileName))
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
}
