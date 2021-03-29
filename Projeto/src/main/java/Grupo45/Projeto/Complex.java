package Grupo45.Projeto;

import java.util.HashMap;

public class Complex {
	private HashMap<String, Integer> map = new HashMap<>();
	
	public void add(String method_name) {
		map.put(method_name, map.get(method_name) + 1);
		
	}
	
	

	public HashMap<String, Integer> getMap() {
		return map;
	}



	public static void main(String[] args) {
		

	}

}
