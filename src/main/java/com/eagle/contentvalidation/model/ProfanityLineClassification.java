package com.eagle.contentvalidation.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfanityLineClassification {
	private String classification;

	private String line;

	private Double probability;
}
