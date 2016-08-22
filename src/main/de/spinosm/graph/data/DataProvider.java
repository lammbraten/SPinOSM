package de.spinosm.graph.data;

import java.util.List;
import java.util.Set;

import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetVertex;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.Way;

public interface DataProvider {
	public StreetVertex getStreetJunction(long id);
	public StreetEdge getStreetEdge(long id);
	public List<StreetVertex> getStreetJunctionsForStreetEdge(long id);
	public Set<StreetEdge>getStreetEdgesForVertex(StreetVertex sj);
	public List<StreetEdge> getStreetEdgesForStreetJunction(long id);
	public List<Way> getWaysForNode(long id);
	public List<Node> getWayNodesComplete(long id, List<Long> nids);
}
