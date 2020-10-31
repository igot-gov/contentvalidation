package com.eagle.contentvalidation.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfanityWordFrequency {
	private int no_of_occurance;
	private Set<Integer> pageOccurred = Collections.emptySet();
	private String word;
	private String level;
	private String category;

	public void addPageOccurred(int page) {
		if (pageOccurred.isEmpty()) {
			pageOccurred = new HashSet<Integer>();
		}
		pageOccurred.add(page);
	}
}
