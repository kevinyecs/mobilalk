package com.example.service_catalog_mobile.api;

public class ServiceCategoryRef {
    private String referredType;
    private String href;
    private String id;
    private String name;

    public ServiceCategoryRef(String referredType, String href, String id, String name) {
        this.referredType = referredType;
        this.href = href;
        this.id = id;
        this.name = name;
    }

    public ServiceCategoryRef() {
    }

    public String getReferredType() {
        return referredType;
    }

    public void setReferredType(String referredType) {
        this.referredType = referredType;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
