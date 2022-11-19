package com.oms.dto.requests;

import lombok.Data;

@Data
public class ScheduleUpdateRequest {
    Long poId;
    Long productOrderId;
    public ProductShipmentManager productShipmentManager;
}
