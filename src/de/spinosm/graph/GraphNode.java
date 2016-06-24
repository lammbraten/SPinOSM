package de.spinosm.graph;

import java.util.LinkedList;

public abstract class GraphNode implements Knot {

	private LinkedList<Edge> edges;
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Knot next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Edge> getEdges() {
		return edges;
	}

	@Override
	public Edge getCheapestEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeEdge(Edge e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addEdge() {
		// TODO Auto-generated method stub

	}

}
