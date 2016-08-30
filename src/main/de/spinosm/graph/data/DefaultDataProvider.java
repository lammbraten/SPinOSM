package de.spinosm.graph.data;

import java.util.HashSet;
import java.util.Set;

import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetEdge;

public class DefaultDataProvider implements DataProvider {

	@Override
	public RouteableVertex getVertex(long id) {
		return null;
	}

	@Override
	public Set<StreetEdge> getStreetEdgesForVertex(RouteableVertex sj) {
		return new HashSet<StreetEdge>();
	}
}
