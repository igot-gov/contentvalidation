package org.sunbird.contentvalidation.model.contentsearch.model;


import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.sunbird.contentvalidation.model.contentsearch.groups.ValidationGroupAuthoringToolSearch;
import org.sunbird.contentvalidation.model.contentsearch.groups.ValidationGroupGeneralSearch;

import javax.validation.Valid;

@Service
@Validated
public class MultiLingualIntegratedSearchValidator {

	@Validated(ValidationGroupGeneralSearch.class)
	public ValidatedSearchData doGeneralSearchValidations(@Valid ValidatedSearchData validatedSearchData) {
		return validatedSearchData;
	}
	
	@Validated(ValidationGroupAuthoringToolSearch.class)
	public ValidatedSearchData doAuthoringSearchValidations(@Valid ValidatedSearchData validatedSearchData) {
		return validatedSearchData;
	}
	
	@Validated
	public ValidatedSearchData doValidations(@Valid ValidatedSearchData validatedSearchData) {
		return validatedSearchData;
	}
}
