package com.eagle.contentvalidation.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.eagle.contentvalidation.config.Configuration;
import com.eagle.contentvalidation.config.Constants;
import com.eagle.contentvalidation.model.contentsearch.model.SearchRequest;
import com.eagle.contentvalidation.model.contentsearch.model.SearchResponse;
import com.eagle.contentvalidation.model.contentsearch.model.ValidatedSearchData;
import com.eagle.contentvalidation.repo.model.PdfDocValidationResponse;
import com.eagle.contentvalidation.repo.model.PdfDocValidationResponsePrimaryKey;
import com.eagle.contentvalidation.service.impl.OutboundRequestHandlerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

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

	public List<PdfDocValidationResponse> getContentValidationResponse(String rootOrg, String wid, String contentId) {
//		Map<String, String> contentIdResponse = getParentAndChildContentIds(rootOrg, wid, contentId);
		Map<String, String> contentIdResponse = new HashMap<String, String>();
		try {
			contentIdResponse = getContentHierarchyDetails(rootOrg, contentId);
		} catch (JSONException e) {
			log.error(e);
		}
		List<String> contentIds = new ArrayList<String>(contentIdResponse.keySet());
		List<PdfDocValidationResponse> respList = pdfRepo.findProgressByContentIds(contentIds);
		Iterator<PdfDocValidationResponse> it = respList.iterator();
		while (it.hasNext()) {
			PdfDocValidationResponse resp = it.next();
			String artifactUrl = contentIdResponse.get(resp.getPrimaryKey().getContentId());
			if (artifactUrl == null || !artifactUrl.contains(resp.getPrimaryKey().getPdfFileName())) {
				it.remove();
			}
		}
		return respList;
	}

	private Map<String, String> getContentHierarchyDetails(String rootOrg, String contentId) throws JSONException {
		Map<String, String> contentIds = new HashMap<String, String>();
		JSONObject request = new JSONObject();
		JSONArray identifiers = new JSONArray();
		identifiers.put(contentId);
		request.put("identifier", identifiers);
		JSONArray fields = new JSONArray();
		for (String str : Constants.MINIMUL_FIELDS) {
			fields.put(str);
		}
		request.put("fields", fields);

		StringBuilder url = new StringBuilder();
		url.append(configuration.getAuthToolServiceHost()).append(configuration.getAuthToolServicePath());
		url.append("?rootOrg=").append(rootOrg);
		JSONArray response = mapper.convertValue(requestHandlerService.fetchResultUsingPost(url.toString(), request),
				JSONArray.class);
		if (response.length() > 0) {
			JSONObject content = response.getJSONObject(0);
			processResponse(contentIds, content);
		}

		return contentIds;
	}

	private Map<String, String> processResponse(Map<String, String> contentIds, JSONObject jObject)
			throws JSONException {
		if ("application/pdf".equalsIgnoreCase((String) jObject.get("mimeType"))) {
			String cId = (String) jObject.get("identifier");
			String aUrl = (String) jObject.get("artifactUrl");
			if (cId != null && aUrl != null) {
				contentIds.put(cId, aUrl);
			}
			JSONArray childs = (JSONArray) jObject.get("children");
			if (childs != null && childs.length() > 1) {
				for (int i = 0; i < childs.length(); i++) {
					processResponse(contentIds, (JSONObject) childs.get(0));
				}
			}
		}
		return contentIds;
	}

	public Map<String, String> getParentAndChildContentIds(String rootOrg, String wid, String contentId) {
		Map<String, String> contentIds = new HashMap<String, String>();
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
			SearchResponse response = mapper.convertValue(
					requestHandlerService.fetchResultUsingPost(
							configuration.getSbExtActorsModuleURL() + configuration.getSearchV5Path(), searchRequest),
					SearchResponse.class);
			ArrayList<HashMap<String, Object>> result = (ArrayList<HashMap<String, Object>>) ((HashMap<String, Object>) response
					.getResult().get("response")).get("result");
			log.info("Response of search request {}", mapper.writeValueAsString(response));
			if (result.stream().findFirst().isPresent()) {
				HashMap<String, Object> firstResult = result.stream().findFirst().get();
				if (((String) firstResult.get("mimeType")).equals("application/pdf"))
					contentIds.put((String) firstResult.get("identifier"), (String) firstResult.get("artifactUrl"));
				ArrayList<HashMap<String, Object>> children = (ArrayList<HashMap<String, Object>>) firstResult
						.get("children");
				children.forEach(map -> {
					if (((String) map.get("mimeType")).equals("application/pdf"))
						contentIds.put((String) map.get("identifier"), (String) map.get("artifactUrl"));
				});
			}
			log.info("ContentIds {}", contentIds);
		} catch (JsonProcessingException e) {
			log.error("Parsing error occured!");
		}
		return contentIds;
	}
}
