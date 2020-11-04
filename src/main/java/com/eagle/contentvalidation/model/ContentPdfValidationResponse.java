package com.eagle.contentvalidation.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentPdfValidationResponse {
	private int total_pages;
	private int profanity_word_count;
	private int total_page_images;
	private double score;
	private String pdfFileName;
	private String image_occurances;
	private String overall_text_classification;
	private List<ProfanityWordFrequency> profanityWordList = Collections.emptyList();

	public void incrementTotalPages() {
		total_pages++;
	}

	public void incrementProfanityWordCount() {
		profanity_word_count++;
	}

	public void addProfanityWordDetails(ProfanityWordFrequency profanityWord) {
		if (profanityWordList.isEmpty()) {
			profanityWordList = new ArrayList<>();
		}
		profanityWordList.add(profanityWord);
	}
}
