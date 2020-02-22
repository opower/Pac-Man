/**
 * @author Olivia Power
 * 
 * This class creates a frame with a minimum size of 1000 by 1000 and displays the PacmanPanel
 */


import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

public class PacmanFrame{
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setTitle("PACMAN");
		frame.setMinimumSize(new Dimension(1000,1000));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new PacmanPanel());
		frame.pack();
		frame.setVisible(true);
		
		
	}
}