package de.spinosm.graph.data;

import java.util.List;
import java.util.Set;

import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetEdge;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.Way;

public interface DataProvider {
	public RouteableVertex getStreetJunction(long id);
	public StreetEdge getStreetEdge(long id);
	public List<RouteableVertex> getStreetJunctionsForStreetEdge(long id);
	public Set<StreetEdge> getStreetEdgesForVertex(RouteableVertex startVertex);
	public List<StreetEdge> getStreetEdgesForStreetJunction(long id);
	public List<Way> getWaysForNode(long id);
	public List<Node> getWayNodesComplete(long id, List<Long> nids);
}
