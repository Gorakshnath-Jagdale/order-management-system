package com.oms.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardResponse {
    private long pendingSDDCount;
    private long pendingInvoiceFor30Day;
    private long activePurchaseOrders;
    private long completedPurchaseOrder;
}
