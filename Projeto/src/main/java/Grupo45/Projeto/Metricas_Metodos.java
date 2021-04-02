package Grupo45.Projeto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.w3c.dom.traversal.NodeIterator;

import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
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
import java.util.Map;





public class Metricas_Metodos {
	private ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
	private ArrayList<String> a = new ArrayList<String>();
	private int nom_class=0;
	private HashMap<String, Integer> map= new HashMap<>();
	private int loc_class;

	public Metricas_Metodos(int j) {
		super();
		for(int i=0;i!=j;i++)
			al.add(new ArrayList<String>());


	}


	public void analyze(File file) throws FileNotFoundException {
		nom_class=0;
		loc_class = 0;
		InputStream is = new FileInputStream(file);
		CompilationUnit cu = StaticJavaParser.parse(is);
		new LOC_method().visit(cu, al);
		//	 map = new Cyclo_method().cyclo_method(is);
		if(!al.isEmpty()) {
			nom_class= al.get(0).size(); 
		}

		new VoidVisitorAdapter<Object>() {
			public void visit (ClassOrInterfaceDeclaration n, Object t) {
				super.visit( n, loc_class);
				loc_class = (n.getEnd().get().line - n.getBegin().get().line + 1);
			}
		}.visit(cu, null);
		

		/*for(int i=0; i != al.get(1).size(); i++) {
			loc_class = loc_class + Integer.parseInt(al.get(1).get(i));

		}*/
	}

	public int getNom_class() {
		return nom_class;
	}



	//	public HashMap<String, Integer> getMap() {
	//		return map;
	//	} 


	public int getLoc_class() {
		return loc_class;
	}


	public ArrayList<ArrayList<String>> getAl() {
		return al;
	}


	public static void main(String[] args) throws FileNotFoundException, Exception {
		//     File file = new File("C://jasml//src//com//jasml//compiler//SourceCodeParser.java");
		//	  File file = new File("C:\\Users\\jtfgb\\OneDrive - ISCTE-IUL\\Documentos\\ES_Projeto Teste\\src\\com\\jasml\\compiler\\SourceCodeParser.java"); 
		File file = new File("C:\\Users\\Francisco\\Desktop\\IGE\\3º Ano\\3Ano2Sem\\Engenharia de Software\\jasml_0.10\\src\\com\\jasml\\compiler\\SourceCodeParser.java"); 

		Metricas_Metodos mm = new Metricas_Metodos(2);
		mm.analyze(file);

		for(int i=0;i!=mm.al.get(0).size();i++) {
			System.out.println(mm.al.get(0).get(i));
			String aux= mm.al.get(0).get(i);
			System.out.println(mm.al.get(1).get(i));
			//	System.out.println(mm.getMap().get(aux));
		}

		System.out.println("A classe tem " +  mm.getLoc_class() + " linhas de código.");

		System.out.println("A classe tem " +  mm.getNom_class() + " metodos.");
	}





}


