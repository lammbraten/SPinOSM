package de.spinosm.graph.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import de.westnordost.osmapi.map.data.BoundingBox;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.Relation;
import de.westnordost.osmapi.map.data.Way;
import de.westnordost.osmapi.map.handler.MapDataHandler;

public class OsmXmlHandler implements MapDataHandler  {
	HashMap<Long, Node> nodes; 
	HashMap<Long, Way> ways;
	HashMap<Long, Set<Long>> waysOfNode;
	
	public OsmXmlHandler(HashMap<Long, Node> nodes, HashMap<Long, Way> ways, HashMap<Long, Set<Long>> waysOfNode){
		this.nodes = nodes;
		this.ways = ways;
		this.waysOfNode = waysOfNode;
	}
	
	
	@Override
	public void handle(BoundingBox bounds) {
		//System.out.println("Bounds");
		//System.out.println(bounds.getAsLeftBottomRightTopString());
	}

	@Override
	public void handle(Node node) {
		nodes.put(node.getId(), node);
	}

	@Override
	public void handle(Way way) {
		ways.put(way.getId(), way);
		for(Long nid : way.getNodeIds()){
			Set<Long> wayIds = new HashSet<Long>();
			if(waysOfNode.containsKey(nid))
				wayIds = waysOfNode.get(nid);
			wayIds.add(way.getId());
			waysOfNode.put(nid, wayIds);
		}
	}

	@Override
	public void handle(Relation relation) {
		System.out.println("Relation");
		
	}
	
}
