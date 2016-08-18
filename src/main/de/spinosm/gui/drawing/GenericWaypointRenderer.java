package de.spinosm.gui.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointRenderer;

public class GenericWaypointRenderer implements WaypointRenderer<Waypoint> {
	private static int r = 5;
	private static int d = 2*r;
	private Color color;
	
	
	public GenericWaypointRenderer(Color color) {
		this.color = color;
	}



	@Override
	public void paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint waypoint) {
		g = (Graphics2D)g.create();

		Point2D point = map.getTileFactory().geoToPixel(waypoint.getPosition(), map.getZoom());
		
		int x = (int)point.getX();
		int y = (int)point.getY();
		
	    g.setColor(color);
	    g.fillOval(x-r, y-r, d, d);

		g.dispose();
	}

}
