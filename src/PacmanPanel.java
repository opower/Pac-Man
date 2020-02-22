/**
 * @author Olivia Power
 * 
 * This class recreates a simplified version of the classic game PAC-MAN!!
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class PacmanPanel extends JPanel{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon blinky  = new ImageIcon("blinky.png");
	private ImageIcon pinky = new ImageIcon("pinky.png");
	private ImageIcon blue = new ImageIcon("blue.png");
	private ImageIcon clyde = new ImageIcon("clyde.png");
	private ImageIcon inky = new ImageIcon("inky.png");
	private ImageIcon pellet = new ImageIcon("pellet.png");
	private ImageIcon pacman = new ImageIcon("pacman.png");
	private ImageIcon currentGhost = new ImageIcon("inky.png");
	private Timer timer;
	private Point ghostLocation = new Point(450, 350);
	private Point pacLocation = new Point(0,0);
	private int xDirection = 10, yDirection = 5;
	private int x1 = (int) (Math.random()*850 + 10);
	private int y1 = (int) (Math.random()*850 + 10);
	private Point pelletLocation;
	private boolean drawPellet = true;
	private boolean drawPac = false;
	private static int timeCount = 0;
	private int pelletWidth;
	private int pelletHeight;
	private int ghostWidth;
	private int ghostHeight;

	/**
	 * This constructor sets the panel's size to 1000 by 1000 and the background color to black.
	 * Initializes KeyListeners, MouseListener, MouseMotionListener and TimerListener(by starting timer).
	 */
	public PacmanPanel() {
		
		setPreferredSize(new Dimension(1000,1000));
		setBackground(Color.black);
		
		addKeyListener(new PBICListener());
		setFocusable(true);
		addMouseListener(new PacEnterListener());
		addMouseMotionListener(new PacListener());
		
		timer = new Timer(100, new TimerListener());
		timer.start();
		
	}
	
	/**
	 * This paintComponent super calls and casts Graphics g to Graphics2D g2.
	 * Gets the pellet icon and ghost icon height and width.
	 * Checks to see if boolean draw is equal to true and if so draws the pellet at a random location.
	 * Checks to see if boolean drawPac is equal to true and if so draws pacman at the location entered on screen.
	 * Draws the current ghost by using the paintIcon method. 
	 * @param Graphics g
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		pelletLocation = new Point(x1, y1);
		
		pelletWidth = pellet.getIconWidth();
		pelletHeight = pellet.getIconHeight();
		
		ghostWidth = pacman.getIconWidth();
		ghostHeight = pacman.getIconHeight();
		
		if(drawPellet == true) {
			pellet.paintIcon(this, g2, pelletLocation.x, pelletLocation.y);
		}
		
		if(drawPac == true) {
			//to center the mouse over the pacman icon
			pacman.paintIcon(this, g2, pacLocation.x - 50, pacLocation.y - 50);		
		}
		
		currentGhost.paintIcon(this, g2, ghostLocation.x , ghostLocation.y);
		
	}
	

	private class TimerListener implements ActionListener{
		
		/**
		 * Firstly, this method retrieves the (x,y) coordinates of the ghost and sets the x-axis range to be
		 * (0,900) and the y-axis range to be (0,750); multiples the the x and y values by -1 to animate the 
		 * ghost's movement.
		 * 
		 * Secondly, this method has an intersection check. It checks if the current ghost is blue 
		 * or not, as well gets the current (x,y) coordinates for pacman and the ghost. If the ghost is not 
		 * blue and pacman and the ghost intersect a pop-up message will appear stating the the player has lost
		 * and exits the program. If the ghost is blue, a timeCount will begin and a player has 5 seconds to intersect
		 * with the blue ghost to win the game. If 5 go by and the user does not intersect with the blue ghost 
		 * the pellet will be drawn at a random location and the ghost will change to blinky.
		 * 
		 * Lastly calls the repaint() method to invoke updated conditions.
		 * 
		 * @param e
		 */
		public void actionPerformed(ActionEvent e) {

			if (ghostLocation.getX() >= 900 || ghostLocation.getX() <= 0) {
				xDirection *= -1;
			}
			if (ghostLocation.getY() >= 750 || ghostLocation.getY() <= 0) {
				yDirection *= -1;
			}
			ghostLocation.x+= xDirection;
			ghostLocation.y+= yDirection;
			
			if(currentGhost != blue) {
				if((pacLocation.getX() >= ghostLocation.getX()) && (pacLocation.getX() <= ghostLocation.getX() + ghostWidth)){
					if((pacLocation.getY() >= ghostLocation.getY()) && (pacLocation.getY() <= ghostLocation.getY() + ghostHeight)) {
						timer.stop();
						JOptionPane.showMessageDialog(null, "You Lose!");	
						System.exit(0);
					}
					
				}
				
			}
			
			if(currentGhost == blue) {
				timeCount++;
				if(timeCount <= 50) {
					if((pacLocation.getX() >= ghostLocation.getX()) && (pacLocation.getX() <= ghostLocation.getX() + ghostWidth)){
						if((pacLocation.getY() >= ghostLocation.getY()) && (pacLocation.getY() <= ghostLocation.getY() + ghostHeight)) {
							timer.stop();
							JOptionPane.showMessageDialog(null, "You Win!");	
							System.exit(0);
						}
						
					}
					
				}
				else {
					drawPellet = true;
					currentGhost = blinky;
					timeCount = 0;
					x1 = (int) (1*Math.random()*900 +1);
					y1 = (int) (1*Math.random()*900 + 1);
				}

			}
	
			repaint();
		}		
	}
	

	private class PBICListener extends KeyAdapter{
		
		/**
		 * This method accepts an event parameter and gets the key code the player has pressed 
		 * and changes the ghost's color accordingly. P equals pinky, I equals inky, B equals blinky 
		 * and C = clyde.
		 * 
		 * Lastly calls the repaint() method to implement changes.
		 * 
		 * @param event
		 */
		public void keyPressed(KeyEvent event) {
			switch(event.getKeyCode()) {
			case KeyEvent.VK_P:
				currentGhost = pinky;
				break;
			case KeyEvent.VK_B:
				currentGhost = blinky;
				break;
			case KeyEvent.VK_I:
				currentGhost = inky;
				break;
			case KeyEvent.VK_C:
				currentGhost = clyde;
				break;
			}
			repaint();
		}
	}
	

	private class PacEnterListener extends MouseAdapter{

		/**
		 * This method accepts an event parameter. Once the mouse enters the panel the boolean
		 * value of drawPac is set to true and pac is drawn at the location where the mouse
		 * enters be retrieving the event point.
		 * 
		 * @param event
		 */
		public void mouseEntered(MouseEvent event) {
			drawPac = true;
			pacLocation = event.getPoint();	
			repaint();
		}
	}
	

	private class PacListener extends MouseMotionAdapter{
		
		/**
		 * This method accepts an event parameter. Firstly this method allows the pacman icon to move 
		 * alongside the mouse while it is in motion over the panel. Secondly, retrieves the (x,y) coordinates 
		 * of the pellet location and pacman location to see if they intersect. When intersection occurs the boolean
		 * value drawPellet becomes false, the pellet disappears and the ghost turns blue. 
		 * 
		 * @param event
		 */
		public void mouseMoved(MouseEvent event) {
			pacLocation = event.getPoint();


			if((pacLocation.getX() >= pelletLocation.getX()) && (pacLocation.getX() <= pelletLocation.getX() + pelletWidth)){
				if((pacLocation.getY() >= pelletLocation.getY()) && (pacLocation.getY() <= pelletLocation.getY() + pelletHeight)) {
					drawPellet = false;
					currentGhost = blue;
					repaint();
				}
				
			}
			
		}
		
	}
}