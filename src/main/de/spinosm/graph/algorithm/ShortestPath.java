package de.spinosm.graph.algorithm;

import java.util.List;

import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.depr.Graph;

public interface ShortestPath {

	public List<RouteableNode> getShortestPath(RouteableNode start, RouteableNode end);
	public StreetGraph getGraph();
	void setGraph(StreetGraph g);
}
