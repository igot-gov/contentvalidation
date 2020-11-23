package com.eagle.contentvalidation.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfanityClassification {

    private String classification;

    private String text;

    private Double probability;
}
