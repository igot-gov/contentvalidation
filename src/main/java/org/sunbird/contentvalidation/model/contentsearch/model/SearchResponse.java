package org.sunbird.contentvalidation.model.contentsearch.model;

import java.util.HashMap;
import java.util.Map;

public class SearchResponse {
    private static final long serialVersionUID = -3773253896160786443L;
    private String id;
    private String ver;
    private String ts;
    private Object params;
    private String responseCode;
    private Map<String, Object> result;

    public SearchResponse() {
        this.responseCode = "OK";
        this.result = new HashMap();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVer() {
        return this.ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getTs() {
        return this.ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public Map<String, Object> getResult() {
        return this.result;
    }

    public Object get(String key) {
        return this.result.get(key);
    }

    public void put(String key, Object vo) {
        this.result.put(key, vo);
    }

    public void putAll(Map<String, Object> map) {
        this.result.putAll(map);
    }

    public boolean containsKey(String key) {
        return this.result.containsKey(key);
    }

    public Object getParams() {
        return this.params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public void setResponseCode(String code) {
        this.responseCode = code;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

}
