

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Line extends PaintingPrimitive {

	Point startPoint;
	Point endPoint;

	public Line(Point start, Point end, Color c) {
		super(c, "Line");
		startPoint = start;
		endPoint = end;
	}

	public void drawGeometry(Graphics g) {
		g.setColor(super.color);
		g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
	}
}
