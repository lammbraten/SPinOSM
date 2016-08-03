package de.spinosm.graph.data;

import java.util.HashMap;

import de.westnordost.osmapi.map.data.BoundingBox;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.Relation;
import de.westnordost.osmapi.map.data.Way;
import de.westnordost.osmapi.map.handler.MapDataHandler;

public class OsmXmlHandler implements MapDataHandler  {
	HashMap<Long, Node> nodes; 
	HashMap<Long, Way> ways;
	
	public OsmXmlHandler(HashMap<Long, Node> nodes, HashMap<Long, Way> ways){
		this.nodes = nodes;
		this.ways = ways;
	}
	
	
	@Override
	public void handle(BoundingBox bounds) {
		System.out.println("Bounds");
		System.out.println(bounds.getAsLeftBottomRightTopString());
	}

	@Override
	public void handle(Node node) {
		nodes.put(node.getId(), node);
	}

	@Override
	public void handle(Way way) {
		ways.put(way.getId(), way);
	}

	@Override
	public void handle(Relation relation) {
		System.out.println("Relation");
		
	}
	
}
