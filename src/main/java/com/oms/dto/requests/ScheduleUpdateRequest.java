package com.oms.dto.requests;

import lombok.Data;

@Data
public class ScheduleUpdateRequest {
    public ProductShipmentManager productShipmentManager;
    Long poId;
    Long productOrderId;
}
