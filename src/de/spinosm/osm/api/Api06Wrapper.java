package de.spinosm.osm.api;

import java.net.MalformedURLException;
import java.net.URL;

import de.spinosm.osm.datatypes.OSMNode;
import de.spinosm.osm.datatypes.OSMRelation;
import de.spinosm.osm.datatypes.OSMWay;

public class Api06Wrapper implements OSMAPI {
	
	private static final String API06_ROOT_URL = "http://www.openstreetmap.org/api/0.6/";

	private URL api06_url;
		
	public Api06Wrapper(){
		
	}

	@Override
	public void connect() throws MalformedURLException {
		this.api_url = new URL(API06_ROOT_URL);
		
	}

	@Override
	public OSMNode getNode(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OSMWay getWay(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OSMRelation getRelation(String uid) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	



}
