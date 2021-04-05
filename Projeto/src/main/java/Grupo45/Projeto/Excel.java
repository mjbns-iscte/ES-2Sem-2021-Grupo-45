package Grupo45.Projeto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

//import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class Excel {

	private static String[] columns = {"MethodID","package","class","method","NOM_class","LOC_class","WMC_class","is_God_Class","LOC_method","CYCLO_method","is_Long_Method"};
	private org.apache.poi.ss.usermodel.Sheet s;
	
	public void setupExcel(String path) throws IOException {
		Workbook w = new XSSFWorkbook();
		String g_path = path + "_metricas.xlsx";
		
		s = w.createSheet("METRICAS");
		
		Font headerFont = w.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short)14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		
		CellStyle headerCellStyle = w.createCellStyle();
		headerCellStyle.setFont(headerFont);
		
		Row headerRow = s.createRow(0);
		
		for(int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}
		
		for(int i = 0; i < columns.length; i++) {
			s.autoSizeColumn(i);
		}
		
		FileOutputStream fileOut = new FileOutputStream(g_path);
		w.write(fileOut);
		fileOut.close();
		
	}
		
}
