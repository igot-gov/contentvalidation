/*               "Copyright 2020 Infosys Ltd.
               Use of this source code is governed by GPL v3 license that can be found in the LICENSE file or at https://opensource.org/licenses/GPL-3.0
               This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License version 3" */
package com.eagle.contentvalidation.model.contentsearch.model;


import com.eagle.contentvalidation.model.contentsearch.groups.ValidationGroupAuthoringToolSearch;
import com.eagle.contentvalidation.model.contentsearch.groups.ValidationGroupGeneralSearch;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
