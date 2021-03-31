package Grupo45.Projeto;

import java.util.HashMap;

public class Counter {
	private HashMap<String, Integer> map = new HashMap<>();
	
	public void add(String method_name) {
		if(map.containsKey(method_name)) {
		map.put(method_name, map.get(method_name) + 1);
		}
		else {
			map.put(method_name, 1);
		}
	}
	

	public HashMap<String, Integer> getMap() {
		return map;
	}
}
