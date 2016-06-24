package de.spinosm.graph;

import java.util.LinkedList;

public interface Graph {

	LinkedList<Knot> getNodes();
	public void setNodes(LinkedList<Knot> nodes);
}
