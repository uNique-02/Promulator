package lab3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

class ShowGanttChart{
	JProgressBar[] progressBars;
	List<Process> processList;
	int n = 0, xposition = 10, yposition = 250;
	private Timer timer;
	JLabel timerLabel;
	int counter = 0;
	JPanel panel;
	
	ShowGanttChart(JPanel panel, List<Process> processList){
		this.panel = panel;
		timerLabel = new JLabel();
		timerLabel.setBounds(650, 35, 100, 42);
		timerLabel.setFont(new Font("Californian FB", Font.BOLD, 15));
		
		this.processList = processList;
		
		timer = new Timer(1000, (ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				n++;
				xposition = 10;
				timerLabel.setText(String.valueOf(counter));
				++counter;
				paint(null);
				panel.repaint();
			}
		});
		timer.start();
		panel.add(timerLabel);
		System.out.println("Xposition: " +xposition);
		
	}
	
	public void paint(Graphics g) {
		panel.paint(g);
		for(int i = 0; i < n; i++) {
			g.setColor(processList.get(i).getColor());
			g.fillRect(xposition, yposition, 15, 50);
			g.setColor(Color.white);
			g.drawString(processList.get(i).getprocessID(), xposition, 25);
			g.drawString(Integer.toString(i+1), xposition, 25+35);
			xposition+=20;
			
		}
	}
	
	public void initComponents(JPanel panel, List<Process> processList) {
		progressBars = new JProgressBar[processList.size()];
				
	}
	
}
