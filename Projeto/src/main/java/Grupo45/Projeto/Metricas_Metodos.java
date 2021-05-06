package Grupo45.Projeto;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;




public class Metricas_Metodos {

	private ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
	private int nom_class=0;
	private HashMap<String, Integer> map= new HashMap<>();
	private int wmc;
	private int loc_class;
	private String pack;
	private ArrayList<Rule> rules = new ArrayList<>();
	private ArrayList<String> operators = new ArrayList<>();
	private ArrayList<File> ficheiros= new ArrayList<>();
	private Excel excel;

	public Metricas_Metodos() {
		super();
		for(int i=0;i!=2;i++)
			al.add(new ArrayList<String>());
	}

	public void analyze(File file) throws FileNotFoundException {
		nom_class=0;
		loc_class = 0;
		for(int i=0;i!=al.size();i++)
			al.get(i).clear();;

			InputStream is = new FileInputStream(file);
			CompilationUnit cu = StaticJavaParser.parse(is);
			new LOC_method().visit(cu, al);
			if(!al.isEmpty()) {
				nom_class= al.get(0).size(); 
			}
			new VoidVisitorAdapter<Object>() {
				public void visit (ClassOrInterfaceDeclaration n, Object t) {
					super.visit( n, loc_class);
					loc_class = (n.getEnd().get().line - n.getBegin().get().line + 1);
				}
			}.visit(cu, null);
			new VoidVisitorAdapter<Object>() {
				public void visit (PackageDeclaration n, Object t) {
					super.visit( n, pack);
					pack = n.getNameAsString();
				}
			}.visit(cu, null);
	}


	public void analyzeCyclometicComplexity (File file) throws FileNotFoundException {
		InputStream is = new FileInputStream(file);
		map.clear();
		map = new Cyclo_method().cyclo_method(is);
		wmc = 0;

		for(int i = 0; i < getMainArray().get(0).size();i++) {
			String nomes = getMainArray().get(0).get(i);	
			int aux =getMap().get(nomes);
			wmc = wmc + aux;

		}
	}

	
	public int getWmc() {
		return wmc;
	}


	public int getNom_class() {
		return nom_class;
	}


	public int getLoc_class() {
		return loc_class;
	}


	public HashMap<String, Integer> getMap() {
		return map;
	} 


	public ArrayList<ArrayList<String>> getMainArray() {
		return al;
	}

	public String getPackage() {
		return pack;
	}

	public ArrayList<File> search(File main){
		File[] files = main.listFiles();
		for (File file : files) {
			if(file.isDirectory() ) {
				this.search(file);
			}
			if(file.isFile()&&file.getAbsolutePath().endsWith(".java")) {
				ficheiros.add(file);
			}

		}
		return ficheiros;
	}

	


		

	public LinkedHashSet<String> applyRule(Rule rule, Excel e) throws IOException{

		//e.setupExcel(path);
		FileInputStream file = new FileInputStream(new File(e.getG_path()));		
		Workbook w = new XSSFWorkbook(file);
		System.out.println(w.getActiveSheetIndex());
		org.apache.poi.ss.usermodel.Sheet sheet = w.getSheet("METRICAS");
		ArrayList<Integer> values = new ArrayList<>();
		//		ArrayList<String> codeSmells = new ArrayList<>();
		LinkedHashSet<String> codeSmells = new LinkedHashSet<>(); 
		Iterator<Row> it = sheet.iterator();
		it.next();
		while(it.hasNext()) {
			values.clear();
			Row row = it.next();
			for(int j=0;j!=rule.getNumberOfConditions();j++) {
				values.add((int)Double.parseDouble(row.getCell(e.getMetricColumn(rule.getCondition(j).getMetric())).toString()));
			}
			if(rule.ruleEvaluate(values)) {
				if(rule.isClassRule()) {
					codeSmells.add(row.getCell(2).toString());
				}else	codeSmells.add(row.getCell(0).toString() + " " + row.getCell(3).toString());
			}
		}
		w.close();
		return codeSmells;

	}



	public Excel metricsToExcel(ArrayList<File> files, Excel e) throws IOException {
		String name = e.getG_path();
		//			org.apache.poi.ss.usermodel.Sheet sheet=e.getSheet();
		FileInputStream file = new FileInputStream(name);

		Workbook w = new XSSFWorkbook(file);
		org.apache.poi.ss.usermodel.Sheet sheet = w.getSheetAt(0);

		int x=1;
		for(File f: files) {
			analyze(f);

			analyzeCyclometicComplexity(f);
			for(int i=0;i!=getMainArray().get(0).size();i++) {
				Row row =sheet.createRow(x);
				String clas = f.getName();
				row.createCell(0).setCellValue(x);
				row.createCell(1).setCellValue(getPackage());
				row.createCell(2).setCellValue(clas.replaceAll(".java",""));
				row.createCell(3).setCellValue(getMainArray().get(0).get(i).replaceAll("\\s+",""));
				row.createCell(4).setCellValue(getNom_class());
				row.createCell(5).setCellValue(getLoc_class());
				row.createCell(6).setCellValue(getWmc());
				row.createCell(7).setCellValue(Integer.parseInt(getMainArray().get(1).get(i)));
				row.createCell(8).setCellValue(getMap().get(getMainArray().get(0).get(i)));

				x++;
			}

		}

		FileOutputStream fileOut = new FileOutputStream(new File(name));
		w.write(fileOut);
		fileOut.close();
		w.close();

		return e;
	}


	public void createTestRule() {
		Condition a = new Condition("LOC_method",">",10);
		Condition a1 = new Condition("CYCLO_method",">",5);
		//			Condition a2 = new Condition("NOM_Class",">",11);
		ArrayList<Condition> cs = new ArrayList<>();
		cs.add(a);
		cs.add(a1);
		//			cs.add(a2);
		ArrayList<String> ops = new ArrayList<>();
		ops.add("OR");
		ops.add("OR");
		Rule rule = new Rule("is_Long_Method",cs,ops);
		rules.add(rule);
	}
	
	
	public Rule getTestRule(int i) {			
		return rules.get(i);		
	}

	public Rule getRuleNamed(String s) {
		Rule out= null;
		for(int i=0;i!=rules.size();i++) {
			if(rules.get(i).getRuleName().equals(s))
				out=rules.get(i);
		}
		if(out.equals(null)) throw new IllegalStateException("There is no rule with given name");
		return out;
	}

	
	public void readTextFile() throws IOException {

		File f = new File("Rules.txt");
		if(f.exists()) { 


			BufferedReader br = new BufferedReader(new FileReader(f));      
			String st;
			while ((st = br.readLine()) != null)
				rules.add(new Rule(st));
			br.close();
		}
	}
	
	public ArrayList<Rule> getRuleArray(){
		return rules;
	}

	public void addRuleToArray(Rule r){
		rules.add(r);
	}
	public void removeRule(Rule r) {
		rules.remove(r);
	}

	public static void main(String[] args) throws FileNotFoundException, Exception {
	


		//
		//		erros a corrigir
		//		impedir que a condição tenha valores que não sejam int
		//		impedir que faça apply rule quando não há ficheiro excel
		//		adicionar mais sinais >= <= =?
		//		
		//		
	}






}


