package de.spinosm.graph.algorithm;

import java.util.List;
import java.util.TreeSet;

import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetGraph;

public interface ShortestPath {
	public List<RouteableVertex> getShortestPath(RouteableVertex start, RouteableVertex end);
	public StreetGraph getGraph();
	public void setGraph(StreetGraph g);
	public List<RouteableVertex> getBorderVertecies();
	public List<RouteableVertex> getFinishedVertecies();
	TreeSet<RouteableVertex> getVisitedVertecies();
	void setVisitedVertecies(TreeSet<RouteableVertex> visitedVertecies);
	
}
