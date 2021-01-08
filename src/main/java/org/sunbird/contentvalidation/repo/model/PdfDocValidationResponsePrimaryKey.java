package org.sunbird.contentvalidation.repo.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@PrimaryKeyClass
@Getter
@Setter
@Builder
public class PdfDocValidationResponsePrimaryKey {

	@PrimaryKeyColumn(name = "content_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String contentId;

	@PrimaryKeyColumn(name = "pdf_file_name", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String pdfFileName;
}
