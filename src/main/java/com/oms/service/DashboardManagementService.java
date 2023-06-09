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
    private final UserManagementService userManagementService;
public DashboardResponse getDashboardStatus(Requester requester) throws Exception {
    if (!userManagementService.validateUser(requester.getUserId())) throw new Exception("Invalid user request!");
    var userAccessList = userManagementService.getTeamMemberList(requester.getUserId());
    DashboardResponse response=new DashboardResponse();
    response.setCompletedPurchaseOrder(poMasterRepository.countByCreatedByInAndOrderStatus(userAccessList,Constants.POStatus.COMPLETED_PO));
    response.setActivePurchaseOrders(poMasterRepository.countByCreatedByInAndOrderStatus(userAccessList,Constants.POStatus.ACTIVE_PO));
    response.setPendingSDDCount(poMasterRepository.countByOrderStatusAndCreatedByInAndProductOrderManagerEntity_ProductShipmentDetails_SupplierDeliveryDateNull(Constants.POStatus.ACTIVE_PO,userAccessList));
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.DAY_OF_MONTH,30);
    response.setPendingInvoiceFor30Day(poMasterRepository.countByCreatedByInAndOrderStatusAndProductOrderManagerEntity_ProductShipmentDetails_SupplierDeliveryDateLessThanEqualAndProductOrderManagerEntity_ProductShipmentDetails_InvoiceNoIsIgnoreCaseAndProductOrderManagerEntity_ProductShipmentDetails_InvoiceDateNull(userAccessList,Constants.POStatus.ACTIVE_PO,cal.getTime(),""));
return response;
}
}
