package org.sunbird.contentvalidation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class HierarchyResponse {

    private String artifactUrl;

    private String mimeType;

    private String name;

    private String status;

    private String identifier;

    private double duration;

    private String downloadUrl;

    private String description;

    private String resourceType;

    private String categoryType;

    private String category;

    private String courseType;


}
