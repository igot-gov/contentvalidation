package org.sunbird.contentvalidation.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.sunbird.contentvalidation.config.Configuration;
import org.sunbird.contentvalidation.model.contentsearch.model.SearchRequest;
import org.sunbird.contentvalidation.model.contentsearch.model.SearchResponse;
import org.sunbird.contentvalidation.model.contentsearch.model.ValidatedSearchData;
import org.sunbird.contentvalidation.repo.model.PdfDocValidationResponse;
import org.sunbird.contentvalidation.repo.model.PdfDocValidationResponsePrimaryKey;
import org.sunbird.contentvalidation.service.impl.OutboundRequestHandlerServiceImpl;

import lombok.extern.log4j.Log4j2;

import java.util.*;

@Service
@Log4j2
public class ContentValidationRepoServiceImpl {
	@Autowired
	PdfDocValidationRepository pdfRepo;

	@Autowired
	private OutboundRequestHandlerServiceImpl requestHandlerService;

	@Autowired
	private Configuration configuration;

	@Autowired
	private ObjectMapper mapper;

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

	public List<PdfDocValidationResponse> getContentValidationResponse(String rootOrg, String wid, String contentId){
		return pdfRepo.findProgressByContentIds(getParentAndChildContentIds(rootOrg, wid, contentId));
	}

	public List<String> getParentAndChildContentIds(String rootOrg, String wid, String contentId) {
		List<String> contentIds = new ArrayList<>();
		ValidatedSearchData request = new ValidatedSearchData();
		request.setUuid(UUID.fromString(wid));
		request.setQuery(contentId);
		request.setRootOrg(rootOrg);
		request.setIsUserRecordEnabled(false);
		request.setPageNo(0);
		request.setPageSize(24);
		request.setAggregationsSorting(null);
		request.getFilters().setStatus(new ArrayList<>());
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setRequest(request);
		try {
			log.info("Request {}", mapper.writeValueAsString(searchRequest));
			SearchResponse response = mapper.convertValue(requestHandlerService.fetchResultUsingPost(configuration.getSbExtActorsModuleURL() + configuration.getSearchV5Path(), searchRequest), SearchResponse.class);
			ArrayList<HashMap<String, Object>> result = (ArrayList<HashMap<String, Object>>) ((HashMap<String, Object>) response.getResult().get("response")).get("result");
			log.info("Response of search request {}", mapper.writeValueAsString(response));
			if (result.stream().findFirst().isPresent()) {
				HashMap<String, Object> firstResult = result.stream().findFirst().get();
				if (((String) firstResult.get("mimeType")).equals("application/pdf"))
					contentIds.add((String) firstResult.get("identifier"));
				ArrayList<HashMap<String, Object>> children = (ArrayList<HashMap<String, Object>>) firstResult.get("children");
				children.forEach(map -> {
					if (((String) map.get("mimeType")).equals("application/pdf"))
						contentIds.add((String) map.get("identifier"));
				});

			}
			log.info("ContentIds {}", contentIds);
		}
		catch (JsonProcessingException e) {
			log.error("Parsing error occured!");
		}
		return contentIds;
	}
}
