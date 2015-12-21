package com.allstate.trobo.controller;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.allstate.trobo.domain.TripRoute;
import com.allstate.trobo.domain.TripRouteEmployee;

public class ExcelTripSheetReportView extends AbstractExcelView {

	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<TripRoute> tripRoutes = (List<TripRoute>) model.get("tripRoutes");
		// create a wordsheets
		HSSFSheet sheet = workbook.createSheet("TripSheet");
		int rowNum = 0;
		
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setFontHeightInPoints((short)10);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		
		
		
		for(TripRoute tripRoute: tripRoutes) {
			HSSFRow vehicleNumber = sheet.createRow(rowNum++);
			vehicleNumber.createCell(0).setCellValue("Vehicle number: " + tripRoute.getVehicleNumber());
//			vehicleNumber.getCell(0).setCellStyle(style);
			
			sheet.addMergedRegion(new CellRangeAddress(
					rowNum-1, //first row (0-based)
					rowNum-1, //last row  (0-based)
			        0, //first column (0-based)
			        4  //last column  (0-based)
			));
			
			vehicleNumber.getCell(0).setCellStyle(style);
			
			HSSFRow header = sheet.createRow(rowNum++);
			header.createCell(0).setCellValue("Emp Id");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Gender");
			header.createCell(3).setCellValue("Address");
			header.createCell(4).setCellValue("Time");
			
			for(int j = 0; j<=4; j++)
				header.getCell(j).setCellStyle(style);
			
			for(TripRouteEmployee tripRouteEmp: tripRoute.getEmployees()) {
				// create the row data
				HSSFRow row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(tripRouteEmp.getEmpId());
				row.createCell(1).setCellValue(tripRouteEmp.getEmpName());				
				row.createCell(2).setCellValue(tripRouteEmp.getEmpSex());	
				row.createCell(3).setCellValue(tripRouteEmp.getAddressLine());
				row.createCell(4).setCellValue(tripRouteEmp.getTripTime());				
			}
			sheet.createRow(rowNum++);
		}
		
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(3);
		
		 response.setContentType("application/vnd.ms-excel");
		 response.setHeader("Content-Disposition", "attachment; filename=\"" + "TripSheet.xls" + "\"");
		
		OutputStream outStream = null;

        try {
            outStream = response.getOutputStream();
            workbook.write(outStream);
            outStream.flush();
        } finally {
            outStream.close();
        }    
	}
}
