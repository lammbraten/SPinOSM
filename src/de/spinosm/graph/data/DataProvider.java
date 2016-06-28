package de.spinosm.graph.data;

import java.util.Collection;
import java.util.List;

import de.westnordost.osmapi.OsmConnection;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.Relation;
import de.westnordost.osmapi.map.data.Way;

public interface DataProvider {
	public Node getNode(long id);
	public Way getWay(long id);
	public List<Node> getNodesForWay(long id);
	public List<Way>getWays(Collection<Long> ids);
	public List<Way> getWaysForNode(long id);
	public Relation getRelation(long id);
	public List<Relation> getRelations(Collection<Long> ids);
	public List<Relation> getRelationsForNode(long id);
	public List<Relation> getRelationsForRelation(long id);
	public List<Relation> getRelationsForWay(long id);
	public OsmConnection getConnection();
}
