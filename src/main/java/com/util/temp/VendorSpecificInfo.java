package com.util.temp;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/1/9 17:14
 */
public class VendorSpecificInfo {

    @ApiModelProperty(value = "客户ID", example = "GHS")
    private String ClientId;

    @ApiModelProperty(value = "属性ID", example = "")
    private String fieldId = "";

    @ApiModelProperty(value = "属性值", example = "")
    private String value  = "";

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
