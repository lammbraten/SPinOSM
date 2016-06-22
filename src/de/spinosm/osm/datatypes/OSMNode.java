package de.spinosm.osm.datatypes;

import java.util.Map;

public class OSMNode {

	private String uid;
	private String lat;
	private String lon;
	private String version;
	private Map<String, String> tags;
	
	public OSMNode(String id, String latitude, String longitude, String version, Map<String, String> tags) {
		this.uid = id;
		this.lat = latitude;
		this.lon = longitude;
		this.version = version;
		this.tags = tags;
	}

}
