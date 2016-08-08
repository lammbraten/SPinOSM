package de.spinosm.graph;

import java.io.Serializable;

public interface RouteableEdge extends Cloneable, Serializable {
	public double getWeight();
	public void setWeight(double cost);
}
