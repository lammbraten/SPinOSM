package de.spinosm.gui.drawing;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class LabeldWayPoint extends DefaultWaypoint {

	private String label;

	public LabeldWayPoint(GeoPosition gp, String label) {
		super(gp);
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
