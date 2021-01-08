package org.sunbird.contentvalidation.model.contentsearch.model;

import org.springframework.util.StringUtils;
import org.sunbird.contentvalidation.model.contentsearch.groups.ValidationGroupAuthoringToolSearch;
import org.sunbird.contentvalidation.model.contentsearch.groups.ValidationGroupGeneralSearch;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class ValidatedSearchData implements Serializable {

	private static final long serialVersionUID = 1L;
	public static List<String> supportedLocales = Arrays.asList("ar","br","cz","da","de","el","en","es","fr","fr_ca","hr","hu","id","it","ja","ko","nl","no","pl","pt","ru","sl","sv","th","tr","zh");

	@PositiveOrZero
    private int pageNo = 0;

    @PositiveOrZero
    private int pageSize = 10;

    @NotBlank
    private String rootOrg;

    @Size(min = 1, groups = {ValidationGroupGeneralSearch.class, ValidationGroupAuthoringToolSearch.class})
    private List<String> orgs = new ArrayList<>();

    @Valid
    private Filters filters = new Filters();

    private String query;
    private List<String> locale = new ArrayList<>();
    private UUID uuid;
    private Boolean isCatalog = false;
    private Boolean isStandAlone;
    private Boolean isUserRecordEnabled;
    private List<String> accessPaths = new ArrayList<>();
    private List<Map<SortableFields, SortOrders>> sort = new ArrayList<>();
    private Map<SortableAggregations, List<Map<AggregationSortType, SortOrders>>> aggregationsSorting = new HashMap<>();

    @AssertTrue(message = "uuid can not be empty")
    private boolean isUuid() {
        if (true) {
            return Objects.nonNull(uuid);
        } else return true;
    }

    @AssertTrue(groups = {ValidationGroupGeneralSearch.class, ValidationGroupAuthoringToolSearch.class}, message = "No accessPaths found, User Unauthorized")
    private boolean isAccessPaths() {
        if (true) {
            return accessPaths.size() > 0;
        } else return true;
    }

    @AssertTrue(message = "sort only applicable if query is empty or if it's an all search")
    private boolean isSort() {
        if (sort.size() > 0) {
            if (sort.stream().filter(item -> item.size() > 0).collect(Collectors.toList()).size() > 0)
                return StringUtils.isEmpty(query) || query.equals("*") || query.equalsIgnoreCase("all");
            else
                return true;
        } else return true;
    }

    @AssertTrue(message = "Locale(s) not supported")
    private boolean isLocale() {
        List<String> x = locale.stream().map(String::toLowerCase).map(i -> i.replace("-", "_")).collect(Collectors.toList());
        x.retainAll(supportedLocales);
        locale = x;
        return true;
    }

    public @PositiveOrZero int getPageNo() {
        return this.pageNo;
    }

    public @PositiveOrZero int getPageSize() {
        return this.pageSize;
    }

    public @NotBlank String getRootOrg() {
        return this.rootOrg;
    }

    public @Size(min = 1, groups = {ValidationGroupGeneralSearch.class, ValidationGroupAuthoringToolSearch.class}) List<String> getOrgs() {
        return this.orgs;
    }

    public @Valid Filters getFilters() {
        return this.filters;
    }

    public String getQuery() {
        return this.query;
    }

    public List<String> getLocale() {
        return this.locale;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Boolean getIsCatalog() {
        return this.isCatalog;
    }

    public Boolean getIsStandAlone() {
        return this.isStandAlone;
    }

    public List<String> getAccessPaths() {
        return this.accessPaths;
    }

    public List<Map<SortableFields, SortOrders>> getSort() {
        return this.sort;
    }

    public void setPageNo(@PositiveOrZero int pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(@PositiveOrZero int pageSize) {
        this.pageSize = pageSize;
    }

    public void setRootOrg(@NotBlank String rootOrg) {
        this.rootOrg = rootOrg;
    }

    public void setOrgs(@Size(min = 1, groups = {ValidationGroupGeneralSearch.class, ValidationGroupAuthoringToolSearch.class}) List<String> orgs) {
        this.orgs = orgs;
    }

    public void setFilters(@Valid Filters filters) {
        this.filters = filters;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setLocale(List<String> locale) {
        this.locale = locale;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setIsCatalog(Boolean isCatalog) {
        this.isCatalog = isCatalog;
    }

    public void setIsStandAlone(Boolean isStandAlone) {
        this.isStandAlone = isStandAlone;
    }

    public void setAccessPaths(List<String> accessPaths) {
        this.accessPaths = accessPaths;
    }

    public void setSort(List<Map<SortableFields, SortOrders>> sort) {
        this.sort = sort;
    }

    public Map<SortableAggregations, List<Map<AggregationSortType, SortOrders>>> getAggregationsSorting() {
        return aggregationsSorting;
    }

    public void setAggregationsSorting(Map<SortableAggregations, List<Map<AggregationSortType, SortOrders>>> aggregationsSorting) {
        this.aggregationsSorting = aggregationsSorting;
    }

    public Boolean getIsUserRecordEnabled() {
        return this.isUserRecordEnabled;
    }

    public void setIsUserRecordEnabled(Boolean isUserRecordEnabled) {
        this.isUserRecordEnabled = isUserRecordEnabled;
    }

}
