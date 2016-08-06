package de.spinosm.graph;


import java.io.Serializable;
import java.util.LinkedList;

import de.westnordost.osmapi.map.data.OsmNode;

public class StreetJunction extends GraphNode implements Serializable {

	private static final long serialVersionUID = -6564943151645680174L;
	private boolean edgesLoaded = false;
	
	
	public StreetJunction(){
		super(0l, null);
	}
	
	public StreetJunction(OsmNode osmNode, LinkedList<RouteableEdge> edges){
		super(osmNode.getId(), osmNode.getPosition());
		this.setEdges(edges);	
	}
	
	public StreetJunction(StreetJunction streetJunction) {
		super(streetJunction.getId(), streetJunction.getPosition(), streetJunction.getDistance());
	}
	
	public StreetJunction(OsmNode osmNode){
		super(osmNode.getId() , osmNode.getPosition());		
	}
	
	
/*
	@Override
	public SerializableLatLon getPosition() {
		return position;
	}

	public void setPosition(LatLon position) {
		this.position = new SerializableLatLon(position);
	}*/
	
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
