package com.eagle.contentvalidation.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class ProfanityCategorial {

    private int count;

    private HashMap<String, String> details;

}