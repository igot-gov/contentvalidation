package org.sunbird.contentvalidation.model.contentsearch.model;

import javax.validation.Valid;
import java.io.Serializable;

public class SearchRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Valid
    private ValidatedSearchData request;

    public ValidatedSearchData getRequest() {
        return this.request;
    }

    public void setRequest(ValidatedSearchData request) {
        this.request = request;
    }
}
