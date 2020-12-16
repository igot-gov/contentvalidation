package com.eagle.contentvalidation.repo;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.eagle.contentvalidation.repo.model.PdfDocValidationResponse;
import com.eagle.contentvalidation.repo.model.PdfDocValidationResponsePrimaryKey;

import java.util.List;

public interface PdfDocValidationRepository
		extends CassandraRepository<PdfDocValidationResponse, PdfDocValidationResponsePrimaryKey> {

	@Query("select * from pdf_validation_response where content_id=?0 and pdf_file_name=?1 ")
	public PdfDocValidationResponse findProgressByContentIdAndPdfFileName(String contentId, String pdfFileName);

	@Query("select * from pdf_validation_response where content_id=?0")
	public List<PdfDocValidationResponse> findProgressByContentId(String contentId);

}
