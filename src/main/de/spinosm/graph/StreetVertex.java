package de.spinosm.graph;


import java.io.Serializable;
import de.westnordost.osmapi.map.data.OsmNode;

public class StreetVertex extends GraphVertex implements Serializable {

	private static final long serialVersionUID = -6564943151645680174L;
	private boolean edgesLoaded = false;
	
	public StreetVertex(){
		super(0l, null);
	}
	
	public StreetVertex(StreetVertex streetJunction) {
		super(streetJunction.getId(), streetJunction.getPosition(), streetJunction.getDistance());
	}
	
	public StreetVertex(OsmNode osmNode){
		super(osmNode.getId() , osmNode.getPosition());		
	}
		
	public StreetVertex(long id) {
		super(id, null, 0);
	}

	@Override
	public boolean isEdgesLoaded() {
		return edgesLoaded;
	}

	public void setEdgesLoaded(boolean edgesLoaded) {
		this.edgesLoaded = edgesLoaded;
	}
	
    @Override 
    public int hashCode(){
		return Long.hashCode(getId());
    }

	@Override
	public boolean equals(Object other) {
		if(super.equals(other))
			//if(this.position.equals(((StreetJunction)other).position))
				return true;
		return false;
	}
}
