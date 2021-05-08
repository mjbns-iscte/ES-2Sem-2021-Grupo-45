package Grupo45.Projeto;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * * Date May 07-2021
 * This class was constructed to build an Excel file with a specific format  
 * @author G45
 * @author Andre Amado, Guilherme Henriques, João Guerra, Miguel Nunes, Francisco Mendes, Tiago Geraldo
 * @version 1.0 ~
 *
 */
public class Excel {

	/**
	 * This vector saves the name of the columns in the excel file
	 */
	private static String[] columns = { "MethodID", "package", "class", "method", "NOM_class", "LOC_class", "WMC_class",
			"LOC_method", "CYCLO_method" };
	/**
	 * This attribute represents the Excel Sheet of our Excel file 
	 */
	private org.apache.poi.ss.usermodel.Sheet s;
	/**
	 * This attribute saves the path where Excel will be saved
	 */
	private String g_path;

	/**
	 * @param path where Excel will be saved
	 * This method is responsible to build the Excel file and save him on the path
	 * @throws IOException when Excel is not found
	 */
	public void setupExcel(String path) throws IOException {
		Workbook w = new XSSFWorkbook();
		g_path = path;

		s = w.createSheet("METRICAS");

		Font headerFont = w.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headerCellStyle = w.createCellStyle();
		headerCellStyle.setFont(headerFont);

		Row headerRow = s.createRow(0);

		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		for (int i = 0; i < columns.length; i++) {
			s.autoSizeColumn(i);
		}

		FileOutputStream fileOut = new FileOutputStream(g_path);
		w.write(fileOut);
		fileOut.close();
		w.close();
	}

	/**
	 * @return the chosen path
	 */
	public String getG_path() {
		return g_path;
	}

	/**
	 * @return the Excel sheet of our file
	 */
	public org.apache.poi.ss.usermodel.Sheet getSheet() {
		return s;
	}

	/**
	 * @return the number of columns in the excel file
	 */
	public int getHeaderSize() {
		return columns.length;
	}

	/**
	 * @param s is the name of a specific metric
	 * @return the number of the column where that metric is
	 */
	public int getMetricColumn(String s) {
		int out = -1;
		for (int i = 0; i != columns.length; i++) {
			if (s.equals(columns[i]))
				out = i;
		}
		if (out == -1)
			throw new IllegalStateException("Input string not found in excel header");

		return out;
	}
}
