package com.server.traffic.logic.dto.response;

public class MessageSuccess {
    private String codeSuccess;

    private String messageSuccess;
    private Long customerId;

    public String getCodeSuccess() {
        return codeSuccess;
    }

    public void setCodeSuccess(String codeSuccess) {
        this.codeSuccess = codeSuccess;
    }

    public String getMessageSuccess() {
        return messageSuccess;
    }

    public void setMessageSuccess(String messageSuccess) {
        this.messageSuccess = messageSuccess;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
