package com.eagle.contentvalidation.service.impl;

import java.util.Arrays;
import java.util.Map;

import com.eagle.contentvalidation.config.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.eagle.contentvalidation.config.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class OutboundRequestHandlerServiceImpl {
    Logger logger = LogManager.getLogger(OutboundRequestHandlerServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Configuration configuration;

    /**
     * @param uri
     * @param request
     * @return
     * @throws Exception
     */
    public Object fetchResultUsingPost(StringBuilder uri, Object request) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Object response = null;
        StringBuilder str = new StringBuilder(this.getClass().getCanonicalName()).append(Constants.FETCH_RESULT_CONSTANT)
                .append(System.lineSeparator());
        str.append(Constants.URI_CONSTANT).append(uri.toString()).append(System.lineSeparator());
        try {
            str.append(Constants.REQUEST_CONSTANT).append(mapper.writeValueAsString(request)).append(System.lineSeparator());
            String message = str.toString();
            logger.info(message);
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Object> entity = new HttpEntity<>(request, headers);
            response = restTemplate.postForObject(uri.toString(), entity, Map.class);
        } catch (HttpClientErrorException e) {
            logger.error(Constants.SERVICE_ERROR_CONSTANT, e);
        } catch (Exception e) {
            logger.error(Constants.EXTERNAL_SERVICE_ERROR_CODE, e);
        }
        return response;
    }

    /**
     * @param uri
     * @return
     * @throws Exception
     */
    public Object fetchResult(StringBuilder uri) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Object response = null;
        StringBuilder str = new StringBuilder(this.getClass().getCanonicalName()).append(Constants.FETCH_RESULT_CONSTANT)
                .append(System.lineSeparator());
        str.append(Constants.URI_CONSTANT).append(uri.toString()).append(System.lineSeparator());
        try {
            String message = str.toString();
            logger.debug(message);
            response = restTemplate.getForObject(uri.toString(), Map.class);
        } catch (HttpClientErrorException e) {
            logger.error(Constants.SERVICE_ERROR_CONSTANT, e);
        } catch (Exception e) {
            logger.error(Constants.EXTERNAL_SERVICE_ERROR_CODE, e);
        }
        return response;
    }

    /**
     * @param uri
     * @return Return the byte stream
     */
    public byte[] fetchByteStream(StringBuilder uri) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        StringBuilder str = new StringBuilder(this.getClass().getCanonicalName()).append(Constants.FETCH_RESULT_CONSTANT)
                .append(System.lineSeparator());
        str.append(Constants.URI_CONSTANT).append(uri.toString()).append(System.lineSeparator());
        ResponseEntity<byte[]> responseEntity = null;
        try {
            String message = str.toString();
            logger.debug(message);
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            responseEntity = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, byte[].class);
        } catch (HttpClientErrorException e) {
            logger.error(Constants.SERVICE_ERROR_CONSTANT, e);
        } catch (Exception e) {
            logger.error(Constants.EXTERNAL_SERVICE_ERROR_CODE, e);
        }
        if (ObjectUtils.isEmpty(responseEntity))
            return new byte[0];
        return responseEntity.getBody();
    }
}
