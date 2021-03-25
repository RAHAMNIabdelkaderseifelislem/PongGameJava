package Data;

import java.awt.*;

import javax.swing.*;

public class frame extends JFrame {

	
	
	frame(){
		
		gamePanel panel = new gamePanel();
		
		this.add(panel);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("Pong Game");
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setBounds(0, 0, 1000, 0);
		this.pack();
		
		
		
		
		
	}
}
