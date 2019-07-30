package com.util.temp;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/1/10 11:17
 */
public class TransactionStatus {

    @ApiModelProperty(value = "登录权限", example = "")
    private Security Security;

    @ApiModelProperty(value = "UBA交易ID(Accountinformation返回的UBATransactionId)", example = "")
    private String transactionId;

    @ApiModelProperty(value = "transsnet交易ID(备选)", example = "")
    private String hstransactionId;

    @ApiModelProperty(value = "路由标记", example = "UBAGRP-GH-BANK")
    private String routingTag;

    @ApiModelProperty(value = "供应商特定信息", example = "")
    private VendorSpecificInfo vendorSpecificFields;

    public com.util.temp.Security getSecurity() {
        return Security;
    }

    public void setSecurity(com.util.temp.Security security) {
        Security = security;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getHstransactionId() {
        return hstransactionId;
    }

    public void setHstransactionId(String hstransactionId) {
        this.hstransactionId = hstransactionId;
    }

    public String getRoutingTag() {
        return routingTag;
    }

    public void setRoutingTag(String routingTag) {
        this.routingTag = routingTag;
    }

    public VendorSpecificInfo getVendorSpecificFields() {
        return vendorSpecificFields;
    }

    public void setVendorSpecificFields(VendorSpecificInfo vendorSpecificFields) {
        this.vendorSpecificFields = vendorSpecificFields;
    }
}
