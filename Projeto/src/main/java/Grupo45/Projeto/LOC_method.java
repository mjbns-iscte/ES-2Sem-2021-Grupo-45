package Grupo45.Projeto;

import java.util.ArrayList;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * Date May 07-2021
* This class adjusts the function of VoidVisitorAdapter visit() method, to search for methods and constructions and analyze its 'lines of code' metric
* @author G45
* @author Andre Amado, Guilherme Henriques, João Guerra, Miguel Nunes, Francisco Mendes, Tiago Geraldo
* @version 1.0
*/
public class LOC_method extends VoidVisitorAdapter<ArrayList<ArrayList<String>>> {

	/**
    *Adjusts the visit method to count method lines
    */	
	public void visit(MethodDeclaration method, ArrayList<ArrayList<String>> aa) {
		aa.get(0).add(method.getSignature().asString());
		aa.get(1).add(String.valueOf(method.getEnd().get().line - method.getBegin().get().line + 1));
		super.visit(method, aa);
	}

	/**
     *Adjusts the visit method to count constructor lines
     */
	
	public void visit(ConstructorDeclaration method, ArrayList<ArrayList<String>> aa) {
		aa.get(0).add(method.getSignature().asString());
		aa.get(1).add(String.valueOf(method.getEnd().get().line - method.getBegin().get().line + 1));
		super.visit(method, aa);
	}
}
