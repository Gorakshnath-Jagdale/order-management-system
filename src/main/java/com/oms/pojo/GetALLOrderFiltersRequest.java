package com.oms.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GetALLOrderFiltersRequest {
    public Long customerId;
    public String invoice;
    public String manufacturer;
    public String customerMFGItemNo;
    public Date fromPODate;
    public Date toPODate;
}
