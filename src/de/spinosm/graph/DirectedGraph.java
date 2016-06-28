package de.spinosm.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public abstract class DirectedGraph implements Graph {

	TreeSet<RouteableNode> nodes;
	
	
	@Override
	public TreeSet<RouteableNode> getNodes() {
		return nodes;
	}


	@Override
	public void setNodes(TreeSet<RouteableNode> nodes) {
		this.nodes = nodes;		
	}
}
