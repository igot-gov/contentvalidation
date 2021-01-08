package org.sunbird.contentvalidation.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class ProfanityWordCount {

    private String offenceCategory;

    private Set<Integer> occurenceOnPage;

    private int totalWordCount;
}
