package org.sunbird.contentvalidation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfanityLineClassification {
	private String classification;

	private String line;

	private Double probability;
}
