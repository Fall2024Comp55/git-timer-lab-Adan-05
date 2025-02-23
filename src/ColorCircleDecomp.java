import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import acm.graphics.*;
import acm.program.*;

public class ColorCircleDecomp extends GraphicsProgram implements ActionListener {
    public static final int PROGRAM_WIDTH = 800;
    public static final int PROGRAM_HEIGHT = 600;
    public static final int BALL_SIZE = 50;
    public static final int DELAY_MS = 25;
    
    private GOval ball;
    private double angle = 0; //current angle of rotation
    public static final int RADIUS = 200; //adjust this for larger circular rotation 

    private Color colour;
    private int red ;
    private int green;
    private int blue;
    //used to tell how to increment for each colour
    private int redDirection = 1;
    private int greenDirection = 1;
    private int blueDirection = 1;

    public void run() {
    	//initial colour values
    	red = 83;
    	green = 168;
    	blue = 253;
    	
        ball = new GOval(BALL_SIZE, BALL_SIZE);
        ball.setFilled(true);
        ball.setFillColor(colour);
        add(ball);

        //keep ball relatively centered
        ball.setLocation(PROGRAM_WIDTH/2, PROGRAM_HEIGHT - 100);

        Timer t = new Timer(DELAY_MS, this);
        t.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //move ball using movePolar relative to center
        ball.movePolar(10, angle); //adjust the left value for a larger/smaller circle turn

        //increments the angle for circular motion
        angle += 3; //adjust for speed
        angle = angle % 360; //keep within 0-360
        
        //updates the colours and rotates between 0 and 255
        red = (red + redDirection) % 256;
        green = (green + greenDirection) % 256;
        blue = (blue + blueDirection) % 256;

        //used to make sure the colour values stay within the valid range (0 to 255)
        //helps to not get an exception
        red = (red < 0) ? 255 : red;
        green = (green < 0) ? 255 : green;
        blue = (blue < 0) ? 255 : blue;

        //update direction when the color value reaches 0 or 255
        if (red == 255 || red == 0) redDirection *= -1;
        if (green == 255 || green == 0) greenDirection *= -1;
        if (blue == 255 || blue == 0) blueDirection *= -1;

        ball.setFillColor(new Color(red, green, blue));
        
        //print to console
        System.out.println("red: " + red + ", green: " + green + ", blue: " + blue);
    }

    public void init() {
        setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
    }

    public static void main(String args[]) {
        new ColorCircleDecomp().start();
    }
}
