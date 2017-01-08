package de.spinosm.graph.algorithm;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetGraph;

public interface ShortestPath extends Serializable {
	public List<RouteableVertex> getShortestPath(RouteableVertex start, RouteableVertex end);
	public StreetGraph getGraph();
	public void setGraph(StreetGraph g);
	public List<RouteableVertex> getBorderVertices();
	public List<RouteableVertex> getFinishedVertices();
	public Set<RouteableVertex> getVisitedVertices();
	void setVisitedVertices(HashSet<RouteableVertex> visitedVertecies);
	public List<RouteableVertex> getCalculatedShortestPath();
	
}
