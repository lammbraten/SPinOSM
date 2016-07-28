package de.spinosm.graph;


import java.util.LinkedList;

import de.westnordost.osmapi.map.data.LatLon;
import de.westnordost.osmapi.map.data.OsmNode;

public class StreetJunction extends GraphNode {

	private LatLon position; //needed for Heuristic
	private boolean edgesLoaded = false;

	public StreetJunction(OsmNode osmNode, LinkedList<RouteableEdge> edges){
		super(osmNode.getId());
		this.setEdges(edges);	
		this.setPosition(osmNode.getPosition());
	}
	
	public StreetJunction(StreetJunction streetJunction) {
		super(streetJunction.getId(), streetJunction.getDistance());
		this.setPosition(streetJunction.position);
	}
	
	public StreetJunction(OsmNode osmNode){
		super(osmNode.getId());		
		this.setPosition(osmNode.getPosition());
	}

	@Override
	public LatLon getPosition() {
		return position;
	}

	public void setPosition(LatLon position) {
		this.position = position;
	}
	
	@Override
	public boolean equals(Object other) {
		if(super.equals(other))
			//if(this.position.equals(((StreetJunction)other).position))
				return true;
		return false;
	}
	
	@Override
	public void setEdges(LinkedList<RouteableEdge> edges) {
		if(edges != null){
			super.setEdges(edges);	
			this.setEdgesLoaded(true);			
		}
	}

	@Override
	public boolean isEdgesLoaded() {
		return edgesLoaded;
	}

	private void setEdgesLoaded(boolean edgesLoaded) {
		this.edgesLoaded = edgesLoaded;
	}
}
