package Grupo45.Projeto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class LOC_method extends VoidVisitorAdapter<ArrayList<ArrayList<String>>>{
	
	 public void visit(MethodDeclaration method, ArrayList<ArrayList<String>> aa) {	
		 aa.get(0).add(method.getSignature().asString());
		 aa.get(1).add(String.valueOf(method.getEnd().get().line - method.getBegin().get().line +1));
		 //aa.put(method.getNameAsString(),method.getEnd().get().line - method.getBegin().get().line +1); 
         super.visit(method, aa);
         

     }
     public void visit(ConstructorDeclaration method, ArrayList<ArrayList<String>> aa) {
    	 aa.get(0).add(method.getSignature().asString()); 	
		 aa.get(1).add(String.valueOf(method.getEnd().get().line - method.getBegin().get().line +1));
    	 //hm.put(method.getNameAsString(),method.getEnd().get().line - method.getBegin().get().line +1);
         super.visit(method, aa);
         
        
     }
     

	
	public static void main(String[] args) throws FileNotFoundException {
	

	}

}
