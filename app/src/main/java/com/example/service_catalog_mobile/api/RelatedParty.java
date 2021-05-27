package com.example.service_catalog_mobile.api;

public class RelatedParty {
    private String baseType;
    private String referredType;
    private String schemaLocation;
    private String type;
    private String href;
    private String id;
    private String name;
    private String role;

    public RelatedParty(String baseType, String referredType, String schemaLocation, String type, String href, String id, String name, String role) {
        this.baseType = baseType;
        this.referredType = referredType;
        this.schemaLocation = schemaLocation;
        this.type = type;
        this.href = href;
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public RelatedParty() {
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public String getReferredType() {
        return referredType;
    }

    public void setReferredType(String referredType) {
        this.referredType = referredType;
    }

    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
