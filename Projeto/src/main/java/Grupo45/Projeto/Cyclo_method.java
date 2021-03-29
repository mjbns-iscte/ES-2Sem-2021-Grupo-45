package Grupo45.Projeto;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class Cyclo_method {
	private ArrayList names = new ArrayList<String>();
	private ArrayList numbers = new ArrayList<Integer>();
	private String method_name;

	private HashMap<String, Integer> cyclo_method(InputStream is) {

		CompilationUnit cu = StaticJavaParser.parse(is);
		Complex c = new Complex();
		new VoidVisitorAdapter<Complex>() {
			public void visit(MethodDeclaration method, Complex c) {
				super.visit(method, c);
				method_name = method.getNameAsString();
				names.add(method_name);

			}

			public void visit(ConstructorDeclaration method, Complex c) {
				super.visit(method, c);
				method_name = method.getNameAsString();
				names.add(method_name);

			}

			public void visit(ForEachStmt statement, Complex c) {
				c.add(method_name);
				super.visit(statement, c);
			}

			public void visit(ForStmt statement, Complex c) {
				c.add(method_name);
				super.visit(statement, c);
			}

		}.visit(cu, c);
		return c.getMap();
	}

	public static void main(String[] args) {
		//File f = new File("C:\\Users\\migue\\Documents\\Projeto\\src\\com\\jasml\\compiler\\ParsingException.java");
		
	}

}
