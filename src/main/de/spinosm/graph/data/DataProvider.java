package de.spinosm.graph.data;

import java.util.Collection;
import java.util.List;

import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetJunction;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.Way;

public interface DataProvider {
	/*
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
	public OsmConnection getConnection();*/
	
	public StreetJunction getStreetJunction(long id);
	public StreetEdge getStreetEdge(long id);
	public List<StreetJunction> getStreetJunctionsForStreetEdge(long id);
	public List<StreetEdge>getStreetEdges(Collection<Long> ids);
	public List<StreetEdge> getStreetEdgesForStreetJunction(long id);
	public List<Way> getWaysForNode(long id);
	public List<Node> getWayNodesComplete(long id, List<Long> nids);
}
