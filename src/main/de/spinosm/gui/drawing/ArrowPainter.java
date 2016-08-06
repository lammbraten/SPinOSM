package de.spinosm.gui.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;

import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.RouteableNode;

public class ArrowPainter implements Painter<JXMapViewer>{
	private Color color = Color.RED;
	private boolean antiAlias = true;
	
	private GeoPosition startCoordinate;
	private GeoPosition endCoordinate;
	private String label;
	
	
	public ArrowPainter(GeoPosition startPoint, GeoPosition endPoint, Color color, String labeltext){
		this.startCoordinate = startPoint;
		this.endCoordinate = endPoint;
		this.color = color;
		this.label = labeltext;
	}
	
	public ArrowPainter(GeoPosition startPoint, GeoPosition endPoint, Color color) {
		this(startPoint, endPoint, color, null);
	}

	void drawArrow ( Graphics2D g, JXMapViewer map){
		Point2D start = coordinateToPixel(map, startCoordinate);
		Point2D end = coordinateToPixel(map, endCoordinate);

		new LineArrow((int) start.getX(),(int) start.getY(), (int) end.getX(), (int) end.getY(), label).draw(g);
    }

	@Override
	public void paint(Graphics2D g, JXMapViewer map, int width, int height) {
		g = (Graphics2D) g.create();
		setMapViewport(g, map);
		setArrowDesign(g);
		drawArrow(g, map);
		g.dispose();
	}

	private void setArrowDesign(Graphics2D g) {
		if (antiAlias)
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	
		g.setColor(color);
		g.setStroke(new BasicStroke(2));
	}

	private void setMapViewport(Graphics2D g, JXMapViewer map) {
		Rectangle rect = map.getViewportBounds();
		g.translate(-rect.x, -rect.y);
	}
	
	private Point2D coordinateToPixel(JXMapViewer map, GeoPosition gp) {
		return map.getTileFactory().geoToPixel(gp, map.getZoom());
	}
}
