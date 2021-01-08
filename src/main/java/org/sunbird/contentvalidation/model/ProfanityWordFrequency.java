package org.sunbird.contentvalidation.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@UserDefinedType("profanity_word_frequency")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfanityWordFrequency {

	@Column("no_of_occurrence")
	private Integer no_of_occurrence;

	@Column("page_occurred")
	private Set<Integer> pageOccurred;

	@Column("word")
	private String word;

	@Column("level")
	private String level;

	@Column("category")
	private String category;

	public void addPageOccurred(int page) {
		if (pageOccurred == null || pageOccurred.isEmpty()) {
			pageOccurred = new HashSet<>();
		}
		pageOccurred.add(page);
	}
}
