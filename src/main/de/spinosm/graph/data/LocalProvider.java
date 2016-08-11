package de.spinosm.graph.data;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetJunction;
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
	public StreetJunction getStreetJunction(long id) {
		OsmNode n = (OsmNode) osmxmlelements.getNode(id);
		return 	buildNewStreetJunction(n);
	}

	@Override
	public StreetEdge getStreetEdge(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StreetJunction> getStreetJunctionsForStreetEdge(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StreetEdge> getStreetEdgesForStreetJunction(long id) {
		// TODO Auto-generated method stub
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
	public Set<StreetEdge> getStreetEdgesForNode(StreetJunction sj) {
		Set<StreetEdge> returnValue;
		if(sj == null){
			throw new IllegalArgumentException("Node not existing in OSM");
		}else{
			returnValue = getRouteableEdgesForNode(sj);
		}
		return returnValue;
	}

}
