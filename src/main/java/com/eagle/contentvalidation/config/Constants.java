package com.eagle.contentvalidation.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {

    public static final String TEXT_FIELD_CONSTANT = "text";

    public static final List<String> MINIMUL_FIELDS = Collections.unmodifiableList(Arrays.asList("identifier", "duration", "downloadUrl", "description", "mimeType", "artifactUrl", "name", "status", "resourceType", "categoryType", "category"));

    public static final boolean FETCH_ON_LEVEL = false;

    public static final boolean FIELDS_PASSED = true;

    public static final boolean SKIP_ACCESS_CHECK = true;

    public static final String CONTENT_ID_REPLACER = "{contentId}";

    public static final String ROOT_ORG_CONSTANT = "rootOrg";

    public static final String ORG_CONSTANT = "org";

    public static final String USER_ID_CONSTANT = "userId";

    public static final String FIELD_PASSED_CONSTANT = "fieldsPassed";

    public static final String FETCH_ONE_LEVEL_CONSTANT = "fetchOneLevel";

    public static final String SKIP_ACCESS_CHECK_CONSTANT = "skipAccessCheck";

    public static final String FIELDS_CONSTANT = "fields";

    public static final String DOWNLOAD_URL_PREFIX = "/apis/proxies/v8/";

}
