package Grupo45.Projeto; 

import java.util.HashMap;

/**
 * Date May 07-2021
 * This class is an auxiliar class to count the cyclomatic complexity of each method
 * @author G45
 * @author Andre Amado, Guilherme Henriques, João Guerra, Miguel Nunes, Francisco Mendes, Tiago Geraldo
 * @version 1.0
 */

public class Counter {

	/**HashMap that has the method name as a key and its cyclomatic complexity as an 
	 * Integer
	 */

	private HashMap<String, Integer> map = new HashMap<>();

	/**
	 * This method increases the value of the cyclomatic complexity if the method name is already in the HashMAp, otherwise it add the method name as well 
	 * @param method_name is the name of the method
	 */
	public void add(String method_name) {
		if(map.containsKey(method_name)) {
			map.put(method_name, map.get(method_name) + 1);
		}
		else {
			map.put(method_name, 1);
		}
	}

	/** 
	 * @return returns the HashMap that has the method names as key and the cyclomatic complexity as Integer
	 */

	public HashMap<String, Integer> getMap() {
		return map;
	}
}
