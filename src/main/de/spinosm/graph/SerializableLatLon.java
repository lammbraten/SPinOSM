package de.spinosm.graph;

import java.io.Serializable;

import de.westnordost.osmapi.map.data.LatLon;

public class SerializableLatLon extends SomeLatLon implements  Serializable{
	private static final long serialVersionUID = -1545221539333715544L;
	private double lat;
	private double lon;
	
	public SerializableLatLon(LatLon position) {
		super(position.getLatitude(), position.getLongitude());
		this.lat = super.getLatitude();
		this.lon = super.getLongitude();
	}
	
	@Override
	public double getLatitude() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	@Override
	public double getLongitude() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}
}
