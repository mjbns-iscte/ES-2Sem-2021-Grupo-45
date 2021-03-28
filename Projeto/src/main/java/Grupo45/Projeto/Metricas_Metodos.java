package Grupo45.Projeto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.w3c.dom.traversal.NodeIterator;

import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;





public class Metricas_Metodos {
	private ArrayList<String> methodNamesLOC = new ArrayList<>();
	
	private ArrayList<String> method_LOC(InputStream is) throws Exception{
		methodNamesLOC.clear();
		
        CompilationUnit cu = StaticJavaParser.parse(is);
        new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(MethodDeclaration method, Object arg) {
                super.visit(method, arg);
                methodNamesLOC.add(method.getNameAsString());
                methodNamesLOC.add(String.valueOf(method.getEnd().get().line - method.getBegin().get().column));
            }
        }.visit(cu, null);
    
    return methodNamesLOC;
}
	public ArrayList<String> getMethodNamesLOC() {
		return methodNamesLOC;
	}
	
    public static void main(String[] args) throws FileNotFoundException, Exception {
    	Metricas_Metodos mm = new Metricas_Metodos();
        File file = new File("src/main/java/Grupo45/Projeto/Metricas_Metodos.java");
        InputStream is = new FileInputStream(file);
        mm.method_LOC(is);
        for(int i = 0; i < mm.getMethodNamesLOC().size(); i++) {   
            System.out.print(mm.getMethodNamesLOC().get(i));
        } 
       //new LOC_Method().visit(StaticJavaParser.parse(file), null);
        
    }

	

}
 

