package de.spinosm.graph.weights;

import java.util.LinkedList;

import de.spinosm.common.Common;
import de.spinosm.common.Vehicle;
import de.westnordost.osmapi.map.data.LatLon;
import de.westnordost.osmapi.map.data.Way;

public class PythagoreanTimeWeight extends PythagoreanDistanceWeight {

	@Override
	public double calcCosts(LinkedList<LatLon> nodes, Way way) {
		double distance = super.calcCosts(nodes, way);		
		double speed = Common.getSpeedLimits(way, Vehicle.CAR);
		return distance /  speed;
	}
}
