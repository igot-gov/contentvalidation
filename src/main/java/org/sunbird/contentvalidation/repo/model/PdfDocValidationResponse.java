package org.sunbird.contentvalidation.repo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;
import org.sunbird.contentvalidation.model.ProfanityWordFrequency;

@Table("pdf_validation_response")
@Getter
@Setter
public class PdfDocValidationResponse {
	@PrimaryKey
	private PdfDocValidationResponsePrimaryKey primaryKey;

	@Column("isCompleted")
	private boolean isCompleted;

	@Column("no_of_pages_completed")
	private Integer noOfPagesCompleted;

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
			profanityWordList = new ArrayList<>();
		}
		profanityWordList.addAll(wordFrequencyList);
	}

	public void addProfanityWordFrequency(ProfanityWordFrequency wordFrequency) {
		if (profanityWordList == null || profanityWordList.isEmpty()) {
			profanityWordList = new ArrayList<>();
		}
		profanityWordList.add(wordFrequency);
	}

	public void incrementTotalNoOfPagesCompleted() {
		if (noOfPagesCompleted == null) {
			noOfPagesCompleted = 1;
		} else {
			noOfPagesCompleted++;
		}
	}

	public void incrementTotalPagesImages(){
		if (total_page_images == null) {
			total_page_images = 1;
		} else {
			total_page_images++;
		}
	}

	public void incrementProfanityWordCount() {
		if (profanity_word_count == null) {
			profanity_word_count = 1;
		} else {
			profanity_word_count++;
		}
	}

	public void addImageOccurances(int pageIndex) {
		if (StringUtils.isEmpty(this.image_occurances)) {
			this.image_occurances = String.valueOf(pageIndex+1);
		} else {
			this.image_occurances = this.image_occurances + ", " + (pageIndex+1);
		}
	}


}
