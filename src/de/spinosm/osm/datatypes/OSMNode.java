package de.spinosm.osm.datatypes;

import java.util.Map;

public class OSMNode{

	private String id;
	private String lat;
	private String lon;
	private String version;
	@SuppressWarnings("deprecation")
	private Map<String, String> tags;

	public OSMNode(String id, String latitude, String longitude, String version) {
		this.id = id;
		this.lat = latitude;
		this.lon = longitude;
		this.version = version;
	}
	
	@Override 
	public OSMNode clone(){
		return new OSMNode(id, lat, lon, version);
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
	
	@Override 
	public String toString(){
		return new String("OSMNode: id: "+id+
				", lat: "+lat+
				", lon: "+lon+
				", version: "+version);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}
}
