package com.example.service_catalog_mobile.api;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ServiceCatalog {
    private String description;
    private String href;
    private String id;
    private Date lastUpdate;
    private String lifecycleStatus;
    private String name;
    private List<Date> validFor;
    private String version;
    private Map<String, String> relatedParty;
    private Map<String, String> category;



    public ServiceCatalog(String description, String href, String id, Date lastUpdate, String lifecycleStatus, String name, List<Date> validFor, String version, Map<String, String> relatedParty, Map<String, String> category) {
        this.description = description;
        this.href = href;
        this.id = id;
        this.lastUpdate = lastUpdate;
        this.lifecycleStatus = lifecycleStatus;
        this.name = name;
        this.validFor = validFor;
        this.version = version;
        this.relatedParty = relatedParty;
        this.category = category;
    }

    public ServiceCatalog(String description, String href, String id, String name, String version) {

        this.description = description;
        this.href = href;
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public ServiceCatalog() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


    public String getLifecycleStatus() {
        return lifecycleStatus;
    }

    public void setLifecycleStatus(String lifecycleStatus) {
        this.lifecycleStatus = lifecycleStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Date> getValidFor() {
        return validFor;
    }

    public void setValidFor(List<Date> validFor) {
        this.validFor = validFor;
    }

    public Map<String, String> getRelatedParty() {
        return relatedParty;
    }

    public void setRelatedParty(Map<String, String> relatedParty) {
        this.relatedParty = relatedParty;
    }

    public Map<String, String> getCategory() {
        return category;
    }

    public void setCategory(Map<String, String> category) {
        this.category = category;
    }
}
