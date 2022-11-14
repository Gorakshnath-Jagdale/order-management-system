package com.oms.pojo.requestPojo;

import lombok.Data;

import java.util.List;

@Data
public class GetExcelRequest {
    //If PO number is NOt null go with PO NUmber
    private String poNumber;
    //If PO is Empty check is SingleUser
    //If true check getActiveOrders  - return active PO's with all shipments
    //If getActiveOrders=false;getAllOrders=true;getOrdersWithEmptySDD=false - return all completed and active Orders
    //if getOrdersWithEmptySDD=true -return only those with empty supplier delivery date.
    private boolean isSingleCustomer;
    private boolean getOrdersWithEmptySDD;
private int orderStatusCode;
//          1 ACTIVE_PO
//          2 COMPLETED_PO
//          3 AMENDED_PO
//          4 CANCEL_PO
//          5 ACTIVE_PO AND COMPLETED_PO
//          6 ALL STATUSES
    private List<Long> customerList;// It will be used where isSingleCustomer is false
}

class test{

}