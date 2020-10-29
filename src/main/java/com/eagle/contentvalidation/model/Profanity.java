package com.eagle.contentvalidation.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Profanity {

    private String textOriginal;

    private String textTagged;

    private List<String> possibleProfanity;

    private Integer possibleProfaneWordCount;

    private List<ProfanityClassification> lineAnalysis;

    private ProfanityClassification overallTextClassification;

}
