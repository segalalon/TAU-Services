package com.gs.infra.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * a Response
 */
@ApiModel
public class IbanResponse implements ServiceResponse {

    @ApiModelProperty
    String iban;

    public IbanResponse(String iban) {
        this.iban = iban;
    }
}
