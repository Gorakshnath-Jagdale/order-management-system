package com.oms.service.util;

import com.oms.dto.responses.ReportsFilterResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.oms.service.util.Constants.POStatus.ELEKTRONIKA_FEEDBACK;

@Component
public class ExcelGeneratorService {

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public ByteArrayResource getOrderDetailsExcel(List<ReportsFilterResponse> poScheduleList, boolean isSingleCustomer, boolean isConsolidatedReport) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("SHEET");


        int rowIdx = 0;
        XSSFCellStyle style = workbook.createCellStyle();

        CellStyle style2 = workbook.createCellStyle();
        CellStyle style3 = workbook.createCellStyle();
        CellStyle style4 = workbook.createCellStyle();
        CellStyle style5 = workbook.createCellStyle();
        Row powerHeaderRowOne = sheet.createRow(rowIdx++);
        Row powerHeaderRowTwo = sheet.createRow(rowIdx++);

        if (isSingleCustomer) {
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 10));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 11, isConsolidatedReport ? 12 : 18));
            powerHeaderRowOne.createCell(0).setCellValue(poScheduleList.get(0).getCustomerName());
            powerHeaderRowOne.createCell(11).setCellValue(ELEKTRONIKA_FEEDBACK);
            powerHeaderRowOne.getCell(0).setCellStyle(getCellStyle(1, style));
            powerHeaderRowOne.getCell(11).setCellStyle(getCellStyle(2, style2));
        } else {

            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, isConsolidatedReport ? 12 : 18));
            powerHeaderRowOne.createCell(0).setCellValue(ELEKTRONIKA_FEEDBACK);
            powerHeaderRowOne.getCell(0).setCellStyle(getCellStyle(1, style));
        }
        //sheet.createRow(rowIdx++);


        var font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        style2.setFont(font);
        Row headerRow = sheet.createRow(rowIdx++);
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

        if (isConsolidatedReport) {
            headerRow.createCell(10).setCellValue("Supplied Qty");
            headerRow.createCell(11).setCellValue("Pending Qty");
            headerRow.createCell(12).setCellValue("POV");
        } else {
            headerRow.createCell(10).setCellValue("Customer Requested Date(CRD)");
            headerRow.createCell(11).setCellValue("Supplied Qty");
            headerRow.createCell(12).setCellValue("Pending Qty");
            headerRow.createCell(13).setCellValue("ESPL PO");
            headerRow.createCell(14).setCellValue("Supplier deliver Date");
            headerRow.createCell(15).setCellValue("Invoice No");
            headerRow.createCell(16).setCellValue("End Customer Bill Date");
            headerRow.createCell(17).setCellValue("POV");
            headerRow.createCell(18).setCellValue("Remarks");


        }
        for (int i = 0; i < (isConsolidatedReport ? 13 : 19); i++) {
            if (i < 11) {
                headerRow.getCell(i).setCellStyle(getCellStyle(3, style3));
            } else {
                headerRow.getCell(i).setCellStyle(getCellStyle(4, style4));
            }

        }
        excelCreation(sheet, poScheduleList, style5, isConsolidatedReport, rowIdx);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return new ByteArrayResource(out.toByteArray());
    }

    private void excelCreation(Sheet sheet, List<ReportsFilterResponse> poScheduleList, CellStyle style5, boolean isConsolidatedReport, int rowIndex) {

        //AtomicInteger rowIdx = new AtomicInteger(finalRowIdx);
        AtomicInteger x = new AtomicInteger(rowIndex);
        //  AtomicReference<long> i= new AtomicReference<>(0L);
        poScheduleList.forEach(order ->
                {

                    Row row = sheet.createRow(x.get());
                    row.createCell(0).setCellValue(x.getAndIncrement() - 3);
                    row.createCell(1).setCellValue(order.getPoNumber());
                    row.createCell(2).setCellValue(format.format(order.getPoDate()));
                    row.createCell(3).setCellValue(order.getCustomerName());
                    row.createCell(4).setCellValue(order.getCustomerItemNo());
                    row.createCell(5).setCellValue(order.getMfgItemNumber());
                    row.createCell(6).setCellValue(order.getProductDetails());
                    row.createCell(7).setCellValue(order.getManufacturer());
                    row.createCell(8).setCellValue(String.valueOf(order.getPrice()));
                    row.createCell(9).setCellValue(order.getScheduleQty());

                    if (isConsolidatedReport) {
                        row.createCell(10).setCellValue(String.valueOf(order.getSuppliedQty()));
                        row.createCell(11).setCellValue(order.getPendingQty() == null ? 0 : order.getPendingQty());
                        row.createCell(12).setCellValue(String.valueOf(order.getPov()));
                    } else {
                        row.createCell(10).setCellValue(order.getCustomerRequestedDate() == null ? "" : format.format(order.getCustomerRequestedDate()));
                        row.createCell(11).setCellValue(String.valueOf(order.getSuppliedQty()));
                        row.createCell(12).setCellValue(order.getPendingQty() == null ? 0 : order.getPendingQty());
                        row.createCell(13).setCellValue(order.getEsplPO() == null ? "" : order.getEsplPO());
                        row.createCell(14).setCellValue(order.getSupplierDeliveryDate() == null ? "" : format.format(order.getSupplierDeliveryDate()));
                        row.createCell(15).setCellValue(order.getInvoiceNo());
                        row.createCell(16).setCellValue(order.getInvoiceDate() == null ? "" : format.format(order.getInvoiceDate()));
                        row.createCell(17).setCellValue(String.valueOf(order.getPov()));
                        row.createCell(18).setCellValue(order.getRemarks());
                    }

                    for (int i = 0; i < (isConsolidatedReport ? 13 : 19); i++) {
                        row.getCell(i).setCellStyle(getCellStyle(0, style5));
                    }
                }
//            if(finalRowIdx<rowIdx.get()-1) {
//                sheet.addMergedRegion(new CellRangeAddress(finalRowIdx, rowIdx.get() - 1, 17, 17));
//                sheet.addMergedRegion(new CellRangeAddress(finalRowIdx, rowIdx.get() - 1, 8, 8));
//                sheet.addMergedRegion(new CellRangeAddress(finalRowIdx, rowIdx.get() - 1, 9, 9));
//                sheet.addMergedRegion(new CellRangeAddress(finalRowIdx, rowIdx.get() - 1, 11, 11));
//                sheet.addMergedRegion(new CellRangeAddress(finalRowIdx, rowIdx.get() - 1, 12, 12));
//
//            }

        );

        for (int i = 0; i < 19; i++) {
            sheet.autoSizeColumn(i);
        }
    }


    private CellStyle getCellStyle(int styleNo, CellStyle style) {
        if (styleNo == 1) {
            style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        } else if (styleNo == 2) {
            style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        } else if (styleNo == 3) {
            style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        } else if (styleNo == 4) {
            style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
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
