package lab3;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Input {
	final int WIDTH = 600, HEIGHT = 450;
    final Dimension gameDimension = new Dimension(WIDTH, HEIGHT);
	JPanel panel;
	UserProcess userPrompt;
	JButton userInputBtn, randomizedBtn, textFileBtn;
	Help help;
    
	Input(JFrame mainFrame){
		panel = new JPanel();
		panel.setPreferredSize(gameDimension);
		panel.setBackground(new Color(255,255,255));
		panel.setVisible(true);
		panel.setLayout(null);
		initComponents(panel, mainFrame);
	}
	
	
	public void initComponents(JPanel panel, JFrame mainFrame) {
		userInputBtn = new JButton();
		randomizedBtn = new JButton();
		textFileBtn = new JButton();
		JLabel lineAbove = new JLabel();
		JLabel lineBelow = new JLabel();
		JLabel forDelay  = new JLabel();
		
		JLabel userInput = new JLabel();
        ImageIcon playIcon = new ImageIcon(this.getClass().getResource("resources/userinputBtn.png"));
        Image playIconCopy = playIcon.getImage().getScaledInstance(128, 42, Image.SCALE_SMOOTH);
        ImageIcon tempPlay = new ImageIcon(playIconCopy);
        userInput.setIcon(tempPlay);
        userInput.setBounds(76, 245, 128, 42);
        userInput.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        userInput.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e) {
        		mainFrame.remove(mainFrame.getContentPane());
        		userPrompt = new UserProcess(mainFrame);
        		mainFrame.setContentPane(userPrompt.getPanel());
        		mainFrame.revalidate();
        		mainFrame.repaint();
        		mainFrame.pack();
        		
        	}
        	@Override
            public void mouseEntered(MouseEvent me) {
        		userInput.setSize(userInput.getWidth()+10, userInput.getHeight()+10);
            }
            
        	@Override 
        	public void mouseExited (MouseEvent e){
        		userInput.setSize(userInput.getWidth()-10, userInput.getHeight()-10);
        	}
        });
        panel.add(userInput);
		
        JLabel random = new JLabel();
        ImageIcon randomIcon = new ImageIcon(this.getClass().getResource("resources/randomizedBtn.png"));
        Image randomIconCopy = randomIcon.getImage().getScaledInstance(128, 42, Image.SCALE_SMOOTH);
        ImageIcon temprandom = new ImageIcon(randomIconCopy);
        random.setIcon(temprandom);
        random.setBounds(238, 245, 128, 42);
        random.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        random.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e) {
        		RandomInput randInput = new RandomInput();
				TableForInput tabInput = new TableForInput(mainFrame, randInput.getProcessList());
        		mainFrame.remove(mainFrame.getContentPane());
        		mainFrame.setContentPane(tabInput.getPanel());
        		mainFrame.revalidate();
        		mainFrame.repaint();
        		mainFrame.pack();
        	}
        	@Override
            public void mouseEntered(MouseEvent me) {
        		random.setSize(random.getWidth()+10, random.getHeight()+10);
            }
            
        	@Override 
        	public void mouseExited (MouseEvent e){
        		random.setSize(random.getWidth()-10, random.getHeight()-10);
        	}
        });
        panel.add(random);
        
        JLabel text = new JLabel();
        ImageIcon textIcon = new ImageIcon(this.getClass().getResource("resources/textfileBtn.png"));
        Image textIconCopy = textIcon.getImage().getScaledInstance(128, 42, Image.SCALE_SMOOTH);
        ImageIcon temptext = new ImageIcon(textIconCopy);
        text.setIcon(temptext);
        text.setBounds(400, 245, 128, 42);
        text.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        text.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e) {
        		try {
					InputFile inputfile = new InputFile();
					TableForInput tabInput = new TableForInput(mainFrame, inputfile.getProcessList());
            		mainFrame.remove(mainFrame.getContentPane());
            		mainFrame.setContentPane(tabInput.getPanel());
            		mainFrame.revalidate();
            		mainFrame.repaint();
            		mainFrame.pack();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        	@Override
            public void mouseEntered(MouseEvent me) {
        		text.setSize(text.getWidth()+10, text.getHeight()+10);
            }
            
        	@Override 
        	public void mouseExited (MouseEvent e){
        		text.setSize(text.getWidth()-10, text.getHeight()-10);
        	}
        });
        panel.add(text);
		
		lineAbove.setSize(300,30);
		lineAbove.setBackground(new Color(0,0,0));
		lineAbove.setOpaque(true);
		lineAbove.setBounds(10,30,580,5);
		panel.add(lineAbove);
		
		lineBelow.setSize(300,30);
		lineBelow.setBackground(new Color(0,0,0));
		lineBelow.setOpaque(true);
		lineBelow.setBounds(10,420,580,5);
		panel.add(lineBelow);
		
		forDelay = new JLabel();
		forDelay.setText("INPUT DATA THROUGH");
		forDelay.setFont(new Font("Bebas Neue", Font.BOLD, 40));
		forDelay.setForeground(new Color(0, 0, 0));
		forDelay.setBounds(-150, 150, 900, 90);
		forDelay.setHorizontalAlignment(JTextField.CENTER);
		panel.add(forDelay);
				
		
	}
	
	
	public JPanel getPanel() {
		return panel;
	}
}

