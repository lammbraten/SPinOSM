package de.spinosm.graph;

import java.util.TreeSet;

public interface Graph {

	public TreeSet<RouteableNode> getNodes();
	public void setNodes(TreeSet<RouteableNode> nodes);
}
