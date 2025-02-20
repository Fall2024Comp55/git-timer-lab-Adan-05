import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class DodgeBall extends GraphicsProgram implements ActionListener {
	private ArrayList<GOval> BALLS;
	private ArrayList<GRect> enemies;
	private GLabel text;
	private GLabel removedEnemies;
	private GLabel gameOverText1;
	private GLabel gameOverText2;
	private Timer movement;
	private RandomGenerator rgen;
	
	public static final int SIZE = 25;
	public static final int SPEED = 2;
	public static final int MS = 50;
	public static final int MAX_ENEMIES = 10;
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 300;
	
	private int numTimes  = -1;//track number of time actionsPerformed is called
	private int enemiesRemoved = 0; // track the number of enemies removed
	
	public void run() {
		rgen = RandomGenerator.getInstance();
		BALLS = new ArrayList<GOval>();
		enemies = new ArrayList<GRect>();
		
		//label for tracking enemies remaining
		text = new GLabel("Enemies: " + enemies.size(), 10, WINDOW_HEIGHT);
		add(text);
		
		removedEnemies = new GLabel("Enemies Removed: " + enemiesRemoved, 10, 40);
		add(removedEnemies);
		
		movement = new Timer(MS, this);
		movement.start();
		addMouseListeners();
	}
	
	public void actionPerformed(ActionEvent e) {
		numTimes++; //increment to track number of times called
		//an if statement that says for every 40th run, add an enemy
		if(numTimes % 40 == 0) {
		    addAnEnemy();
		}
		moveAllBALLSOnce();
		moveAllEnemiesOnce(); //move the enemies
		
        text.setLabel("Enemies: " + enemies.size()); //update label

        // Check if enemies exceed limit
        if (enemies.size() > MAX_ENEMIES) {
            gameOver();
        }
	}
	
	public void mousePressed(MouseEvent e) {
		for(GOval b:BALLS) {
			if(b.getX() < SIZE * 2.5) {
				return;
			}
		}
		addABall(e.getY());     
	}
	
	private void addABall(double y) {
		GOval ball = makeBall(SIZE/2, y);
		add(ball);
		BALLS.add(ball);
	}
	
	public GOval makeBall(double x, double y) {
		GOval temp = new GOval(x-SIZE/2, y-SIZE/2, SIZE, SIZE);
		temp.setColor(Color.RED);
		temp.setFilled(true);
		return temp;
	}
	
	private void addAnEnemy() {
		GRect e = makeEnemy(rgen.nextInt(0, WINDOW_HEIGHT-SIZE/2));
		enemies.add(e);
		text.setLabel("" + enemies.size());
		add(e);
	}
	
	public GRect makeEnemy(double y) {
		GRect temp = new GRect(WINDOW_WIDTH-SIZE, y-SIZE/2, SIZE, SIZE);
		temp.setColor(Color.GREEN);
		temp.setFilled(true);
		return temp;
	}

	private void moveAllBALLSOnce() {
		for(GOval ball:BALLS) {
			ball.move(SPEED, 0);
			
			//use the measurements to calculate the point just outside the ball on the right side
	        double checkX = ball.getX() + ball.getWidth() + 1;
	        double checkY = ball.getY() + ball.getHeight() / 2;

	        //check if an object exists at that point
	        GObject poorLilGuy = getElementAt(checkX, checkY);

	        //if the object is a GRect (enemy), remove it
	        if (poorLilGuy instanceof GRect) {
	        	remove(poorLilGuy); //removes from screen
	            enemies.remove(poorLilGuy); //removes from enemies list
	            enemiesRemoved++; //update enemies removed
	            removedEnemies.setLabel("Enemies Removed: " + enemiesRemoved); //update the label
	        }
		}
	}
	
	//similarly to moveAllBALLSOnce, this moves the enemy squares
	private void moveAllEnemiesOnce() {
		for(GRect box : enemies) {
			box.move(0, rgen.nextInt(-2, 2));
		}
	}
	
	//a function that ends the game
	private void gameOver() {
		movement.stop(); //stops the timer
		removeAll(); //clears the screen
		
		gameOverText1 = new GLabel("GAME OVER!", WINDOW_WIDTH / 4, WINDOW_HEIGHT / 2);
		gameOverText2 = new GLabel("You murdered " + enemiesRemoved + " squares!", WINDOW_WIDTH / 4, WINDOW_HEIGHT / 2 + 30);
        gameOverText1.setFont("SansSerif-bold-18");
        gameOverText2.setFont("SansSerif-bold-18");

        add(gameOverText1);
        add(gameOverText2);
	}
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	public static void main(String args[]) {
		new DodgeBall().start();
	}
}
