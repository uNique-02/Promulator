package lab3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class SplashScreen extends JFrame{
	
	private static final long serialVersionUID = -8625680036134734100L;
	final static int WIDTH = 480, HEIGHT = 350;
	final static Dimension gameDimension = new Dimension(WIDTH, HEIGHT);
	static JPanel splashPanel;
	
	SplashScreen(){
		
		splashPanel = new JPanel();
		splashPanel.setFocusable(true);
		splashPanel.setPreferredSize(gameDimension); 
		splashPanel.setLayout(new BorderLayout());
		
		ImageIcon splashBackGround = new ImageIcon(this.getClass().getResource("resources/Promulator Splash.gif"));
		Image splashBG = splashBackGround.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
		ImageIcon splashBGgif = new ImageIcon(splashBG);
		JLabel screenImage = new JLabel(splashBGgif);
		splashPanel.add(screenImage, BorderLayout.CENTER); 
		
		
		this.setContentPane(splashPanel);
		this.setTitle("Time Slice");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true); 
		 
		int i = 0;
		try { 
			while(i <= 40) {
				Thread.sleep(50);
				i++;
			}  
		}	catch(Exception e) {
			e.printStackTrace();
		}
		
		new Main();
		this.dispose();
	}
}
