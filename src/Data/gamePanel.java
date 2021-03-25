package Data;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class gamePanel extends JPanel implements Runnable{

	static final int width = 1000;
	static final int height = (int)(width*0.555555);
	static final Dimension dim = new Dimension(width,height);
	static final int ballDiameter = 20;
	static final int padH = 100;
	static final int padW = 25;
	Thread gameTh;
	Image image;
	Graphics graphic;
	Random rand;
	Padelle pad1,pad2;
	Ball ball;
	Score score = new Score(width,height);
	
	gamePanel(){
		
		newPaddles();
		newBall();
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(dim);
		
		gameTh = new Thread(this);
		gameTh.start();
	}
	
	public void newBall() {
		rand = new Random();
		ball = new Ball((width/2)-(ballDiameter/2),rand.nextInt(height-ballDiameter),ballDiameter,ballDiameter);
	}
	public void newPaddles() {
		pad1 = new Padelle(0,(height/2)-(padH/2),padW,padH,1);
		pad2 = new Padelle(width-padW,(height/2)-(padH/2),padW,padH,2);
	}
	public void paint(Graphics g) {
		image = createImage(getWidth(),getHeight());
		graphic = image.getGraphics();
		draw(graphic);
		g.drawImage(image,0,0,this);
	}
	public void draw(Graphics g) {
		pad1.draw(g);
		pad2.draw(g);
		ball.draw(g);
		score.draw(g);
		Toolkit.getDefaultToolkit().sync();

	}
	public void move() {
		pad1.move();
		pad2.move();
		ball.move();
	}
	public void checkCollision() {
		
		//bounce ball off top & bottom window edges
		if(ball.y <=0) {
			ball.setYDirection(-ball.yVelocity);
		}
		if(ball.y >= height-ballDiameter) {
			ball.setYDirection(-ball.yVelocity);
		}
		//bounce ball off paddles
		if(ball.intersects(pad1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; //optional for more difficulty
			if(ball.yVelocity>0)
				ball.yVelocity++; //optional for more difficulty
			else
				ball.yVelocity--;
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		if(ball.intersects(pad2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; //optional for more difficulty
			if(ball.yVelocity>0)
				ball.yVelocity++; //optional for more difficulty
			else
				ball.yVelocity--;
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		//stops paddles at window edges
		if(pad1.y<=0)
			pad1.y=0;
		if(pad1.y >= (height-padH))
			pad1.y = height-padH;
		if(pad2.y<=0)
			pad2.y=0;
		if(pad2.y >= (height-padH))
			pad2.y = height-padH;
		//give a player 1 point and creates new paddles & ball
		if(ball.x <=0) {
			score.player2++;
			newPaddles();
			newBall();
			System.out.println("Player 2: "+score.player2);
		}
		if(ball.x >= width-ballDiameter) {
			score.player1++;
			newPaddles();
			newBall();
			System.out.println("Player 1: "+score.player1);
		}
	}
	public void run() {
		//game loop
		long lastTime = System.nanoTime();
		double amountOfTicks =60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now -lastTime)/ns;
			lastTime = now;
			if(delta >=1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}
	}
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			pad1.keyPressed(e);
			pad2.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			pad1.keyReleased(e);
			pad2.keyReleased(e);
		}
	}
}
