package de.spinosm.graph;

import java.util.LinkedList;
import java.util.List;

public abstract class DirectedGraph implements Graph {

	LinkedList<RouteableNode> nodes;
	
	
	@Override
	public LinkedList<RouteableNode> getNodes() {
		return nodes;
	}


	@Override
	/**
	 * Heyho
	 */
	public void setNodes(LinkedList<RouteableNode> nodes) {
		this.nodes = nodes;		
	}

}
