package lab3;

import java.awt.Dimension;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

public class Main {

	final int WIDTH = 600, HEIGHT = 450;
    final Dimension gameDimension = new Dimension(WIDTH, HEIGHT);
    JFrame mainFrame;
    MainMenu mainMenu;
    Help help;
    Clip clip;
    
    public Main() {
    	mainFrame = new JFrame();
    	mainMenu = new MainMenu(mainFrame);
        mainFrame.setTitle("PROmulator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setVisible(true); 
        mainFrame.setContentPane(mainMenu.getPanel());
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        
        try{
            AudioInputStream stream = AudioSystem.getAudioInputStream(this.getClass().getResource("resources/BGMusic.wav"));
            clip = AudioSystem.getClip();
            clip.open(stream);
            //clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            //Thread.sleep(10000); // looping as long as this thread is alive
            }catch(Exception evt){
                System.out.println(evt);
            }
    }  
	
    public JFrame getFrame() {
    	return mainFrame;
    }
}
