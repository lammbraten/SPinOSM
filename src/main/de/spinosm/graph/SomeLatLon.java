package de.spinosm.graph;

import de.westnordost.osmapi.map.data.OsmLatLon;

/**
 * Needed to create this DummyClass to generate  Serializeable LatLons
 * @author lammbraten
 *
 */
public class SomeLatLon extends OsmLatLon {
	public SomeLatLon(){
		super(0.0, 0.0);
	}

	public SomeLatLon(double latitude, double longitude) {
		super(latitude, longitude);
	}
}
