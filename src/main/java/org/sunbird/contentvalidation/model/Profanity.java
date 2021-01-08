package org.sunbird.contentvalidation.model;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profanity {

	private String text_original;

	private String text_tagged;

	private List<String> possible_profanity;

	private Integer possible_profane_word_count;

	private List<ProfanityLineClassification> line_analysis;

	private ProfanityClassification overall_text_classification;

	private List<ProfanityWordFrequency> possible_profanity_frequency;

	private HashMap<String, ProfanityCategorial> possible_profanity_categorical;
}
