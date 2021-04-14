package Grupo45.Projeto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.traversal.NodeIterator;

import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;





public class Metricas_Metodos {
	private ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
	private ArrayList<String> a = new ArrayList<String>();
	private int nom_class=0;
	private HashMap<String, Integer> map= new HashMap<>();
	private int wmc;
	private int loc_class;
	private String pack;
	
	public Metricas_Metodos(int j) {
		super();
		for(int i=0;i!=j;i++)
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
		
		for(int i = 0; i < getAl().get(0).size();i++) {
			String nomes = getAl().get(0).get(i);	
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


	public ArrayList<ArrayList<String>> getAl() {
		return al;
	}
  
	
	public String getPackage() {
		return pack;
	}


	public Excel metricsToExcel(ArrayList<File> files, Excel e) throws IOException {
		String name = e.getG_path();
//		org.apache.poi.ss.usermodel.Sheet sheet=e.getSheet();
		FileInputStream file = new FileInputStream(name);
		
		Workbook w = new XSSFWorkbook(file);
		org.apache.poi.ss.usermodel.Sheet sheet = w.getSheetAt(0);
		
		int x=1;
		for(File f: files) {
			analyze(f);
			
			analyzeCyclometicComplexity(f);
			for(int i=0;i!=getAl().get(0).size();i++) {
				Row row =sheet.createRow(x);
				
				row.createCell(0).setCellValue(x);
				row.createCell(1).setCellValue(getPackage());
				row.createCell(2).setCellValue(f.getName());
				row.createCell(3).setCellValue(getAl().get(0).get(i));
				row.createCell(4).setCellValue(getNom_class());
				row.createCell(5).setCellValue(getLoc_class());
				row.createCell(6).setCellValue(getWmc());
				row.createCell(8).setCellValue(Integer.parseInt(getAl().get(1).get(i)));
				row.createCell(9).setCellValue(getMap().get(getAl().get(0).get(i)));

				
				
				x++;
				
			}
			
		}
		System.out.println(getAl().get(1).size());

		FileOutputStream fileOut = new FileOutputStream(new File(name));
		w.write(fileOut);
		fileOut.close();
		
		return e;
	}
	
	private ArrayList<File> ficheiros = new ArrayList<File>();
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
	
    public static void main(String[] args) throws FileNotFoundException, Exception {
     //   File file = new File("C:\\jasml\\src");
   // 	  File file = new File("C:\\Users\\jtfgb\\OneDrive - ISCTE-IUL\\Documentos\\ES_Projeto Teste\\src\\com\\jasml\\compiler\\SourceCodeParser.java"); 
    	File file = new File("C:\\Users\\Amado\\Desktop\\Gosto muito de programar\\src");
//    	File file = new File("C:\\Users\\migue\\Documents\\Projeto\\src\\com\\jasml\\compiler\\ParsingException.java");
    	Excel e = new Excel();
    	e.setupExcel("C:\\Users\\Amado\\Desktop\\Gosto muito de programar\\ola");
    	Metricas_Metodos mm = new Metricas_Metodos(2);
    	
    	mm.metricsToExcel(mm.search(file), e);

    }


	
	

}
 

