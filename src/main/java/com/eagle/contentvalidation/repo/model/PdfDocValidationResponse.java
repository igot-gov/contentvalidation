package com.eagle.contentvalidation.repo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.eagle.contentvalidation.model.ProfanityWordFrequency;

import lombok.Getter;
import lombok.Setter;

@Table("pdf_validation_response")
@Getter
@Setter
public class PdfDocValidationResponse {
	@PrimaryKey
	private PdfDocValidationResponsePrimaryKey primaryKey;

	@Column("isCompleted")
	private boolean isCompleted;

	@Column("total_pages")
	private Integer total_pages;

	@Column("profanity_word_count")
	private Integer profanity_word_count;

	@Column("total_page_images")
	private Integer total_page_images;

	@Column("score")
	private double score;

	@Column("image_occrances")
	private String image_occurances;

	@Column("overall_text_classification")
	private String overall_text_classification;

	@Column("error_message")
	private String errorMessage;

	@Column("profanity_word_list")
	private List<ProfanityWordFrequency> profanityWordList;

	public void addProfanityWordFrequency(List<ProfanityWordFrequency> wordFrequencyList) {
		if (profanityWordList == null || profanityWordList.isEmpty()) {
			profanityWordList = new ArrayList<ProfanityWordFrequency>();
		}
		profanityWordList.addAll(wordFrequencyList);
	}

	public void addProfanityWordFrequency(ProfanityWordFrequency wordFrequency) {
		if (profanityWordList == null || profanityWordList.isEmpty()) {
			profanityWordList = new ArrayList<ProfanityWordFrequency>();
		}
		profanityWordList.add(wordFrequency);
	}

	public void incrementTotalPages() {
		if (total_pages == null) {
			total_pages = 1;
		} else {
			total_pages++;
		}
	}

	public void incrementProfanityWordCount() {
		if (profanity_word_count == null) {
			profanity_word_count = 1;
		} else {
			profanity_word_count++;
		}
	}
}
