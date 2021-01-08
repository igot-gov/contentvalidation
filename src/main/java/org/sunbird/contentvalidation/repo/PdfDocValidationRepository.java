package org.sunbird.contentvalidation.repo;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.sunbird.contentvalidation.repo.model.PdfDocValidationResponse;
import org.sunbird.contentvalidation.repo.model.PdfDocValidationResponsePrimaryKey;

import java.util.List;

public interface PdfDocValidationRepository
		extends CassandraRepository<PdfDocValidationResponse, PdfDocValidationResponsePrimaryKey> {

	@Query("select * from pdf_validation_response where content_id=?0 and pdf_file_name=?1 ")
	public PdfDocValidationResponse findProgressByContentIdAndPdfFileName(String contentId, String pdfFileName);

	@Query("select * from pdf_validation_response where content_id=?0")
	public List<PdfDocValidationResponse> findProgressByContentId(String contentId);

	@Query("select * from pdf_validation_response where content_id in ?0")
	public List<PdfDocValidationResponse> findProgressByContentIds(List<String> contentIds);

}
