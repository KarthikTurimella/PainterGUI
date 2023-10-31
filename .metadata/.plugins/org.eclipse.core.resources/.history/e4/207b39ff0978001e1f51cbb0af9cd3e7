package K2Prog2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

public class Painter extends JFrame {

	public Painter() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		String name = JOptionPane.showInputDialog("Enter your name");

		JPanel holder = new JPanel();

		TitledBorder title = BorderFactory.createTitledBorder(name);
		holder.setBorder(title);

		holder.setLayout(new BorderLayout());
		setSize(500, 500);
		// Create the paints
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(3, 1)); // 3 by 1

		// add red paint button
		JButton redPaint = new JButton("Red");
		redPaint.setName("RED");
		redPaint.setBackground(Color.RED);
		redPaint.setOpaque(true);
		redPaint.setBorderPainted(false);
		leftPanel.add(redPaint); // Added in next open cell in the grid

		// similar for green and blue
		JButton greenPaint = new JButton();
		greenPaint.setName("GREEN");
		greenPaint.setBackground(Color.GREEN);
		greenPaint.setOpaque(true);
		greenPaint.setBorderPainted(false);
		leftPanel.add(greenPaint);

		JButton bluePaint = new JButton();
		bluePaint.setName("BLUE");

		bluePaint.setBackground(Color.BLUE);
		bluePaint.setOpaque(true);
		bluePaint.setBorderPainted(false);
		leftPanel.add(bluePaint);

		// add the panels to the overall panel, holder
		// note that holder's layout should be set to BorderLayout
		holder.add(leftPanel, BorderLayout.WEST);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 2));

		JButton line = new JButton("Line");
		line.setName("LINE");

		JButton circle = new JButton("Circle");
		circle.setName("CIRCLE");

		// use similar code to add topPanel buttons to the NORTH region
		topPanel.add(line);
		topPanel.add(circle);
		holder.add(topPanel, BorderLayout.NORTH);

//		JPanel bottomPanel = new JPanel();
//		bottomPanel.setLayout(new GridLayout(1, 1));
//		holder.add(bottomPanel, BorderLayout.SOUTH);

		// omit the center panel for now
		// after finishing the PaintingPanel class (described later) add a
		// new object of this class as the CENTER panel

		PaintingPanel centerPanel = new PaintingPanel(name);
		centerPanel.setLayout(new GridLayout(1, 1));
		holder.add(centerPanel, BorderLayout.CENTER);
		redPaint.addActionListener(centerPanel);
		greenPaint.addActionListener(centerPanel);
		bluePaint.addActionListener(centerPanel);
		line.addActionListener(centerPanel);
		circle.addActionListener(centerPanel);
		centerPanel.addMouseListener(centerPanel);

		// And later you will add the chat panel to the SOUTH

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(2, 2));
		bottomPanel.add(centerPanel.enterText); // Added in next open cell in the grid

		JButton postText = new JButton("Post");
		postText.setName("Post");
		postText.setOpaque(true);
		postText.setBorderPainted(false);
		bottomPanel.add(postText); // add button to the panel
		postText.addActionListener(centerPanel);

		centerPanel.postedText = new JTextArea("", 6, 8);
		centerPanel.postedText.setSize(5, 3);
		centerPanel.postedText.setBackground(Color.gray);
		centerPanel.postedText.setLineWrap(true);
		centerPanel.postedText.setWrapStyleWord(true);
		bottomPanel.add(centerPanel.postedText); // Added in next open cell in the grid
		bottomPanel.add(new JScrollPane(centerPanel.postedText));

		// add the panels to the overall panel, holder
		holder.add(bottomPanel, BorderLayout.SOUTH);

		// Lastly, connect the holder to the JFrame
		setContentPane(holder);
		// JFrame f = new JFrame(name);
		// f.setContentPane(holder);

		// And make it visible to layout all the components on the screen
		setVisible(true);

	}

	public static void main(String[] args) {
		Painter thePainter = new Painter();
	}

}
