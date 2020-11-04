package com.eagle.contentvalidation.service.impl;

import com.eagle.contentvalidation.config.Configuration;
import com.eagle.contentvalidation.config.Constants;
import com.eagle.contentvalidation.model.*;
import com.eagle.contentvalidation.service.ContentProviderService;
import com.eagle.contentvalidation.service.ContentValidationService;
import com.eagle.contentvalidation.util.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
@Log4j2
public class ContentValidationServiceImpl implements ContentValidationService {

	@Autowired
	private OutboundRequestHandlerServiceImpl outboundRequestHandlerService;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private Configuration configuration;

	@Autowired
	private ContentProviderService contentProviderService;

	@Autowired
	private CommonUtils commonUtils;

	/**
	 * Get the profanity check list for pdf
	 *
	 * @param fileInputStream
	 * @return
	 * @throws IOException
	 */
	private ProfanityResponseWrapper getTheProfanityCheckList(InputStream fileInputStream, String fileName)
			throws IOException {
		List<Profanity> profanityList = new ArrayList<>();
		ProfanityResponseWrapper profanityResponseWrapper = new ProfanityResponseWrapper();
		PDDocument doc = null;
		PDFTextStripper pdfStripper = null;
		String pageText = null;
		try {
			doc = PDDocument.load(fileInputStream);
			int pageCount = doc.getPages().getCount();
			for (int i = 1; i <= pageCount; i++) {
				pdfStripper = new PDFTextStripper();
				pdfStripper.setStartPage(i);
				pdfStripper.setEndPage(i);
				pageText = pdfStripper.getText(doc);
				extractImagesAndUpdateTheResponse(doc, i, profanityResponseWrapper);
				if (!StringUtils.isEmpty(pageText)) {
					Profanity profanityResponse = getProfanityCheckForText(pageText);
					profanityList.add(profanityResponse);
					updateProfanityWordOccurence(profanityResponse, i, profanityResponseWrapper);
				}
			}

			profanityResponseWrapper.setTotalPageUploaded(pageCount);
			profanityResponseWrapper.setProfanityList(profanityList);
			profanityResponseWrapper.setFileName(fileName);
		} catch (IOException e) {
			log.error("Exception occured while reading the pdf file");
			throw new IOException(e);
		}
		return profanityResponseWrapper;
	}

	/**
	 *
	 * @param rootOrg
	 * @param org
	 * @param contentId
	 * @param userId
	 * @return Get the profanity response
	 */
	@Override
	public ProfanityResponseWrapper validateContent(String rootOrg, String org, String contentId, String userId)
			throws IOException {
		HierarchyResponse hierarchyResponse = contentProviderService.getHeirarchyResponse(rootOrg, org, contentId,
				userId);
		InputStream inputStream = contentProviderService.getContentFile(hierarchyResponse.getDownloadUrl());
		return getTheProfanityCheckList(inputStream, commonUtils.getFileName(hierarchyResponse.getArtifactUrl()));
	}

	/**
	 * Query to the profanity service and get the result
	 *
	 * @param text
	 * @return return the Profanity result
	 */
	public Profanity getProfanityCheckForText(String text) {
		HashMap<String, Object> requestObject = new HashMap<>();
		requestObject.put(Constants.TEXT_FIELD_CONSTANT, text);
		StringBuilder url = new StringBuilder();
		url.append(configuration.getProfanityServiceHost()).append(configuration.getProfanityServicePath());
		Object response = outboundRequestHandlerService.fetchResultUsingPost(url.toString(), requestObject);
		return mapper.convertValue(response, Profanity.class);
	}

	/**
	 * Provides the Profanity analysis for PDF content
	 * 
	 * @param contentPdfValidation - Contains PDF File details
	 * 
	 * @return Returns the validation details of the given PDF file
	 */
	public ContentPdfValidationResponse validatePdfContent(ContentPdfValidation contentPdfValidation)
			throws IOException {
		StringBuilder logStr = null;
		if (log.isDebugEnabled()) {
			logStr = new StringBuilder();
			logStr.append("ValidatePDFContent request: ").append(mapper.writeValueAsString(contentPdfValidation));
		}
		long startTime = System.currentTimeMillis();
		InputStream inputStream = contentProviderService.getContentFile(contentPdfValidation.getPdfDownloadUrl());
		if (logStr != null) {
			logStr.append("Time taken to download PDF File: ").append(System.currentTimeMillis() - startTime)
					.append(" milliseconds");
		}
		String fileName = contentPdfValidation.getPdfDownloadUrl().split("artifacts%2F")[1];
		ContentPdfValidationResponse response = performProfanityAnalysis(inputStream, fileName);
		if (logStr != null) {
			logStr.append("Time take to validate PDF Content: ").append(System.currentTimeMillis() - startTime)
					.append(" milliseconds");
			log.debug(logStr.toString());
		}
		return response;
	}

	public ContentPdfValidationResponse validateLocalPdfContent(ContentPdfValidation contentPdfValidation)
			throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("validateLocalPdfContent request: {}", mapper.writeValueAsString(contentPdfValidation));
		}
		long startTime = System.currentTimeMillis();
		File pdfFile = new File(contentPdfValidation.getPdfDownloadUrl());
		ContentPdfValidationResponse response = new ContentPdfValidationResponse();
		if (pdfFile.exists()) {
			response = performProfanityAnalysis(new DataInputStream(new FileInputStream(pdfFile)), pdfFile.getName());
		} else {
			log.error("File doesn't exist at path: {}", contentPdfValidation.getPdfDownloadUrl());
		}
		if (log.isDebugEnabled()) {
			log.debug("Time take to validate PDF Content: {} milliseconds", System.currentTimeMillis() - startTime);
			log.debug("ContentPdfValidationResponse: {}", mapper.writeValueAsString(response));
		}
		return response;
	}

	/**
	 * Extract the image and update the count of responsewrapper
	 *
	 * @param doc
	 * @param index
	 * @param responseWrapper
	 * @throws IOException
	 */
	private void extractImagesAndUpdateTheResponse(PDDocument doc, int index, ProfanityResponseWrapper responseWrapper)
			throws IOException {
		PDPage page = doc.getPages().get(index - 1);
		int totalImageOnthePage = 0;
		PDResources resources = page.getResources();
		for (COSName name : resources.getXObjectNames()) {
			PDXObject o = resources.getXObject(name);
			if (o instanceof PDImageXObject) {
				totalImageOnthePage++;
			}
		}
		if (totalImageOnthePage > 0) {
			if (CollectionUtils.isEmpty(responseWrapper.getImagesOccurrenceOnPageNo())) {
				responseWrapper.setImagesOccurrenceOnPageNo(new HashSet<>());
			}
			responseWrapper.getImagesOccurrenceOnPageNo().add(index);
			responseWrapper.setPagesWithImages(responseWrapper.getPagesWithImages() + 1);
		}
	}

	/**
	 * Update the word count and occurence on each page
	 *
	 * @param profanity
	 * @param pageNo
	 * @param responseWrapper
	 */
	private void updateProfanityWordOccurence(Profanity profanity, int pageNo,
			ProfanityResponseWrapper responseWrapper) {
		responseWrapper.setOverAllOffensivescore(responseWrapper.getOverAllOffensivescore()
				+ profanity.getOverall_text_classification().getProbability());
		if (profanity.getPossible_profane_word_count() > 0) {
			HashMap<String, ProfanityWordCount> wordCountMap = responseWrapper.getProfanityClassifications();
			ProfanityWordCount wordCount = null;
			int size = profanity.getPossible_profanity_frequency().size();
			for (int i = 0; i < size; i++) {
				if (CollectionUtils.isEmpty(wordCountMap)) {
					wordCountMap = new HashMap<>();
				}
				String profaneWord = profanity.getPossible_profanity_frequency().get(i).getWord();
				Integer totalWordCount = (Integer) profanity.getPossible_profanity_frequency().get(i)
						.getNo_of_occurrence();
				if (ObjectUtils.isEmpty(wordCountMap.get(profaneWord))) {
					wordCount = new ProfanityWordCount();
					wordCount.setOffenceCategory(profanity.getOverall_text_classification().getClassification());
					wordCount.setOccurenceOnPage(new HashSet<>());
					wordCount.getOccurenceOnPage().add(pageNo);
					wordCount.setTotalWordCount(totalWordCount);
					wordCountMap.put(profaneWord, wordCount);
					responseWrapper.setProfanityWordCount(responseWrapper.getProfanityWordCount() + 1);
				} else {
					wordCountMap.get(profaneWord)
							.setOffenceCategory(profanity.getOverall_text_classification().getClassification());
					wordCountMap.get(profaneWord).getOccurenceOnPage().add(pageNo);
				}
			}
			responseWrapper.setProfanityClassifications(wordCountMap);
		}
	}

	private ContentPdfValidationResponse performProfanityAnalysis(InputStream inputStream, String fileName)
			throws IOException {
		ContentPdfValidationResponse response = new ContentPdfValidationResponse();
		PDDocument doc = PDDocument.load(inputStream);
		Splitter splitter = new Splitter();
		List<PDDocument> docPages = splitter.split(doc);
		PDFTextStripper pdfStripper = null;
		long totalTime = 0l;
		double overAllClassification = 0.0;
		for (int p = 0; p < docPages.size(); p++) {
			long startTime = System.currentTimeMillis();
			pdfStripper = new PDFTextStripper();
			pdfStripper.setAddMoreFormatting(false);
			pdfStripper.setLineSeparator(" ");
			String text = pdfStripper.getText(docPages.get(p));
			if (!StringUtils.isEmpty(text)) {
				Profanity profanityResponse = getProfanityCheckForText(text);
				if (log.isDebugEnabled()) {
					log.debug("Page wise analysis PageNo: {}, Analysis: {}", p,
							mapper.writeValueAsString(profanityResponse));
				}
				response.incrementTotalPages();
				for (ProfanityWordFrequency wordFrequency : profanityResponse.getPossible_profanity_frequency()) {
					wordFrequency.addPageOccurred(getPageNumberForIndex(p));
					response.addProfanityWordDetails(wordFrequency);
					response.incrementProfanityWordCount();
				}
				overAllClassification += profanityResponse.getOverall_text_classification().getProbability();
				if (StringUtils.isEmpty(response.getOverall_text_classification())) {
					response.setOverall_text_classification(
							profanityResponse.getOverall_text_classification().getClassification());
				} else {
					response.setOverall_text_classification(
							commonUtils.getProfanityClassification(response.getOverall_text_classification(),
									profanityResponse.getOverall_text_classification().getClassification()));
				}
			}
			long perPageTime = System.currentTimeMillis() - startTime;

			if (log.isDebugEnabled()) {
				log.debug("Time taken to perform Profanity Analysis for page {} is {} milliseconds",
						getPageNumberForIndex(p), perPageTime);
			}
			totalTime += perPageTime;
		}
		response.setScore(overAllClassification / (double) docPages.size());
		if (log.isDebugEnabled()) {
			log.debug("Time taken to perform Profanity Analysis for document {} is {} milliseconds", fileName,
					totalTime);
		}
		response.setPdfFileName(fileName);
		return response;
	}

	private int getPageNumberForIndex(int index) {
		return ++index;
	}
}
