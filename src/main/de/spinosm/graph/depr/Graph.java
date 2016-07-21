package de.spinosm.graph.depr;

import java.util.TreeSet;

import de.spinosm.graph.RouteableNode;

@SuppressWarnings("derpricated")
public interface Graph {

	public TreeSet<RouteableNode> getNodes();
	public void setNodes(TreeSet<RouteableNode> nodes);
}
