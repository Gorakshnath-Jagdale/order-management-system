package com.oms.dto.requests;

import lombok.Data;

import java.util.List;

@Data
public class FilteredReportRequest {
    private String manufacturer;
    private Long customer;
    private Long mfgItem;
    private List<String> status;
}
