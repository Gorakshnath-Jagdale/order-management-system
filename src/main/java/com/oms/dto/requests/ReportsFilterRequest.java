package com.oms.dto.requests;

import lombok.Data;

import java.util.Date;

@Data
public class ReportsFilterRequest {
    private Long customerId;
    private String manufacturer;
    private Long ProductId;
    private int status;
    private Date fromDate;
    private Date toDate;

}
