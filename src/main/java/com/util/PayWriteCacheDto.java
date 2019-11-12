package com.util;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/9/26 10:21
 */
public class PayWriteCacheDto {

    @ApiModelProperty("可用积分")
    private Long availablePoint;

    @ApiModelProperty("抵扣积分")
    private Long deductPoint;

    @ApiModelProperty("抵扣金额")
    private Long deductAmount;

    @ApiModelProperty("手续费")
    private Long payFee;

    @ApiModelProperty("返还积分")
    private Long redeemPoint;

    @ApiModelProperty("税费")
    private Long vat;

    @ApiModelProperty(value = "收款人手续费")
    private Long payeeFee;

    @ApiModelProperty(value = "收款人税费")
    private Long payeeVat;

    @ApiModelProperty(value = "收款人收款金额")
    private Long payeeAmount;

    @ApiModelProperty(value = "平台出手续费")
    private Long platformFee;

    @ApiModelProperty(value = "平台出税费")
    private Long platformVat;

    @ApiModelProperty(value = "收款人赠送积分")
    private Long payeeRedeemPoint;

}
