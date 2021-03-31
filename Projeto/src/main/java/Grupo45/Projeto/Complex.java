package Grupo45.Projeto;

import java.util.ArrayList;
import java.util.HashMap;

public class Complex {
	private HashMap<String, Integer> map = new HashMap<>();
	private int h = 0;
	
	public void add(String method_name) {
		if(map.containsKey(method_name)) {
			
		map.put(method_name, map.get(method_name) + 1);
	} 
		else { map.put(method_name, 1);
	}
	}
	
	

	public HashMap<String, Integer> getMap() {
		return map;
	}
	
	public static void main(String[] args) {
		HashMap<String, Integer> map = new HashMap<>();
		ArrayList a = new ArrayList<String>();
		
		String s = "miguel";
		String s1 = "gui";
		String s2 = "mendes";
		
		a.add(s);
		a.add(s1);
		a.add(s2);
		
		map.put(s, 10);
		map.put(s1, 12);
		map.put(s2, 19);
		
		for(int i=0; i<a.size(); i++) {
			String nomes= (String) a.get(i);
			System.out.println(nomes);
			System.out.println(map.get(nomes));
		}

	}

}
