package org.sunbird.contentvalidation.model.contentsearch.model;


import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

import org.sunbird.contentvalidation.model.contentsearch.groups.ValidationGroupGeneralSearch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Filters implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 0, groups = ValidationGroupGeneralSearch.class)
    private List<String> publisherDetails = Collections.emptyList();

    @Size(max = 0, groups = ValidationGroupGeneralSearch.class)
    private List<String> trackContacts = Collections.emptyList();

//    @Size(max = 0, groups = ValidationGroupGeneralSearch.class)
    private List<String> creatorContacts = Collections.emptyList();

    private List<SearchStatuses> status = new ArrayList<>(Arrays.asList(SearchStatuses.Live, SearchStatuses.MarkedForDeletion));
    private List<String> contentType = Collections.emptyList();
    private List<String> unit = Collections.emptyList();
    private List<String> learningMode = Collections.emptyList();
    private List<String> isExternal = Collections.emptyList();
    private List<String> resourceType = Collections.emptyList();
    private List<String> resourceCategory = Collections.emptyList();
    private List<String> sourceShortName = Collections.emptyList();
    private List<String> fileType = Collections.emptyList();
    private List<String> duration = Collections.emptyList();
    private List<String> complexityLevel = Collections.emptyList();
    private List<String> catalogPaths = Collections.emptyList();
    private List<String> lastUpdatedOn = Collections.emptyList();
    private List<String> keywords = Collections.emptyList();
    private List<String> isRejected = Collections.emptyList();
    private List<String> catalogPathsIds = Collections.emptyList();
    private List<String> skills = Collections.emptyList();
    private List<String> region = Collections.emptyList();
    private List<String> jobProfile = Collections.emptyList();
    private List<String> exclusiveContent = Collections.emptyList();
    private List<String> instanceCatalog = Collections.emptyList();
    private List<String> labels = Collections.emptyList();
    private List<String> curatedTags = Collections.emptyList();
    private List<String> collectionsId = Collections.emptyList();
    private List<String> collectionsName = Collections.emptyList();
    private List<String> childrenId = Collections.emptyList();
    private List<String> childrenName = Collections.emptyList();
    private List<String> isInIntranet = Collections.emptyList();
    private List<String> mimeType = Collections.emptyList();

//    @AssertTrue(message = "Can be applied only if " + SearchConstants.FILTER_CONTENT_TYPE_FIELD_KEY + " has " + SearchConstants.RESOURCE)
//    private boolean isResourceType() {
//        if (resourceType.size() > 0) {
//            return contentType.contains(SearchConstants.RESOURCE);
//        } else return true;
//    }

//    @AssertTrue(message = "Can be applied only if " + SearchConstants.FILTER_CONTENT_TYPE_FIELD_KEY + " has " + SearchConstants.RESOURCE)
//    private boolean isResourceCategory() {
//        if (resourceCategory.size() > 0) {
//            return contentType.contains(SearchConstants.RESOURCE);
//        } else return true;
//    }

//    @AssertTrue(message = "Can be applied only if " + SearchConstants.FILTER_CONTENT_TYPE_FIELD_KEY + " has " + SearchConstants.COURSE)
//    private boolean getIsExternal() {
//        if (isExternal.size() > 0) {
//            return contentType.contains(SearchConstants.COURSE);
//        } else return true;
//    }

//    @AssertTrue(message = "Can be applied only if " + SearchConstants.FILTER_CONTENT_TYPE_FIELD_KEY + " has " + SearchConstants.COURSE)
//    private boolean isLearningMode() {
//        if (learningMode.size() > 0) {
//            return contentType.contains(SearchConstants.COURSE);
//        } else return true;
//    }

    @AssertTrue(message = "Filter not allowed", groups = ValidationGroupGeneralSearch.class)
    private boolean isStatus() {
        return status.size() == 2 && status.contains(SearchStatuses.Live) && status.contains(SearchStatuses.MarkedForDeletion);
    }

    @AssertFalse(message = "Filter not allowed", groups = ValidationGroupGeneralSearch.class)
    private boolean isIsRejected() {
        return isRejected.size() > 0;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getRegion() {
        return region;
    }

    public void setRegion(List<String> region) {
        this.region = region;
    }

    public List<String> getJobProfile() {
        return jobProfile;
    }

    public void setJobProfile(List<String> jobProfile) {
        this.jobProfile = jobProfile;
    }

    public List<String> getExclusiveContent() {
        return exclusiveContent;
    }

    public void setExclusiveContent(List<String> exclusiveContent) {
        this.exclusiveContent = exclusiveContent;
    }

    public List<String> getInstanceCatalog() {
        return instanceCatalog;
    }

    public void setInstanceCatalog(List<String> instanceCatalog) {
        this.instanceCatalog = instanceCatalog;
    }

    public List<String> getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(List<String> isRejected) {
        this.isRejected = isRejected;
    }

    public List<String> getCatalogPathsIds() {
        return catalogPathsIds;
    }

    public void setCatalogPathsIds(List<String> catalogPathsIds) {
        this.catalogPathsIds = catalogPathsIds;
    }

    public List<String> getPublisherDetails() {
        return this.publisherDetails;
    }

    public void setPublisherDetails(List<String> publisherDetails) {
        this.publisherDetails = publisherDetails;
    }

    public List<String> getTrackContacts() {
        return this.trackContacts;
    }

    public void setTrackContacts(List<String> trackContacts) {
        this.trackContacts = trackContacts;
    }

    public List<String> getCreatorContacts() {
        return this.creatorContacts;
    }

    public void setCreatorContacts(List<String> creatorContacts) {
        this.creatorContacts = creatorContacts;
    }

    public List<SearchStatuses> getStatus() {
        return this.status;
    }

    public void setStatus(List<SearchStatuses> status) {
        this.status = status;
    }

    public List<String> getContentType() {
        return this.contentType;
    }

    public void setContentType(List<String> contentType) {
        this.contentType = contentType;
    }

    public List<String> getUnit() {
        return this.unit;
    }

    public void setUnit(List<String> unit) {
        this.unit = unit;
    }

    public List<String> getLearningMode() {
        return this.learningMode;
    }

    public void setLearningMode(List<String> learningMode) {
        this.learningMode = learningMode;
    }

    public List<String> getIsExternal() {
        return this.isExternal;
    }

    public void setIsExternal(List<String> isExternal) {
        this.isExternal = isExternal;
    }

    public List<String> getResourceType() {
        return this.resourceType;
    }

    public void setResourceType(List<String> resourceType) {
        this.resourceType = resourceType;
    }

    public List<String> getResourceCategory() {
        return this.resourceCategory;
    }

    public void setResourceCategory(List<String> resourceCategory) {
        this.resourceCategory = resourceCategory;
    }

    public List<String> getSourceShortName() {
        return this.sourceShortName;
    }

    public void setSourceShortName(List<String> sourceShortName) {
        this.sourceShortName = sourceShortName;
    }

    public List<String> getFileType() {
        return this.fileType;
    }

    public void setFileType(List<String> fileType) {
        this.fileType = fileType;
    }

    public List<String> getDuration() {
        return this.duration;
    }

    public void setDuration(List<String> duration) {
        this.duration = duration;
    }

    public List<String> getComplexityLevel() {
        return this.complexityLevel;
    }

    public void setComplexityLevel(List<String> complexityLevel) {
        this.complexityLevel = complexityLevel;
    }

    public List<String> getCatalogPaths() {
        return this.catalogPaths;
    }

    public void setCatalogPaths(List<String> catalogPaths) {
        this.catalogPaths = catalogPaths;
    }

    public List<String> getLastUpdatedOn() {
        return this.lastUpdatedOn;
    }

    public void setLastUpdatedOn(List<String> lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public List<String> getKeywords() {
        return this.keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<String> getCuratedTags() {
        return curatedTags;
    }

    public void setCuratedTags(List<String> curatedTags) {
        this.curatedTags = curatedTags;
    }

    public List<String> getCollectionsId() {
        return collectionsId;
    }

    public void setCollectionsId(List<String> collectionsId) {
        this.collectionsId = collectionsId;
    }

    public List<String> getCollectionsName() {
        return collectionsName;
    }

    public void setCollectionsName(List<String> collectionsName) {
        this.collectionsName = collectionsName;
    }

    public List<String> getChildrenId() {
        return childrenId;
    }

    public void setChildrenId(List<String> childrenId) {
        this.childrenId = childrenId;
    }

    public List<String> getChildrenName() {
        return childrenName;
    }

    public void setChildrenName(List<String> childrenName) {
        this.childrenName = childrenName;
    }

    public List<String> getIsInIntranet() {
        return isInIntranet;
    }

    public void setIsInIntranet(List<String> isInIntranet) {
        this.isInIntranet = isInIntranet;
    }

    public List<String> getMimeType() {
        return mimeType;
    }

    public void setMimeType(List<String> mimeType) {
        this.mimeType = mimeType;
    }
}
