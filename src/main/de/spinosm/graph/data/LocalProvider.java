package de.spinosm.graph.data;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetJunction;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.OsmNode;
import de.westnordost.osmapi.map.data.Way;

public class LocalProvider extends AbstractProvider{

	File xmlFile;
	OsmXmlFilerHandler osmxmlelements;
	
	public LocalProvider(String filePath){
		this.xmlFile = new File(filePath);
		osmxmlelements = new OsmXmlFilerHandler(xmlFile);
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
	public List<StreetEdge> getStreetEdges(Collection<Long> ids) {
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

}
