package com.eagle.contentvalidation.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eagle.contentvalidation.repo.model.PdfDocValidationResponse;
import com.eagle.contentvalidation.repo.model.PdfDocValidationResponsePrimaryKey;

import lombok.extern.log4j.Log4j2;

import java.util.List;

@Service
@Log4j2
public class ContentValidationRepoServiceImpl {
	@Autowired
	PdfDocValidationRepository pdfRepo;

	public void insertStartData(String contentId, String fileName) {

		PdfDocValidationResponse pdfResponse = getContentValidationResponseForFile(contentId, fileName);
		log.info("Insert Start Data. Does any response existing ? {} ", pdfResponse != null);
		if (pdfResponse != null) {
			pdfResponse.setCompleted(false);
			pdfResponse.setErrorMessage("");
			pdfResponse.setImage_occurances("");
			pdfResponse.setOverall_text_classification("");
			pdfResponse.setProfanity_word_count(0);
			pdfResponse.setProfanityWordList(null);
			pdfResponse.setScore(0.0);
			pdfResponse.setTotal_page_images(0);
			pdfResponse.setNoOfPagesCompleted(0);
			pdfResponse.setTotal_pages(0);
		} else {
			pdfResponse = new PdfDocValidationResponse();
			pdfResponse.setPrimaryKey(
					PdfDocValidationResponsePrimaryKey.builder().contentId(contentId).pdfFileName(fileName).build());
			pdfResponse.setCompleted(false);
		}
		pdfRepo.save(pdfResponse);
	}

	public void updateContentValidationResult(PdfDocValidationResponse newResponse, boolean isCompleted) {
		PdfDocValidationResponse responseExisting = getContentValidationResponseForFile(
				newResponse.getPrimaryKey().getContentId(), newResponse.getPrimaryKey().getPdfFileName());
		if (responseExisting != null) {
			responseExisting.setOverall_text_classification(newResponse.getOverall_text_classification());
			responseExisting.setProfanity_word_count(newResponse.getProfanity_word_count());
			responseExisting.setProfanityWordList(newResponse.getProfanityWordList());
			responseExisting.setScore(newResponse.getScore());
			responseExisting.setNoOfPagesCompleted(newResponse.getNoOfPagesCompleted());
			responseExisting.setCompleted(isCompleted);
			responseExisting.setTotal_page_images(newResponse.getTotal_page_images());
			responseExisting.setProfanityWordList(newResponse.getProfanityWordList());
			responseExisting.setImage_occurances(newResponse.getImage_occurances());

			pdfRepo.save(responseExisting);
		} else {
			log.error("Failed to find existing record from latestResponse");
		}
	}

	public PdfDocValidationResponse getContentValidationResponseForFile(String contentId, String pdfFileName) {
		return pdfRepo.findProgressByContentIdAndPdfFileName(contentId, pdfFileName);
	}

	public List<PdfDocValidationResponse> getContentValidationResponse(String contentId){
		return pdfRepo.findProgressByContentId(contentId);
	}
}
