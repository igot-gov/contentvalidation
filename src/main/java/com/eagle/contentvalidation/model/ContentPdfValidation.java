package com.eagle.contentvalidation.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentPdfValidation {

	private String pdfDownloadUrl;
	private String contentId;
	private String fileName;

	public String toString() {
		return "PdfDownloadURL: " + pdfDownloadUrl + ", ContentId: " + contentId + ", FileName: " + fileName;
	}
}
