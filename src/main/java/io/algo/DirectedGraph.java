package io.algo;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class DirectedGraph {
	
	static Map<Vertex, List<Edge>> dGraph = new ConcurrentHashMap<>();
	
	static class Vertex {
		private String label;
		
		public Vertex(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return label;
		}
		
		@Override
		public int hashCode() {
			return label.hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			Vertex vtx = (Vertex) obj;
			return vtx.getLabel().equals(this.getLabel());
		}
		
		@Override
		public String toString() {
			return "Vertex{" +
					       "label='" + label + '\'' +
					       '}';
		}
	}
	
	static class Edge {
		private String srcVtx, destVtx;
		
		public Edge(String srcVtx, String destVtx) {
			this.srcVtx = srcVtx;
			this.destVtx = destVtx;
		}
		
		public String getSrcVtx() {
			return srcVtx;
		}
		
		public String getDestVtx() {
			return destVtx;
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Edge edge = (Edge) o;
			return Objects.equals(srcVtx, edge.srcVtx) &&
					       Objects.equals(destVtx, edge.destVtx);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(srcVtx, destVtx);
		}
		
		@Override
		public String toString() {
			return "Edge{" +
					       "srcVtx='" + srcVtx + '\'' +
					       ", destVtx='" + destVtx + '\'' +
					       '}';
		}
	}
	
	
	public static void addVertex(String label) {
		dGraph.putIfAbsent(new Vertex(label), new CopyOnWriteArrayList<>());
	}
	
	public static void addEdge(String src, String dest) {
		Vertex vertex = new Vertex(src);
		Edge edge = new Edge(src, dest);
		if (dGraph.containsKey(vertex)) {
			dGraph.get(vertex).add(edge);
		} else {
			List<Edge> edges = new CopyOnWriteArrayList<>();
			edges.add(edge);
			addVertex(src);
			dGraph.get(vertex).add(edge);
		}
	}
	
	public static void displayGraph() {
		dGraph.forEach((k, v) -> {
			v.forEach(op -> {
				System.out.println(k.getLabel() + " -> " + op.getDestVtx());
			});
		});
	}
	
	
	public static void removeVertex(String label) {
		Vertex vertex = new Vertex(label);
		dGraph.remove(vertex);
		dGraph.forEach((k, v) -> {
			v.stream().filter(o -> o.getDestVtx().equals(vertex.getLabel())).forEach(p -> v.remove(p));
		});
	}
	
	
	public static void removeEdge(String label, String dest) {
		dGraph.forEach((k,v) -> {
			v.remove(new Edge(label, dest));
		});
	}
	
	
	public static void main(String[] args) {
		addVertex("P");
		addVertex("Q");
		addVertex("R");
		addVertex("T");
		addVertex("S");
		addEdge("P", "Q");
		addEdge("P", "T");
		addEdge("R", "P");
		addEdge("R", "S");
		addEdge("T", "R");
		addEdge("T", "S");
		System.out.println("Displaying Graph");
		displayGraph();
		System.out.println("Removing R");
		removeVertex("R");
		displayGraph();
		System.out.println("Removing Edge P to Q");
		removeEdge("P", "Q");
		displayGraph();
	}
}
