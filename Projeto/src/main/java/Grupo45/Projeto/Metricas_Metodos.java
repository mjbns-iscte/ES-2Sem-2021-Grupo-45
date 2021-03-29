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
	
	public Metricas_Metodos() {
		super();
		al.add(new ArrayList<String>());
		al.add(new ArrayList<String>());
		
	}

//	private ArrayList<String> method_LOC(InputStream is) throws Exception{
//		methodNamesLOC.clear();
//		
//        CompilationUnit cu = StaticJavaParser.parse(is);
//       
//        System.out.println(1);--
//        new VoidVisitorAdapter<Object>() {
//            public void visit(MethodDeclaration method, Object arg) {
//                super.visit(method, arg);
//                System.out.println(1);
//                methodNamesLOC.add(method.getNameAsString());
//                methodNamesLOC.add(String.valueOf(method.getEnd().get().line - method.getBegin().get().line +1));
//            }
//            public void visit(ConstructorDeclaration method, Object arg) {
//                super.visit(method, arg);
//                System.out.println(1);
//                methodNamesLOC.add(method.getNameAsString());
//                methodNamesLOC.add(String.valueOf(method.getEnd().get().line - method.getBegin().get().line +1));
//            }
//        
//        }.visit(cu,null);
//        
//    return methodNamesLOC;
//}
	public void analyze(File file) throws FileNotFoundException {
		 InputStream is = new FileInputStream(file);
		 CompilationUnit cu = StaticJavaParser.parse(is);
		 new LOC_method().visit(cu, al);
		 
	}
	




	public ArrayList<ArrayList<String>> getAl() {
		return al;
	}

	//	
    public static void main(String[] args) throws FileNotFoundException, Exception {
    	//File file = new File("src/main/java/Grupo45/Projeto/Metricas_Metodos.java");
        File file = new File("C://jasml//src//com//jasml//compiler//ParsingException.java");
    	
    	Metricas_Metodos mm = new Metricas_Metodos();
    	mm.analyze(file);
    	
        for(int i=0;i!=mm.al.get(0).size();i++) {
        	System.out.println(mm.al.get(0).get(i));
        	System.out.println(mm.al.get(1).get(i));
        }
    }

	

}
 

