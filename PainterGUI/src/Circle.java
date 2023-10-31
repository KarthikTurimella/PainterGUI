
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Circle extends PaintingPrimitive {

	Point center;
	Point radiusPoint;

	public Circle(Point c, Point r, Color color) {
		super(color, "Circle");
		center = c;
		radiusPoint = r;
	}

	public void drawGeometry(Graphics g) {
		g.setColor(super.color);
		int radius = (int) Math.abs(center.distance(radiusPoint));
		g.drawOval(center.x - radius, center.y - radius, radius * 2, radius * 2);
	}
}
