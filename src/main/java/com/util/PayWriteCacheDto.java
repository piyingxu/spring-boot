package com.util;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/9/26 10:21
 */
public class PayWriteCacheDto {

    @ApiModelProperty("可用积分")
    private Long availablePoint;

    @ApiModelProperty(value = "付款人手续费", required = false, example = "100", hidden = false)
    private Long fee;

    @ApiModelProperty(value = "付款人税费", required = false, example = "100", hidden = false)
    private Long vat;

    @ApiModelProperty(value = "付款人抵扣积分", required = false, example = "100", hidden = false)
    private Long deductionPoint;

    @ApiModelProperty(value = "付款人抵扣积分金额", required = false, example = "100", hidden = false)
    private Long deductionPointAmount;

    @ApiModelProperty(value = "付款人赠送积分", required = false, example = "100", hidden = false)
    private Long returnPoint;

    @ApiModelProperty(value = "付款人实际支付", required = false, example = "100", hidden = false)
    private Long payAmount;

    @ApiModelProperty(value = "收款人手续费", required = false, example = "100", hidden = false)
    private Long payeeFee;

    @ApiModelProperty(value = "收款人税费", required = false, example = "100", hidden = false)
    private Long payeeVat;

    @ApiModelProperty(value = "收款人收款金额", required = false, example = "100", hidden = false)
    private Long payeeAmount;

    @ApiModelProperty(value = "平台出手续费", required = false, example = "100", hidden = false)
    private Long platformFee;

    @ApiModelProperty(value = "平台出税费", required = false, example = "100", hidden = false)
    private Long platformVat;

    public Long getAvailablePoint() {
        return availablePoint;
    }

    public void setAvailablePoint(Long availablePoint) {
        this.availablePoint = availablePoint;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Long getVat() {
        return vat;
    }

    public void setVat(Long vat) {
        this.vat = vat;
    }

    public Long getDeductionPoint() {
        return deductionPoint;
    }

    public void setDeductionPoint(Long deductionPoint) {
        this.deductionPoint = deductionPoint;
    }

    public Long getDeductionPointAmount() {
        return deductionPointAmount;
    }

    public void setDeductionPointAmount(Long deductionPointAmount) {
        this.deductionPointAmount = deductionPointAmount;
    }

    public Long getReturnPoint() {
        return returnPoint;
    }

    public void setReturnPoint(Long returnPoint) {
        this.returnPoint = returnPoint;
    }

    public Long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

    public Long getPayeeFee() {
        return payeeFee;
    }

    public void setPayeeFee(Long payeeFee) {
        this.payeeFee = payeeFee;
    }

    public Long getPayeeVat() {
        return payeeVat;
    }

    public void setPayeeVat(Long payeeVat) {
        this.payeeVat = payeeVat;
    }

    public Long getPayeeAmount() {
        return payeeAmount;
    }

    public void setPayeeAmount(Long payeeAmount) {
        this.payeeAmount = payeeAmount;
    }

    public Long getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(Long platformFee) {
        this.platformFee = platformFee;
    }

    public Long getPlatformVat() {
        return platformVat;
    }

    public void setPlatformVat(Long platformVat) {
        this.platformVat = platformVat;
    }
}
