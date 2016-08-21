package de.spinosm.gui.drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointRenderer;

public class GenericWaypointRenderer implements WaypointRenderer<Waypoint> {
	private int r = 5;
	private int d = 2*r;
	private Color color;
	
	public GenericWaypointRenderer(Color color, int radius) {
		this.color = color;
		this.r = radius;
	}



	@Override
	public void paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint waypoint) {
		g = (Graphics2D)g.create();

		Point2D point = map.getTileFactory().geoToPixel(waypoint.getPosition(), map.getZoom());
		
		int x = (int)point.getX();
		int y = (int)point.getY();
		
	    g.setColor(color);
	    g.fillOval(x-r, y-r, d, d);
	    drawLabel(x, y, waypoint, g);

		g.dispose();
	}



	private void drawLabel(int x, int y, Waypoint wp, Graphics2D g) {
		if(!(wp instanceof LabeldWayPoint))
			return;
		LabeldWayPoint lwp = (LabeldWayPoint) wp;		
		if(lwp.getLabel() == null)
			return;
		g.setFont(new Font("Helvitica", Font.BOLD, 12));
		FontMetrics metrics = g.getFontMetrics();
		int th = 1 + metrics.getAscent();
		g.setColor(Color.BLUE);
		g.drawString(lwp.getLabel(),x + 3,y + 3 + th);
		
	}

}
