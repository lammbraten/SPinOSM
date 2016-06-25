package de.spinosm.graph;

import java.util.LinkedList;

public abstract class GraphNode implements Knot {

	private LinkedList<Edge> edges;
	
	@Override
	public boolean hasNext() {
		if(!edges.isEmpty())
			return true;
		return false;
	}

	@Override
	public Knot next() {
		return edges.iterator().next().getOtherKnotThan(this);
	}

	@Override
	public LinkedList<Edge> getEdges() {
		return edges;
	}

	@Override
	public void removeEdge(Edge toRemove) {
		edges.removeIf(e -> e.equals(toRemove));
	}

	@Override
	public void addEdge(Edge e) {
		edges.add(e);
	}

	@Override
	public void setEdges(LinkedList<Edge> edges) {
		this.edges = edges;
		
	}

	@Override
	public void getOutDegree() {
		this.edges.size();
	}
}
