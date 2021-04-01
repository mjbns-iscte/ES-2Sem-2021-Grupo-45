package Grupo45.Projeto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.w3c.dom.traversal.NodeIterator;

import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
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
	private int loc_clas =0;
	
	public Metricas_Metodos(int j) {
		super();
		for(int i=0;i!=j;i++)
		al.add(new ArrayList<String>());
		
		
	}


	public void analyze(File file) throws FileNotFoundException {
		 loc_clas =0;
		 InputStream is = new FileInputStream(file);
		 CompilationUnit cu = StaticJavaParser.parse(is);
		 new LOC_method().visit(cu, al);
		 
	}
	




	public ArrayList<ArrayList<String>> getAl() {
		return al;
	}

	//	
    public static void main(String[] args) throws FileNotFoundException, Exception {
        File file = new File("C://jasml//src//com//jasml//compiler//SourceCodeParser.java");
    	
    	Metricas_Metodos mm = new Metricas_Metodos(2);
    	mm.analyze(file);
    	
        for(int i=0;i!=mm.al.get(0).size();i++) {
        	System.out.println(mm.al.get(0).get(i));
        	System.out.println(mm.al.get(1).get(i));
        }
    }

	

}
 

