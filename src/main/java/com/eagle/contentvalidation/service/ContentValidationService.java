package com.eagle.contentvalidation.service;

import com.eagle.contentvalidation.model.HierarchyResponse;
import com.eagle.contentvalidation.model.Profanity;
import com.eagle.contentvalidation.model.ProfanityResponseWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ContentValidationService {

    public ProfanityResponseWrapper getTheProfanityCheckList(InputStream fileInputStream, HierarchyResponse hierarchyResponse) throws IOException;

    public ProfanityResponseWrapper validateContent(String rootOrg, String org, String contentId, String userId) throws IOException;
}
