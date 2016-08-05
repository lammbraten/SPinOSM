package de.spinosm.gui.drawing;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.AbstractPainter;

public class PointPainter<P extends Point> extends AbstractPainter<JXMapViewer>{

	Set<P> points = new HashSet<P>();
	
	public PointPainter(){
		setAntialiasing(false);
		setCacheable(false);
	}

	@Override
	protected void doPaint(Graphics2D g, JXMapViewer map, int width, int height) {
		Rectangle viewportBounds = map.getViewportBounds();

		g.translate(-viewportBounds.getX(), -viewportBounds.getY());

		for (P p : getPoints()){
			Point2D point = map.getTileFactory().geoToPixel(p.getPosition(), viewer.getZoom());
			
			int x = (int)point.getX();
			int y = (int)point.getY();
			
			
			g.drawOval(p.x, p.y, 5, 5);
		}

		g.translate(viewportBounds.getX(), viewportBounds.getY());
		
	}

	public Set<P> getPoints() {
		return points;
	}

	public void setPoints(Set<P> points) {
		this.points = points;
	}


}
