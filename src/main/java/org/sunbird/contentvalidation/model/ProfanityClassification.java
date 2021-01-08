package org.sunbird.contentvalidation.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfanityClassification {

    private String classification;

    private String text;

    private Double probability;
}
