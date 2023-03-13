package com.rk.email.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author Dayan Kodippily
 */

@Component
@ConfigurationProperties(prefix = "configurations")
@Profile(value = "dev")
public class AppConfiguration {
    private String service;
    private String apiKey;
    private String enrichApi;
    private String fromAddress;
    private String filterDomain;
    private boolean filterEmailsByDomain;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getEnrichApi() {
        return enrichApi;
    }

    public void setEnrichApi(String enrichApi) {
        this.enrichApi = enrichApi;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFilterDomain() {
        return filterDomain;
    }

    public void setFilterDomain(String filterDomain) {
        this.filterDomain = filterDomain;
    }

    public boolean isFilterEmailsByDomain() {
        return filterEmailsByDomain;
    }

    public void setFilterEmailsByDomain(boolean filterEmailsByDomain) {
        this.filterEmailsByDomain = filterEmailsByDomain;
    }
}
