package com.oms.service.util;

import com.oms.models.OrderManagerEntity;
import com.oms.pojo.CustomerDetailsPojo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelGeneratorService {
    public ByteArrayInputStream getOrderDetailsExcel(List<OrderManagerEntity> orderManagerEntities,boolean isSingleCustomer) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("SHEET");


        int rowIdx=0;
        XSSFCellStyle style=workbook.createCellStyle();

        CellStyle style2=workbook.createCellStyle();
        CellStyle style3=workbook.createCellStyle();
        CellStyle style4=workbook.createCellStyle();
        CellStyle style5=workbook.createCellStyle();
        Row powerHeaderRowOne=sheet.createRow(rowIdx++);
        Row powerHeaderRowTwo=sheet.createRow(rowIdx++);

        if(isSingleCustomer)
        {
            sheet.addMergedRegion(new CellRangeAddress(0,1,0,12));
            sheet.addMergedRegion(new CellRangeAddress(0,1,13,19));
            powerHeaderRowOne.createCell(0).setCellValue(orderManagerEntities.get(0).getCustomerDetails().getCustomerName());
            powerHeaderRowOne.createCell(13).setCellValue("Electronika Feedback");
            powerHeaderRowOne.getCell(0).setCellStyle(getCellStyle(1,style));
            powerHeaderRowOne.getCell(13).setCellStyle(getCellStyle(2,style2));
        }else
        {

            sheet.addMergedRegion(new CellRangeAddress(0,1,0,19));
            powerHeaderRowOne.createCell(0).setCellValue("Electronika Feedback");
            powerHeaderRowOne.getCell(0).setCellStyle(getCellStyle(1,style));
        }
        //sheet.createRow(rowIdx++);


        var font=workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        style2.setFont(font);
        Row headerRow=sheet.createRow(rowIdx++);
        headerRow.createCell(0).setCellValue("Sr.");
        headerRow.createCell(1).setCellValue("PO Number");
        headerRow.createCell(2).setCellValue("Order Date");
        headerRow.createCell(3).setCellValue("Customer Name");
        headerRow.createCell(4).setCellValue("Customer Item No");
        headerRow.createCell(5).setCellValue("Manufacturer Item No");
        headerRow.createCell(6).setCellValue("Item Description");
        headerRow.createCell(7).setCellValue("MFG/Make");
        headerRow.createCell(8).setCellValue("INR Price/pcs");
        headerRow.createCell(9).setCellValue("PO Qty");
        headerRow.createCell(10).setCellValue("Customer Requested Date(CRD)");
        headerRow.createCell(11).setCellValue("Supplied Qty");
        headerRow.createCell(12).setCellValue("Pending Qty");
        headerRow.createCell(13).setCellValue("ESPL PO/EBIS No");
        headerRow.createCell(14).setCellValue("Supplier deliver Date");
        headerRow.createCell(15).setCellValue("Invoice No");
        headerRow.createCell(16).setCellValue("End Customer Bill Date");
        headerRow.createCell(17).setCellValue("POV");
        headerRow.createCell(18).setCellValue("Remarks");
        for (int i = 0; i < 19; i++) {
            if(i<13)
            {
                headerRow.getCell(i).setCellStyle(getCellStyle(3,style3));
            }else{
                headerRow.getCell(i).setCellStyle(getCellStyle(4,style4));
            }

        }
        for (OrderManagerEntity order : orderManagerEntities) {
            Row row = sheet.createRow(rowIdx++);
            int rowCounter = 1;
            row.createCell(0).setCellValue(++rowCounter);
            row.createCell(1).setCellValue(order.getPoNumber().toString());
            row.createCell(2).setCellValue(order.getPoDate().toString());
            row.createCell(3).setCellValue(order.getCustomerDetails().getCustomerName());
            row.createCell(4).setCellValue(order.getCustomerPartNo().toString());
            row.createCell(5).setCellValue(order.getMfgItemNo());
            row.createCell(6).setCellValue(order.getItemDetails());
            row.createCell(7).setCellValue(order.getMaker());
            row.createCell(8).setCellValue(order.getPrice());
            row.createCell(9).setCellValue(order.getPoQuantity());
            row.createCell(10).setCellValue(order.getCustomerRequestedDate().toString());
            row.createCell(11).setCellValue(order.getSuppliedQty());
            row.createCell(12).setCellValue(order.getPendingQty());
            row.createCell(13).setCellValue(order.getESPL_PO_OR_EBIS_NO());
            row.createCell(14).setCellValue(order.getSupplierDeliveryDate().toString());
            row.createCell(15).setCellValue(order.getInvoiceNo());
            row.createCell(16).setCellValue(order.getEndCustomerBillDate().toString());
            row.createCell(17).setCellValue(order.getPov());
            row.createCell(18).setCellValue(order.getRemarks());
            for (int i = 0; i < 19; i++) {
                row.getCell(i).setCellStyle(getCellStyle(0, style5));
            }
        }
        for (int i = 0; i < 20; i++) {
            sheet.autoSizeColumn(i);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    private CellStyle getCellStyle(int styleNo,CellStyle style) {
        if (styleNo == 1) {
            style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND );

        } else if (styleNo==2){
            style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND );
        }else if (styleNo==3)
        {
            style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND );
        }else if(styleNo==4)
        {
            style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND );
        }
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        return style;
    }
}
