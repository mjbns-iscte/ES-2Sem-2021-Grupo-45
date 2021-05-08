package Grupo45.Projeto;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 *	Date May 07-2021
 * This class is responsible to count the cyclomatic complexity of given methods
 * @author G45
 * @author Andre Amado, Guilherme Henriques, João Guerra, Miguel Nunes, Francisco Mendes, Tiago Geraldo
 * @version 1.0 
 *
 */
public class Cyclo_method {

	/**
	 * This attribute saves the names of the methods in an array
	 */
	private static ArrayList<String> names = new ArrayList<String>();

	/**
	 * This string represents the name of one method
	 */
	private String method_name;

	/**
	 * @param is the file to be analyzed
	 * @return a map with Strings, which represents the name of the methods, and Integers that represent the count of the cyclomatic complexity for
	 * each method;
	 */
	public HashMap<String, Integer> cyclo_method(InputStream is) {

		CompilationUnit cu = StaticJavaParser.parse(is);
		Counter c = new Counter();
		new VoidVisitorAdapter<Counter>() {

			/**
			 * this method is responsible to visit and save the name of all methods in an array and a map, and add 1 to the cyclomatic complexity 
			 * of the method;
			 */
			public void visit(MethodDeclaration method, Counter c) {
				method_name = method.getSignature().asString();
				names.add(method_name);
				super.visit(method, c);
				c.add(method_name);
			}

			/**
			 * this method is responsible to visit and save the name of all constructors in an array and a map, and add 1 to the cyclomatic complexity 
			 * of the constructor;
			 */
			public void visit(ConstructorDeclaration method, Counter c) {
				method_name = method.getSignature().asString();
				names.add(method_name);
				super.visit(method, c);
				c.add(method_name);
			}

			/**
			 * this method is responsible to visit all the methods, count the number of ForEach statements and add that number to the cyclomatic 
			 * complexity of that method;
			 */
			public void visit(ForEachStmt statement, Counter c) {
				c.add(method_name);
				super.visit(statement, c);
			}

			/**
			 * this method is responsible to visit all the methods, count the number of For statements and add that number to the cyclomatic 
			 * complexity of that method;
			 */
			public void visit(ForStmt statement, Counter c) {
				c.add(method_name);
				super.visit(statement, c);
			}

			/**
			 * this method is responsible to visit all the methods, count the number of Do statements and add that number to the cyclomatic 
			 * complexity of that method;
			 */
			public void visit(DoStmt statement, Counter c) {
				c.add(method_name);
				super.visit(statement, c);
			}

			/**
			 * this method is responsible to visit all the methods, count the number of While statements and add that number to the cyclomatic 
			 * complexity of that method;
			 */
			public void visit(WhileStmt statement, Counter c) {
				c.add(method_name);
				super.visit(statement, c);
			}

			/**
			 * this method is responsible to visit all the methods, count the number of IF statements and add that number to the cyclomatic 
			 * complexity of that method;
			 */
			public void visit(IfStmt statement, Counter c) {
				c.add(method_name);
				super.visit(statement, c);
			}

			/**
			 * this method is responsible to visit all the methods, count the number of Switch statements and add that number to the cyclomatic 
			 * complexity of that method;
			 */
			public void visit(SwitchStmt statement, Counter c) {
				for (@SuppressWarnings("unused")
				SwitchEntry s : statement.getEntries()) {
					c.add(method_name);
				}
				super.visit(statement, c);
			}
		}.visit(cu, c);
		return c.getMap();
	}

	/**
	 * @return the array which contains the names of all methods;
	 */
	public static ArrayList<String> getNames() {
		return names;
	}
}
