package com.eagle.contentvalidation.service;

import com.eagle.contentvalidation.model.HierarchyResponse;

import java.io.InputStream;

public interface ContentProviderService {

	public InputStream getContentFile(String downloadUrl);

	public HierarchyResponse getHeirarchyResponse(String rootOrg, String org, String contentId, String userId);


}
