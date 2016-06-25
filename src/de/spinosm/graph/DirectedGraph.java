package de.spinosm.graph;

import java.util.LinkedList;
import java.util.List;

public abstract class DirectedGraph implements Graph {

	LinkedList<Knot> nodes;
	
	
	@Override
	public LinkedList<Knot> getNodes() {
		return nodes;
	}


	@Override
	/**
	 * Heyho
	 */
	public void setNodes(LinkedList<Knot> nodes) {
		this.nodes = nodes;		
	}

}
