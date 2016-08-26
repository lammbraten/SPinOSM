package de.spinosm.graph;

import java.io.Serializable;
import de.westnordost.osmapi.map.data.LatLon;

public interface RouteableVertex extends Comparable<RouteableVertex>, Serializable{
	public long getId();
	public void setDistance(double distance);
	public double getDistance();
	public void setHeuristic(double heuristic);
	public double getHeuristic();
	public LatLon getPosition();
	public boolean isEdgesLoaded();
	public int compareTo(RouteableVertex other);
	public void setEdgesLoaded(boolean b);
	boolean hasSameId(RouteableVertex n);
	public boolean equals(Object other);
}
