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

public class Excel {

	private static String[] columns = { "MethodID", "package", "class", "method", "NOM_class", "LOC_class", "WMC_class",
			"LOC_method", "CYCLO_method" };
	private org.apache.poi.ss.usermodel.Sheet s;
	private String g_path;

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

	public String getG_path() {
		return g_path;
	}

	public org.apache.poi.ss.usermodel.Sheet getSheet() {
		return s;
	}

	public int getHeaderSize() {
		return columns.length;
	}

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
