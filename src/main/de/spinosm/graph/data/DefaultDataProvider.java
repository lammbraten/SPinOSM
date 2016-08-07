package de.spinosm.graph.data;

import java.util.Collection;
import java.util.List;

import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetJunction;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.Way;

public class DefaultDataProvider implements DataProvider {

	@Override
	public StreetJunction getStreetJunction(long id) {
		return null;
	}

	@Override
	public StreetEdge getStreetEdge(long id) {
		return null;
	}

	@Override
	public List<StreetJunction> getStreetJunctionsForStreetEdge(long id) {
		return null;
	}

	@Override
	public List<StreetEdge> getStreetEdges(Collection<Long> ids) {
		return null;
	}

	@Override
	public List<StreetEdge> getStreetEdgesForStreetJunction(long id) {
		return null;
	}

	@Override
	public List<Way> getWaysForNode(long id) {
		return null;
	}

	@Override
	public List<Node> getWayNodesComplete(long id, List<Long> nids) {
		return null;
	}

}
