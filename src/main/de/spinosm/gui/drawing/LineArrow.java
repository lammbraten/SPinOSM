package de.spinosm.gui.drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class LineArrow implements Arrow {

    private final int ARROW_SIZE = 6;
    private final int SHIFT = 3;
    
	private float x1;
	private float y1;
	private double dx;
	private double dy;
	private double angle;
	private int len;
	private String label;

	public LineArrow(int x1, int y1, int x2, int y2, String label) {
		this.x1 = x1;
		this.y1 = y1;
		this.label = label;
		
		this.dx = x2 - x1;
		this.dy = y2 - y1;
		this.angle = Math.atan2(dy, dx);
		this.len = (int) Math.sqrt(dx*dx + dy*dy);
	}

	@Override
	public void draw(Graphics2D g) {
		transform(g);
		drawArrow(g);
	}

	private void transform(Graphics2D g) {
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);
	}
    
	private void drawArrow(Graphics2D g) {
		g.drawLine(0, SHIFT, len, SHIFT);
		drawArrowHead(g);
		drawLabel(g);
	}

	private void drawLabel(Graphics2D g) {
		if(label == null)
			return;
		g.setFont(new Font("Helvitica", Font.BOLD, 12));
		FontMetrics metrics = g.getFontMetrics();
		int th = 1 + metrics.getAscent();
		g.setColor(Color.BLACK);
		g.drawString(label, SHIFT, SHIFT + th);
	}

	private void drawArrowHead(Graphics2D g) {
		g.fillPolygon(new int[] {len, len-ARROW_SIZE, len-ARROW_SIZE, len}, new int[] {SHIFT, SHIFT-ARROW_SIZE, SHIFT+ARROW_SIZE, SHIFT}, 4);
	}

	public float getX1() {
		return x1;
	}

	public float getY1() {
		return y1;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}	
	
}
