
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class PaintingPrimitive implements Serializable {

	Color color = null;
	String shape = null;

	public PaintingPrimitive(Color c, String s) {
		color = c;
		shape = s;
	}

	public final void draw(Graphics g) {
		g.setColor(this.color);
		drawGeometry(g);
	}

	protected abstract void drawGeometry(Graphics g);

}
