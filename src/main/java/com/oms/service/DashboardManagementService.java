package com.oms.service;

import com.oms.dto.Requester;
import com.oms.dto.responses.DashboardResponse;
import com.oms.models.repository.POMasterRepository;
import com.oms.service.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class DashboardManagementService {
private final POMasterRepository poMasterRepository;
public DashboardResponse getDashboardStatus(Requester requester)
{
    DashboardResponse response=new DashboardResponse();
    response.setCompletedPurchaseOrder(poMasterRepository.countByOrderStatusIgnoreCase(Constants.POStatus.COMPLETED_PO));
    response.setActivePurchaseOrders(poMasterRepository.countByOrderStatusIgnoreCase(Constants.POStatus.ACTIVE_PO));
    response.setPendingSDDCount(poMasterRepository.countByOrderStatusAndProductOrderManagerEntity_ProductShipmentDetails_SupplierDeliveryDateNull(Constants.POStatus.ACTIVE_PO));
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.DAY_OF_MONTH,30);
    response.setPendingInvoiceFor30Day(poMasterRepository.countByOrderStatusAndProductOrderManagerEntity_ProductShipmentDetails_SupplierDeliveryDateLessThanEqualAndProductOrderManagerEntity_ProductShipmentDetails_InvoiceNoIsIgnoreCaseAndProductOrderManagerEntity_ProductShipmentDetails_InvoiceDateNull(Constants.POStatus.ACTIVE_PO,cal.getTime(),""));
return response;
}
}
