package Grupo45.Projeto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

	/**
	 * Date May 07-2021
	 * This program is able to extract metrics from all .java files in a given source.
	 * It analyzes the code of java files for the metrics 'Lines of Code', 'Weighted Metric Count', 'Number of Class Methods' and 'Cyclomatic Complexity' creating a .xlsx file with the data.
	 * It has a Graphical User Interface that also shows the analyzed metrics and allows users to create rules for Code Smells evaluation.
	 * The GUI shows the detected code smells for the given rule, and has functionalities of rule manipulation.
	 * 
	 * This class has all the methods to manipulate metrics and Excel files
	 * @author G45
	 * @author André Amado, Guilherme Henriques, João Guerra, Miguel Nunes, Francisco Mendes, Tiago Geraldo
	 * @version 1.0
	 */
	public class Metrics {

		private Search search = new Search();
		/**
		 * ArrayList composed of two ArrayLists. One for 'lines of code of the method' values and the other for the method signature
		 */
		private ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
		/**
		 * int value for nom_class metric
		 */
		private int nom_class=0;
		/**
		 * HashMap containing the name of all methods as a key String and the cyclomatic complexity of each method as Integer
		 */
		private HashMap<String, Integer> map= new HashMap<>();
		/**
		 * int value for weighted method count 
		 */
		private int wmc;
		/**
		 * int value for 'lines of code of the class'
		 */
		private int loc_class;
		/**
		 * String with the package name
		 */
		private String pack;
		/**
		 * ArrayList for all the active Rules that can be used in the GUI
		 */
		private ArrayList<Rule> rules = new ArrayList<>();
		/**
		 * Constructor of the class that initiates the two arrays in the main array
		 */
		public Metrics() {
			super();
			for (int i = 0; i != 2; i++)
				al.add(new ArrayList<String>());
		}

		/**
		 * This method analyzes each given file extracting the metrics: 'lines of code of the method',
		 * 'lines of code of the class' and 'number of class methods'
		 * @param file to be analyzed
		 * @throws FileNotFoundException when the given file dosen't exist or can't be found
		 */
		public void analyze(File file) throws FileNotFoundException {
			nom_class = 0;
			loc_class = 0;
			for (int i = 0; i != al.size(); i++)
				al.get(i).clear();

			InputStream is = new FileInputStream(file);
			CompilationUnit cu = StaticJavaParser.parse(is);
			new LOC_method().visit(cu, al);
			if (!al.isEmpty()) {
				nom_class = al.get(0).size();
			}
			new VoidVisitorAdapter<Object>() {
				public void visit(ClassOrInterfaceDeclaration n, Object t) {
					super.visit(n, loc_class);
					loc_class = (n.getEnd().get().line - n.getBegin().get().line + 1);
				}
			}.visit(cu, null);
			new VoidVisitorAdapter<Object>() {
				public void visit(PackageDeclaration n, Object t) {
					super.visit(n, pack);
					pack = n.getNameAsString();
				}
			}.visit(cu, null);
		}

		/**
		 * This method analyzes each given file, extracting the metrics> 'cyclomatic complexity of methods' and 'weighted method count'
		 * @param file to be analyzed
		 * @throws FileNotFoundException when the given file dosen't exist or can't be found
		 */
		public void analyzeCyclometicComplexity(File file) throws FileNotFoundException {
			FileInputStream is = new FileInputStream(file);
			map.clear();
			map = new Cyclo_method().cyclo_method(is);
			wmc = 0;

			for (int i = 0; i < getMainArray().get(0).size(); i++) {
				String nomes = getMainArray().get(0).get(i);
				int aux = getMap().get(nomes);
				wmc = wmc + aux;
			}
		}

		/**
		 * @return returns weighted method count value
		 */
		public int getWmc() {
			return wmc;
		}

		/**
		 * @return returns number of class methods value
		 */
		public int getNom_class() {
			return nom_class;
		}

		/**
		 * @return returns lines of code of the class
		 */
		public int getLoc_class() {
			return loc_class;
		}

		/**
		 * @return returns HashMap with method names and the cyclomatic complexity of each one
		 */
		public HashMap<String, Integer> getMap() {
			return map;
		}

		/**
		 * @return returns the main array composed of two arrays, one with 'lines of code' metric and the other with method signatures
		 */
		public ArrayList<ArrayList<String>> getMainArray() {
			return al;
		}

		/**
		 * @return returns the package of the current file beeing used
		 */
		public String getPackage() {
			return pack;
		}

		/**
		 * This method searches for .java files in the given directory
		 * @param main is the file where the method will search for java files
		 * @return returns an array of all java files
		 */
		public ArrayList<File> search(File main) {
			return search.search(main);
		}

		/**
		 * This method applies a given Rule to an Excel file, comparing the conditions with the excel values to check if
		 * the Rule is true and if the method is a Code Smell
		 * @param rule is the Rule that will be applied to the excel
		 * @param e is the Excel where the rule will be applied
		 * @return returns a LinkedHashSet with information of all the code smells detected
		 * @throws IOException if it founds a problem getting the input file or the workbook
		 */
		public LinkedHashSet<String> applyRule(Rule rule, Excel e) throws IOException {

			return rule.applyRule(e);
		}

		/**
		 * Exports the metrics found in java files to an Excel file
		 * @param files is the array of all java files
		 * @param e is the Excel file to where the values will be exported
		 * @return returns the Excel file with all the metric values
		 * @throws IOException if it founds a problem getting the input file or the workbook
		 */
		public Excel metricsToExcel(ArrayList<File> files, Excel e) throws IOException {
			String name = e.getG_path();
			FileInputStream file = new FileInputStream(name);

			Workbook w = new XSSFWorkbook(file);
			org.apache.poi.ss.usermodel.Sheet sheet = w.getSheetAt(0);

			int x = 1;
			for (File f : files) {
				analyze(f);

				analyzeCyclometicComplexity(f);
				for (int i = 0; i != getMainArray().get(0).size(); i++) {
					Row row = sheet.createRow(x);
					String clas = f.getName();
					row.createCell(0).setCellValue(x);
					row.createCell(1).setCellValue(getPackage());
					row.createCell(2).setCellValue(clas.replaceAll(".java", ""));
					row.createCell(3).setCellValue(getMainArray().get(0).get(i).replaceAll("\\s+", ""));
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

		/**
		 * Used to test the creation of rules and for unity testing
		 * 
		 */
		public void createTestRule() {
			Rule r = new Rule("teste: :NOM_class:=:25: :AND: :NOM_class:=:10");
			rules.add(r);
		}


		/**
		 * Returns an active rule with given name
		 * @param s the name of the rule to be found
		 * @return the rule with the given name
		 */
		public Rule getRuleNamed(String s) {
			Rule out = null;
			for (int i = 0; i != rules.size(); i++) {
				if (rules.get(i).getRuleName().equals(s))
					out = rules.get(i);
			}
			if (out.equals(null))
				throw new IllegalStateException("There is no rule with given name");
			return out;
		}

		/**
		 * Reads a text file and creates Rules with the information found in the file
		 * @throws IOException when there is a problem with the BufferedReader
		 */
		public void readTextFile() throws IOException {
			File f = new File("Rules.txt");
			if(f.exists()) { 
				int i=0;
				BufferedReader br = new BufferedReader(new FileReader(f));      
				String st;
				while ((st = br.readLine()) != null) {
					i++;
					rules.add(new Rule(st));
				}
				if(i==0) {
					rules.add(new Rule("is_Long_Method: :LOC_method:>:40: :AND: :CYCLO_method:>:10"));
					rules.add(new Rule("is_God_Class: :LOC_class:>:500: :AND: :NOM_class:>:20: :AND: :WMC_class:>:50"));
				}
				br.close();
			} else { 
				rules.add(new Rule("is_Long_Method: :LOC_method:>:40: :AND: :CYCLO_method:>:10"));
				rules.add(new Rule("is_God_Class: :LOC_class:>:500: :AND: :NOM_class:>:20: :AND: :WMC_class:>:50"));
			}
		}

		/**
		 * 
		 * @return returns array of all active Rules
		 */
		public ArrayList<Rule> getRuleArray() {
			return rules;
		}

		/**
		 * Adds a given Rule to the active rules array
		 * @param r is the Rule to be added
		 */
		public void addRuleToArray(Rule r) {
			rules.add(r);
		}

		/**
		 * Removes a rule in the active Rules array
		 * @param r is the Rule to be removed
		 */
		public void removeRule(Rule r) {
			rules.remove(r);
		}
	}
