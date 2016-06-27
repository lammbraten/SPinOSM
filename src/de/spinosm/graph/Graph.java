package de.spinosm.graph;

import java.util.LinkedList;

public interface Graph {

	LinkedList<RouteableNode> getNodes();
	public void setNodes(LinkedList<RouteableNode> nodes);
}
