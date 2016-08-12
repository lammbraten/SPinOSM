package de.spinosm.graph.data;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetVertex;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.Way;

public class DefaultDataProvider implements DataProvider {

	@Override
	public StreetVertex getStreetJunction(long id) {
		return null;
	}

	@Override
	public StreetEdge getStreetEdge(long id) {
		return null;
	}

	@Override
	public List<StreetVertex> getStreetJunctionsForStreetEdge(long id) {
		return null;
	}

	@Override
	public List<StreetEdge> getStreetEdgesForStreetJunction(long id) {
		return new LinkedList<StreetEdge>();
	}

	@Override
	public List<Way> getWaysForNode(long id) {
		return null;
	}

	@Override
	public List<Node> getWayNodesComplete(long id, List<Long> nids) {
		return null;
	}

	@Override
	public Set<StreetEdge> getStreetEdgesForNode(StreetVertex sj) {
		return new HashSet<StreetEdge>();
	}
}
