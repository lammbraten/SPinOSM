package de.spinosm.osm.api;

import java.net.MalformedURLException;
import java.net.URL;

import de.spinosm.osm.datatypes.OSMNode;
import de.spinosm.osm.datatypes.OSMRelation;
import de.spinosm.osm.datatypes.OSMWay;

public interface OSMAPI {
	URL api_url = null;
	
	void connect() throws MalformedURLException;
	OSMNode getNode(String uid);
	OSMWay getWay(String uid);
	OSMRelation getRelation(String uid);

}
