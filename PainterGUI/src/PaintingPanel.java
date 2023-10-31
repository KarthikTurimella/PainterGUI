
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PaintingPanel extends JPanel implements ActionListener, MouseListener {

	String stringPainterName;
	Color c = Color.black;
	// 1: Line, 0: Circle
	int shape = 1;
	Point startPos = null;

	JPanel centerPanel = new JPanel();
	static Vector<PaintingPrimitive> primitives = new Vector<PaintingPrimitive>();

	static Socket hubLink = null;
	static ObjectOutputStream oos = null;

	public static JTextArea postedText = null;
	public JTextArea enterText = new JTextArea("<enter post here>");

	public static Graphics myG = null; /// trying (?)

	public PaintingPanel(String inName) {
		stringPainterName = inName;
		centerPanel.setBackground(Color.WHITE);
		connectToHub();
	}

	// Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (PaintingPrimitive obj : primitives) { // I named my ArrayList primitives -- could also use a standard for
													// loop if you wish
			obj.draw(g);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (myG == null)
			myG = getGraphics(); /// trying

		JButton pressedButton = (JButton) e.getSource();
		if (pressedButton.getName() == "LINE") {
			shape = 1;
		} else if (pressedButton.getName() == "CIRCLE") {
			shape = 0;
		} else if (pressedButton.getName() == "RED") {
			c = Color.red;
		} else if (pressedButton.getName() == "BLUE") {
			c = Color.blue;
		} else if (pressedButton.getName() == "GREEN") {
			c = Color.green;
		} else { // posted text in the chat
			String newl = enterText.getText() + "\n" + postedText.getText();
			postedText.setText(newl);
			newl = stringPainterName + ":" + newl;
			msgType msgToSend = new msgType(0);
			enterText.setText("");

			try {
				oos.writeObject(msgToSend);
				oos.writeObject(newl);
				System.out.println("mouseReleased:shape: " + newl + " Sent to the Hub");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		System.out.println("actionPerformed:" + pressedButton.getName() + "     " + c + "   " + shape);

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (myG == null)
			myG = getGraphics(); /// trying
		startPos = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Point endPos = e.getPoint();
		PaintingPrimitive newShapeAdded = null;

		if (shape == 1) {
			Line line = new Line(startPos, endPos, c);
			newShapeAdded = line;
		} else if (shape == 0) {
			Circle circle = new Circle(startPos, endPos, c);
			newShapeAdded = circle;
		}

		newShapeAdded.drawGeometry(getGraphics());
		primitives.add(newShapeAdded);
		System.out.println("mouseReleased:Painter (" + stringPainterName + ") drawn a shape: " + newShapeAdded);
		msgType msgToSend = new msgType(1);

		try {
			oos.writeObject(msgToSend);
			oos.writeObject(newShapeAdded);
			System.out.println("mouseReleased:shape: " + newShapeAdded + " Sent to the Hub");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public void connectToHub() {
		try {
			System.out.println("connectToHub:Painter (" + stringPainterName + ") setting up socket with Hub");
			hubLink = new Socket("localhost", 7007);
			oos = new ObjectOutputStream(hubLink.getOutputStream());
			System.out.println("connectToHub:Connected");
			HubHandler ph = new HubHandler();
			new Thread(ph).start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static class HubHandler implements Runnable {

		public HubHandler() {

		}

		@Override
		public void run() {
			System.out.println("HubHandler: Hub Handler started");
			try {
				ObjectInputStream ois = new ObjectInputStream(hubLink.getInputStream());
				PaintingPrimitive x = null;
				String textMessage = null;

				while (true) {
					System.out.println("HubHandler: waiting to read");
					msgType t = (msgType) ois.readObject();
					System.out.println("HubHandler :read " + t);
					// Draw x on the panel
					if (t.msg == 0) {
						textMessage = (String) ois.readObject();
						postedText.setText(textMessage);
						System.out.println("HubHandler:read " + textMessage);
					} else {
						x = (PaintingPrimitive) ois.readObject();
						primitives.add(x);
						if (myG != null)
							x.drawGeometry(myG); /// trying
						System.out.println("HubHandler:read " + x);
					}

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
////				try {
////					if (ois != null) {
////						ois.close();
////					}
////				}
//				catch (IOException e1) {
//					e1.printStackTrace();
//				}
				// e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			finally {
//				try {
//					if (ois != null) {
//						ois.close();
//					}
//				}
//				catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
		}
	}

}
