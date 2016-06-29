package de.spinosm.graph;


import java.util.LinkedList;

import de.westnordost.osmapi.map.data.LatLon;
import de.westnordost.osmapi.map.data.OsmNode;

public class StreetJunction extends GraphNode {

	LatLon position; //needed for Heuristic

	public StreetJunction(OsmNode osmNode, LinkedList<RouteableEdge> edges){
		super(osmNode.getId());
		super.setEdges(edges);
		this.setPostion(osmNode.getPosition());
	}
	
	public StreetJunction(StreetJunction streetJunction) {
		super(streetJunction.getId(), streetJunction.getDistance());
		this.setPostion(streetJunction.position);
	}

	public LatLon getPostion() {
		return position;
	}

	public void setPostion(LatLon position) {
		this.position = position;
	}
	
	@Override
	public boolean equals(Object other) {
		if(super.equals(other))
			if(this.position.equals(((StreetJunction)other).position))
				return true;
		return false;
	}
}
