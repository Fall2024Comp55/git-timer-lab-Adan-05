import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class BallLauncher extends GraphicsProgram{
	public static final int PROGRAM_HEIGHT = 600;
	public static final int PROGRAM_WIDTH = 800;
	public static final int SIZE = 25;
	
	private ArrayList<GOval> ovals; //store our BALLS
	public static final int MS = 50; //stores how often we want the timer to be woken up
	public static final int SPEED = 2; //stores how much to move each ball
	
	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		requestFocus();
	}
	
	public void run() {
		ovals = new ArrayList<>(); //initialize the list of BALLS
		addMouseListeners();
		
		Timer t = new Timer(MS, this);
		t.start();
	}
	
	public void mousePressed(MouseEvent e) {
        //check if any BALLS are still near the starting position (x < 100)
        for (GOval ball : ovals) {
            if (ball.getX() < 100) {
                return; //if a BALL was recently launched, this just ignore the mouse press
            }
        }
		
		GOval ball = makeBall(SIZE/2, e.getY());
		add(ball);
		ovals.add(ball); //adds BALLS to ovals list
	}
	//if no BALLS are near the start, continue like normal
	public GOval makeBall(double x, double y) {
		GOval temp = new GOval(x-SIZE/2, y-SIZE/2, SIZE, SIZE);
		temp.setColor(Color.RED);
		temp.setFilled(true);
		return temp;
	}
	
    @Override 
    public void actionPerformed(ActionEvent e) {
        //iterate using the enhanced for-loop and move each BALL
        for (GOval ball : ovals) {
            ball.move(SPEED, 0); //BALLS move in the X direction
        }
    }
	public static void main(String[] args) {
		new BallLauncher().start();
	}

}
