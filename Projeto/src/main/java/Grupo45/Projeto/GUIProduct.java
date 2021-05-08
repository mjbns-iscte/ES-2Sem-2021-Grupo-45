package Grupo45.Projeto;


import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class GUIProduct {
	private HashMap<String, Boolean> islong = new HashMap<>();
	private HashMap<String, Boolean> isgod = new HashMap<>();

	public HashMap<String, Boolean> getIslong() {
		return islong;
	}

	public HashMap<String, Boolean> getIsgod() {
		return isgod;
	}

	/**
	* Reads the Code Smells from the Excel file Code_Smells.xlsx into HashMaps
	* @throws IOException  when the Code_Smells.xlsx file is not found
	*/
	public void readCodeSmellsExcel() throws IOException {
		InputStream is = this.getClass().getResourceAsStream("/Excel/Code_Smells.xlsx");
		Workbook w = new XSSFWorkbook(is);
		org.apache.poi.ss.usermodel.Sheet sheet = w.getSheetAt(0);
		Iterator<Row> it = sheet.iterator();
		it.next();
		while (it.hasNext()) {
			Row row = it.next();
			Cell meth = row.getCell(3);
			Cell clas = row.getCell(2);
			Cell cell = row.getCell(7);
			Cell cell1 = row.getCell(10);
			if (cell.getCellType().equals(CellType.BOOLEAN) && cell1.getCellType().equals(CellType.BOOLEAN)) {
				islong.put(meth.getStringCellValue(), cell1.getBooleanCellValue());
				isgod.put(clas.getStringCellValue(), cell.getBooleanCellValue());
			}
		}
		w.close();
	}
}