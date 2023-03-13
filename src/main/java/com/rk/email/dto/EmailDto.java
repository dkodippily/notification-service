package com.rk.email.dto;

import java.util.List;

/**
 * @author Dayan Kodippily
 */


public class EmailDto {

    private String subject;
    private String content;
    private List<String> toAddresses;
    private List<String> ccAddresses;
    private List<String> bcAddresses;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getToAddresses() {
        return toAddresses;
    }

    public void setToAddresses(List<String> toAddresses) {
        this.toAddresses = toAddresses;
    }

    public List<String> getCcAddresses() {
        return ccAddresses;
    }

    public void setCcAddresses(List<String> ccAddresses) {
        this.ccAddresses = ccAddresses;
    }

    public List<String> getBcAddresses() {
        return bcAddresses;
    }

    public void setBcAddresses(List<String> bcAddresses) {
        this.bcAddresses = bcAddresses;
    }
}
