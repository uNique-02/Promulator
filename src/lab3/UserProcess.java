package lab3;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class UserProcess {
	
	final int WIDTH = 600, HEIGHT = 450;
    final Dimension gameDimension = new Dimension(WIDTH, HEIGHT);
	JPanel panel;
	JTextField numOfProcess;
	TableForInput tabInput;
	int processCount;
	
	UserProcess(JFrame mainFrame){
		panel = new JPanel();
		panel.setPreferredSize(gameDimension);
		panel.setBackground(new Color(255,255,255));
		panel.setVisible(true);
		panel.setLayout(null);
		initComponents(panel, mainFrame);
	}
	
	public void initComponents(JPanel panel, JFrame mainFrame) {
		
		JLabel lineAbove = new JLabel();
		JLabel lineBelow = new JLabel();
		JLabel forText	 = new JLabel();
		JLabel forText1	 = new JLabel();
		JLabel forText2  = new JLabel();
		
		
		forText = new JLabel();
		forText.setText("ENTER NUMBER OF");
		forText.setFont(new Font("Arial Black", Font.BOLD, 30));
		forText.setForeground(new Color(0, 0, 0));
		forText.setBounds(-149, 100, 900, 90);
		forText.setHorizontalAlignment(JTextField.CENTER);
		panel.add(forText);
		
		
		JLabel proceed = new JLabel();
        ImageIcon proceedIcon = new ImageIcon(this.getClass().getResource("resources/proceed.png"));
        Image proceedIconCopy = proceedIcon.getImage().getScaledInstance(128, 42, Image.SCALE_SMOOTH);
        ImageIcon proceedtext = new ImageIcon(proceedIconCopy);
        proceed.setIcon(proceedtext);
        proceed.setBounds(237, 370, 128, 42);
        proceed.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        proceed.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e) {
        		String userInput;
        		userInput = numOfProcess.getText();
        		if(!userInput.equals("")) {
        			int numProcess = Integer.parseInt(userInput);
        		 	if(numProcess < 3 || numProcess > 20) {
        				JOptionPane.showMessageDialog(panel, "Number of process must be 3 - 20", "Eror", JOptionPane.WARNING_MESSAGE);
        			} else {
        				processCount = numProcess;
        				tabInput = new TableForInput(mainFrame, processCount);
                		mainFrame.remove(mainFrame.getContentPane());
                		mainFrame.setContentPane(tabInput.getPanel());
                		mainFrame.revalidate();
                		mainFrame.repaint();
                		mainFrame.pack();
        				System.out.println(processCount);
        				
        			}
        		}else {
        			JOptionPane.showMessageDialog(panel, "Number of process must be 3 - 20", "Error", JOptionPane.WARNING_MESSAGE);
        		}
        	}
        	@Override
            public void mouseEntered(MouseEvent me) {
        		//proceed.setSize(proceed.getWidth()+10, proceed.getHeight()+10);
            }
            
        	@Override 
        	public void mouseExited (MouseEvent e){
        		//proceed.setSize(proceed.getWidth()-10, proceed.getHeight()-10);
        	}
        });
        panel.add(proceed);
        
        
		forText1 = new JLabel();
		forText1.setText("PROCESSES");
		forText1.setFont(new Font("Arial Black", Font.BOLD, 30));
		forText1.setForeground(new Color(0, 0, 0));
		forText1.setBounds(-152, 130, 900, 90);
		forText1.setHorizontalAlignment(JTextField.CENTER);
		panel.add(forText1);
		
		numOfProcess = new JTextField("");
		numOfProcess.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0,0,0)), BorderFactory.createEmptyBorder(0,0,0,0)));
		numOfProcess.setBackground(new Color(255, 255, 255));
		numOfProcess.setFont(new Font("Californian FB", 1, 18)); 
		numOfProcess.setForeground(new Color(0, 0, 0));
		numOfProcess.setHorizontalAlignment(JTextField.CENTER);
		numOfProcess.setBounds(225, 200, 150, 40);
		numOfProcess.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				numOfProcess.setText("");
			}
		});	
		numOfProcess.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				char input = e.getKeyChar();
				
				if(!Character.isDigit(input)) {
		 			e.consume();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
			}
		});
		panel.add(numOfProcess);
		
		forText2 = new JLabel();
		forText2.setText("Number of processes must be 3 - 20");
		forText2.setFont(new Font("Arial", Font.BOLD, 12));
		forText2.setForeground(new Color(0, 0, 0));
		forText2.setBounds(-152, 210, 900, 90);
		forText2.setHorizontalAlignment(JTextField.CENTER);
		panel.add(forText2);
		
		
		
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
		
		
	}
	
	public JPanel getPanel() {
		return panel;
	}
}
