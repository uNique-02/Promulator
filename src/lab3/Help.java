package lab3;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Help {
	final int WIDTH = 600, HEIGHT = 450;
    final Dimension gameDimension = new Dimension(WIDTH, HEIGHT);
    JPanel panel;
    JLabel forHelp;
    MainMenu mainMenu;
    
    
	Help(JFrame mainFrame){
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
		JLabel lineHeader = new JLabel();
		JLabel instruction1 = new JLabel();
		JLabel instruction2 = new JLabel();
		JLabel instruction3 = new JLabel();
	
		JLabel exit = new JLabel();
        ImageIcon exitIcon = new ImageIcon(this.getClass().getResource("resources/exit.png"));
        Image exitIconCopy = exitIcon.getImage().getScaledInstance(100, 30, Image.SCALE_SMOOTH);
        ImageIcon tempexit = new ImageIcon(exitIconCopy);
        exit.setIcon(tempexit);
        exit.setBounds(480, 387, 128, 42);
        exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exit.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e) {
        		mainFrame.remove(mainFrame.getContentPane());
        		mainMenu = new MainMenu(mainFrame);
        		mainFrame.setContentPane(mainMenu.getPanel());
        		mainFrame.revalidate();
        		mainFrame.repaint();
        		mainFrame.pack();
        	}
        	@Override
            public void mouseEntered(MouseEvent me) {
        		//exit.setSize(exit.getWidth()+10, exit.getHeight()+10);
            }
            
        	@Override 
        	public void mouseExited (MouseEvent e){
        		//exit.setSize(exit.getWidth()-10, exit.getHeight()-10);
        	}
        });
        panel.add(exit);
		
		instruction1.setText("<html>If you proceed to start, the program will proceed to require the user to"
				+ "<br> choose a mode of input.</html>");
		instruction1.setBounds(30, 105, 580, 40);
		instruction1.setForeground(Color.black);
		instruction1.setFont(new Font("Californian FB", Font.BOLD, 18));
		panel.add(instruction1);
		
		instruction2.setText("<html>1. User input    - User has to fill out the table <br>"
								 + "2. Randomized    - User will be given random data <br>"
								 + "3. Text File	 - The system will ask for the user to locate the .txt file <br>"
								 + " to be read. </html>");
		instruction2.setBounds(40, 162, 580, 100);
		instruction2.setForeground(Color.black);
		instruction2.setFont(new Font("Californian FB", Font.BOLD, 18));
		panel.add(instruction2);
		
		instruction3.setText("<html> After the data is provided, the user must choose which algorithm will be<br>"
				+ "used for simulating the data. The button SIMULATE will try to generate<br>"
				+ "values that are in line with the algorithm chosen by the user.A Gantt Chart <br>"
				+ "will be located above the output table. This will be used to simulate the <br>"
				+ "scheduling process of the data provided.</html>");
		instruction3.setBounds(30, 260, 580, 130);
		instruction3.setForeground(Color.black);
		instruction3.setFont(new Font("Californian FB", Font.BOLD, 18));
		panel.add(instruction3);
		
		
		lineAbove.setSize(300,30);
		lineAbove.setBackground(new Color(0,0,0));
		lineAbove.setOpaque(true);
		lineAbove.setBounds(10,10,580,5);
		panel.add(lineAbove);
		
		lineHeader.setSize(300,30);
		lineHeader.setBackground(Color.yellow);
		lineHeader.setOpaque(true);
		lineHeader.setBounds(10,80,580,5);
		panel.add(lineHeader);
		
		lineBelow.setSize(300,30);
		lineBelow.setBackground(new Color(0,0,0));
		lineBelow.setOpaque(true);
		lineBelow.setBounds(10,430,580,5);
		panel.add(lineBelow);
		
		forHelp = new JLabel();
		forHelp.setText("HELP");
		forHelp.setFont(new Font("Californian FB", Font.BOLD, 40));
		forHelp.setForeground(new Color(0, 0, 0));
		forHelp.setBounds(-170, 30, 900, 50);
		forHelp.setHorizontalAlignment(JTextField.CENTER);
		panel.add(forHelp);
		
		
	}
	
	public JPanel getPanel() {
		return panel;
	}
}
