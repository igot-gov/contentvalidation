package com.eagle.contentvalidation.service.impl;

import com.eagle.contentvalidation.config.Configuration;
import com.eagle.contentvalidation.config.Constants;
import com.eagle.contentvalidation.model.HierarchyResponse;
import com.eagle.contentvalidation.service.ContentProviderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.NoSuchElementException;

@Service
public class ContentProviderServiceImpl implements ContentProviderService {

    @Autowired
    private OutboundRequestHandlerServiceImpl outboundRequestHandlerService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public InputStream getContentFile(String downloadUrl) {
        if (downloadUrl.contains(Constants.DOWNLOAD_URL_PREFIX)) {
            downloadUrl = downloadUrl.replace(Constants.DOWNLOAD_URL_PREFIX, configuration.getContentServiceHost());
        }
        StringBuilder url = new StringBuilder(downloadUrl);
        byte[] byteStream = outboundRequestHandlerService.fetchByteStream(url);
        return new ByteArrayInputStream(byteStream);
    }

    @Override
    public HierarchyResponse getHeirarchyResponse(String rootOrg, String org, String contentId, String userId) {
        StringBuilder url = new StringBuilder();
        url.append(configuration.getLexCoreServiceHost()).append(configuration.getHeirarchySearchPath().replace(Constants.CONTENT_ID_REPLACER, contentId));
        HashMap<String, Object> request = new HashMap<>();
        request.put(Constants.ROOT_ORG_CONSTANT, rootOrg);
        request.put(Constants.ORG_CONSTANT, org);
        request.put(Constants.USER_ID_CONSTANT, userId);
        request.put(Constants.FIELD_PASSED_CONSTANT, Constants.FIELDS_PASSED);
        request.put(Constants.FETCH_ONE_LEVEL_CONSTANT, Constants.FETCH_ON_LEVEL);
        request.put(Constants.SKIP_ACCESS_CHECK_CONSTANT, Constants.SKIP_ACCESS_CHECK);
        request.put(Constants.FIELDS_CONSTANT, Constants.MINIMUL_FIELDS);
        Object recieviedResponse = outboundRequestHandlerService.fetchResultUsingPost(url, request);
        HierarchyResponse response = mapper.convertValue(recieviedResponse, HierarchyResponse.class);
        if (ObjectUtils.isEmpty(response) || StringUtils.isEmpty(response.getDownloadUrl())) {
            throw new NoSuchElementException();
        }
        return response;
    }
}
