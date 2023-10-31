package K2Prog2;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class msgType implements Serializable {
	int msg; // 0 - text, 1 - drawing

	public msgType(int type) {
		msg = type;
	}

}