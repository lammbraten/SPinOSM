package de.spinosm.graph.data;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetVertex;
import de.spinosm.graph.weights.WeightFunction;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.OsmNode;
import de.westnordost.osmapi.map.data.Way;

public class LocalProvider extends AbstractProvider{

	private File xmlFile;
	private OsmXmlFilerHandler osmxmlelements;
	
	public LocalProvider(String filePath, WeightFunction weightFunction){
		this.xmlFile = new File(filePath);
		osmxmlelements = new OsmXmlFilerHandler(xmlFile);
		this.weightFunction = weightFunction;
	}
	
	@Override
	public RouteableVertex getStreetJunction(long id) {
		OsmNode n = (OsmNode) osmxmlelements.getNode(id);
		return 	buildNewStreetVertex(n);
	}

	@Override
	public StreetEdge getStreetEdge(long id) {
		return null;
	}

	@Override
	public List<RouteableVertex> getStreetJunctionsForStreetEdge(long id) {
		return null;
	}

	@Override
	public List<StreetEdge> getStreetEdgesForStreetJunction(long id) {
		return null;
	}

	@Override
	public List<Way> getWaysForNode(long id) {
		return osmxmlelements.getWaysForNode(id);
	}

	@Override
	public List<Node> getWayNodesComplete(long id, List<Long> nids) {
		LinkedList<Node> nodes = new LinkedList<Node>();
		for(long nid : nids)
			nodes.add(osmxmlelements.getNode(nid));
		return nodes;
	}

	@Override
	public Set<StreetEdge> getStreetEdgesForVertex(RouteableVertex sj) {
		Set<StreetEdge> returnValue;
		if(sj == null){
			throw new IllegalArgumentException("Node not existing in OSM");
		}else{
			returnValue = getRouteableEdgesForVertex(sj);
		}
		return returnValue;
	}

}
