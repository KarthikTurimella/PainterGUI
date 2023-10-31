

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Hub {
	static Vector<ObjectOutputStream> paintersList = new Vector<ObjectOutputStream>();
	static Vector<PaintingPrimitive> shapesList = new Vector<PaintingPrimitive>();

	public static void main(String[] args) {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(7007);
			while (true) {
				System.out.println("Hub: Waiting for Painter call");
				Socket hubSocket = ss.accept(); // blocking
				System.out.println("Hub: Painter call accepted");
				PaintHandler ph = new PaintHandler(hubSocket);
				new Thread(ph).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (ss != null) {
					ss.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static class PaintHandler implements Runnable {
		Socket painterSocket = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;

		public PaintHandler(Socket s) {
			painterSocket = s;
			try {
				oos = new ObjectOutputStream(painterSocket.getOutputStream());
				// help the new painter catch up
				msgType t = new msgType(1);
				for (PaintingPrimitive shape : shapesList) {
					System.out.println("PaintHandler: catching up - type:" + t + " and the shape" + shape);
					oos.writeObject(t);
					oos.writeObject(shape);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			paintersList.add(oos);
		}

		@Override
		public void run() {
			System.out.println("PaintHandler: Paint Handler started");
			try {

				ois = new ObjectInputStream(painterSocket.getInputStream());
				PaintingPrimitive x = null;
				String textMessage = null;

				while (true) {
					System.out.println("PaintHandler:waiting to read from socket - " + painterSocket);
					msgType t = (msgType) ois.readObject();
					if (t.msg == 0) {
						textMessage = (String) ois.readObject();
						System.out.println("PaintHandler:read " + textMessage);
					} else {
						x = (PaintingPrimitive) ois.readObject();
						shapesList.add(x);
						System.out.println("PaintHandler:read " + x);
					}
					// Need to send the object to all the other painters
					for (ObjectOutputStream oosIter : paintersList) {
						if (oosIter != oos) {
							oosIter.writeObject(t);
							if (t.msg == 0) {
								oosIter.writeObject(textMessage);
							} else {
								oosIter.writeObject(x);
							}
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				paintersList.remove(oos);
				if (oos != null) {
					try {
						ois.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (ois != null) {
						try {
							ois.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				// e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
