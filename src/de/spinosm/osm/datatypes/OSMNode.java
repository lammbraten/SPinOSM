package de.spinosm.osm.datatypes;

import java.util.Map;

public class OSMNode {

	private String id;
	private String lat;
	private String lon;
	private String version;
	private Map<String, String> tags;
	
	public OSMNode(String id, String latitude, String longitude, String version) {
		this.id = id;
		this.lat = latitude;
		this.lon = longitude;
		this.version = version;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof OSMNode){
			OSMNode n = (OSMNode) o;
			if(this.id.equals(n.id) &&
				this.lat.equals(n.lat)&&
				this.lon.equals(n.lon)&&
				this.version.equals(n.version))
			return true;
		}
		return false;
		
	}

}
