package org.sunbird.contentvalidation.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class ProfanityResponseWrapper {

    private double overAllOffensivescore;

    private String fileName;

    private int totalPageUploaded;

    private int profanityWordCount;

    private int pagesWithImages;

    private HashMap<String, ProfanityWordCount> profanityClassifications;
    
    private Set<Integer> imagesOccurrenceOnPageNo;

    private List<Profanity> profanityList;

}
