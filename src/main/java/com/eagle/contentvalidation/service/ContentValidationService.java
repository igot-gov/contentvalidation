package com.eagle.contentvalidation.service;

import com.eagle.contentvalidation.model.Profanity;
import com.eagle.contentvalidation.model.ProfanityResponseWrapper;

import java.io.IOException;
import java.util.List;

public interface ContentValidationService {

    public ProfanityResponseWrapper getTheProfanityCheckList() throws IOException;
}
