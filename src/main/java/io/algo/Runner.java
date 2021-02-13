package io.algo;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Runner {
	
	static Map<String, Integer> map = new LinkedHashMap<>();
	
	public static void put(String key, Integer value) {
		map.putIfAbsent(key, 0);
	}
	
	public static void main(String[] args) {
		List<String> list = new LinkedList<>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		System.out.println(list);
		list.remove("C");
		System.out.println(list);
	}
}
