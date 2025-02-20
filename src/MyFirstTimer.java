import java.awt.event.*;
import javax.swing.*;
import acm.graphics.*;
import acm.program.*;

public class MyFirstTimer extends GraphicsProgram implements ActionListener {
	public static final int PROGRAM_HEIGHT = 600;
	public static final int PROGRAM_WIDTH = 800;
	public static final int MAX_STEPS = 20;
	private GLabel myLabel;
	private Timer t;      //initialize in run() instead if you want to use it to stop at a certain time
	private int numTimes = 0; //tracks how many times actionPreformed has been called
	
	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		requestFocus();
	}
	
	public void run() {
		myLabel = new GLabel("# of times called?", 0, 100);		
		add(myLabel);
		
		t = new Timer(1000, this);		//Create timer object
		t.setInitialDelay(3000);	//set a delay of 3 seconds (Note: 3s = 3000 milliseconds)
		t.start();
	}
	
	public void actionPerformed(ActionEvent e) {		
	numTimes++;	//increment to track how many times this is being called
	myLabel.move(5,  0);	
	myLabel.setLabel("times called? " + numTimes);	//remember to update the label
	//stop at 10 secs
		if (numTimes == 10) {
			t.stop();
		}
	}
	
	public static void main(String[] args) {
		new MyFirstTimer().start();
	}
}