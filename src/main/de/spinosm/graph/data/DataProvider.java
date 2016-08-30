package de.spinosm.graph.data;

import java.util.Set;

import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetEdge;

public interface DataProvider {
	public RouteableVertex getVertex(long id);
	public Set<StreetEdge> getStreetEdgesForVertex(RouteableVertex startVertex);
}
