package Grupo45.Projeto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class Cyclo_method {
	private static ArrayList names = new ArrayList<String>();
	private String method_name;

	public HashMap<String, Integer> cyclo_method(InputStream is) {

		CompilationUnit cu = StaticJavaParser.parse(is);
		Counter c = new Counter();
		new VoidVisitorAdapter<Counter>() {
			public void visit(MethodDeclaration method, Counter c) {
				method_name = method.getSignature().asString();
				names.add(method_name);
				super.visit(method, c);
				c.add(method_name);

			}

			public void visit(ConstructorDeclaration method, Counter c) {
				method_name = method.getSignature().asString();
				names.add(method_name);
				super.visit(method, c);
				c.add(method_name);

			}

			public void visit(ForEachStmt statement, Counter c) {
				c.add(method_name);
				super.visit(statement, c);
				
			}

			public void visit(ForStmt statement, Counter c) {
				c.add(method_name);
				super.visit(statement, c);
				
			}
			
			public void visit(DoStmt statement, Counter c) {
				c.add(method_name);
				super.visit(statement, c);
			}

			public void visit(WhileStmt statement, Counter c) {
				c.add(method_name);
				super.visit(statement, c);
				
			}

			public void visit(IfStmt statement, Counter c) {
				c.add(method_name);				
				super.visit(statement, c);
				
			}

		public void visit(SwitchStmt statement, Counter c) {
				for (SwitchEntry s : statement.getEntries()) {
					c.add(method_name);
				}
				super.visit(statement, c);
			}
		}.visit(cu, c);
		return c.getMap();
	}

	public static ArrayList getNames() {
		return names;
	}
	
	
	
/*
	public static void main(String[] args) throws FileNotFoundException {
		File f= new File("C:\\Users\\migue\\Documents\\Projeto\\src\\com\\jasml\\compiler\\SourceCodeParser.java");
		//File f = new File("C:\\Users\\migue\\Documents\\Projeto\\src\\com\\jasml\\compiler\\ParsingException.java");
		InputStream is = new FileInputStream(f);
		Cyclo_method cm = new Cyclo_method();
		HashMap hm = cm.cyclo_method(is);
		for (int i = 0; i < names.size(); i++) {
			String nome = (String) names.get(i);
			System.out.println(nome);
			System.out.println(hm.get(nome));
		}
		System.out.println(hm.size());
		System.out.println(names.size());

	}
*/
}
