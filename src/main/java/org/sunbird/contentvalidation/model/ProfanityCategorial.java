package org.sunbird.contentvalidation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfanityCategorial {

    private int count;

    private HashMap<String, String> details;

}