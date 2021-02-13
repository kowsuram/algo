package io.algo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class UndirectedGraph {
	
	
	static Map<Vertex, CopyOnWriteArrayList<Vertex>> uGraph = new ConcurrentHashMap<>();
	
	public static void addVertex(String label) {
		Vertex vertex = new Vertex(label);
		uGraph.putIfAbsent(vertex, new CopyOnWriteArrayList<>());
	}
	
	public static void addEdge(String src, String dest) {
		Vertex vertex = new Vertex(src);
		if (uGraph.containsKey(vertex)) {
			Vertex vtx = new Vertex(dest);
			uGraph.get(vertex).add(vtx);
		} else {
			uGraph.putIfAbsent(vertex, new CopyOnWriteArrayList<>());
		}
	}
	
	
	public static void removeVertex(String label) {
		Vertex vertex = new Vertex(label);
		uGraph.remove(vertex);
		uGraph.forEach((k, v) -> {
			v.forEach(lEle -> {
				if (lEle.getLabel().equals(label)) {
					Vertex vtx = new Vertex(label);
					v.remove(vtx);
				}
			});
		});
	}
	
	
	public static void removeEdge(String src, String dest) {
		CopyOnWriteArrayList<Vertex> copy = uGraph.get(new Vertex(src));
		copy.stream().filter(p -> p.getLabel().equals(dest)).map(p -> new Vertex(dest)).forEach(copy::remove);
	}
	
	public static void main(String[] args) {
		addVertex("A");
		addVertex("B");
		addVertex("C");
		addVertex("D");
		addVertex("E");
		addEdge("A", "B");
		addEdge("A", "C");
		addEdge("A", "E");
		addEdge("B", "A");
		addEdge("B", "C");
		addEdge("C", "A");
		addEdge("C", "B");
		addEdge("C", "D");
		addEdge("C", "E");
		addEdge("D", "C");
		addEdge("E", "A");
		addEdge("E", "C");
		System.out.println("Displaying actual graph.");
		displayGraph();
		System.out.println("Removing Vertex A");
		removeVertex("A");
		displayGraph();
		System.out.println("Removing Edge from C to B");
		removeEdge("C", "B");
		displayGraph();
	}
	
	public static void displayGraph() {
		uGraph.forEach((k, v) -> {
			System.out.print(k.getLabel() + " -> ");
			v.forEach(adj -> {
				System.out.print(" " + adj.getLabel() + " ");
			});
			System.out.println();
		});
	}
	
	static class Vertex {
		
		private String label;
		
		public Vertex(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return label;
		}
		
		public void setLabel(String label) {
			this.label = label;
		}
		
		@Override
		public boolean equals(Object o) {
			Vertex vertex = (Vertex) o;
			return vertex.getLabel().equalsIgnoreCase(this.getLabel());
		}
		
		@Override
		public int hashCode() {
			return label.hashCode();
		}
		
		@Override
		public String toString() {
			return "Vertex{" + "label='" + label + '\'' + '}';
		}
	}
}
