package com.eagle.contentvalidation.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class ProfanityWordCount {

    private String OffenceCategory;

    private Set<Integer> OccurenceOnPage;

    private int totalWordCount;
}
