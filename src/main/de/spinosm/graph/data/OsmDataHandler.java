package de.spinosm.graph.data;

import java.util.List;

import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.Way;

public interface OsmDataHandler {
	public List<Way> getWaysForNode(long id);
	public List<Node> getWayNodesComplete(long id, List<Long> nids);
}
