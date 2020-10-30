package com.eagle.contentvalidation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profanity {

    private String text_original;

    private String text_tagged;

    private List<String> possible_profanity;

    private Integer possible_profane_word_count;

    private List<ProfanityClassification> line_analysis;

    private ProfanityClassification overall_text_classification;

    private List<HashMap<String, Object>> possible_profanity_frequency;

}
