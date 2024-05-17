package lab3;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Output extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3907025888960569823L;
	final int WIDTH = 800, HEIGHT = 450;
    final Dimension gameDimension = new Dimension(WIDTH, HEIGHT);
	JLabel timerLabel;
	DefaultTableModel tableModel;
	Timer timer;
	int indexArrayForSJF[] = new int[30];
	int n = 0, xposition = 20, yposition = 290, counter = 0, i = 0, k = 0, l = 0, rectangleAdded = 0;;
	List<Process> processList;
	List<Rectangles> rectangleList = new ArrayList<Rectangles>();
	PriorityQueue<Process> processQueue;
	PriorityQueue<Process> processQueue2;
	int[] waitingTimeList;
	BufferedImage buffer;
	boolean finishedProcess = false, hasProcess = false, firstProcessDone = false;
	MainMenu mainMenu;
	int quantum;
	JTable table, tableFromOtherClass;
	int rowIndex =-1;
	int totalWaitingTime = 0;
	int totalTurnAroundTime = 0;
	JViewport viewPort;
	String hasPrio;
	
	
	Output(JFrame mainFrame, String algo, int numOfprocess, List<Process> processList, int quantum, JTable table, String hasPrio){
		this.quantum = quantum;
		this.tableFromOtherClass = table;
		this.processList = processList;
		this.hasPrio = hasPrio;
		waitingTimeList = new int[processList.size()];
		this.setPreferredSize(gameDimension);
		this.setBackground(new Color(255,255,255));
		this.setVisible(true);
		this.setLayout(null);
		initComponents(this, mainFrame, algo, numOfprocess, processList);
		mainFrame.setContentPane(this);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        checkChosenAlgo(algo,processList, tableModel);
        whatAlgo(algo);
        System.out.println(algo);
        timer = new Timer(1000, (ActionListener) new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				//xposition += 20;
				timerLabel.setText(String.valueOf(counter));
				
				System.out.println("Timer");
				System.out.println("Process List size: " +processList.size());
				
				table.repaint();
				printRec();
				n++; 
				counter++;
				
				repaint();
			}
		});
        timer.start();
        
	}
	
	public void whatAlgo(String algo) {
		switch(algo) {
		case "First Come First Serve"					:	addRectList(); break;
		case "Shortest Job First (Preemptive)"			:   addRecSJFPre(); break;
		case "Shortest Job First (Non-preemptive)"		:	addRecSJF(); break;
		case "Priority (Non-preemptive)"				:	addRecPrio(); break;
		case "Priority (Preemptive)"					:	addRecPrioPreempt(); break;
		case "Round Robin"								:	addRoundRobin(); break;
		default: 
		}
	}
	
	
	/*
	 * FIRST COME FIRST SERVE
	 */
	
	public void printRec() {
		
		System.out.println("RectangelList size: " +rectangleList.size());
		
		if(rectangleAdded >= rectangleList.size()) {
			
			
			for(int j = 0; j < processList.size(); j++) {
				totalTurnAroundTime += processList.get(j).getTurnAroundTime();
				totalWaitingTime += processList.get(j).getWaitingTime();
				
			}
			float averageWaitingTime = (float) totalWaitingTime / (float) processList.size();
			float averageTotalAroundTime = (float) totalTurnAroundTime / (float) processList.size();
			
			for(int k = 0; k < processList.size(); k++) {
				tableModel.setValueAt(String.valueOf(averageWaitingTime), k, 6);
				tableModel.setValueAt(String.valueOf(averageTotalAroundTime), k, 7);
			}
			timer.stop();
		}else {
			
			if(n == rectangleList.get(l).getNth()) {
				
				for(int k = 0; k < processList.size(); k++) {
					if(processList.get(k).getprocessID().equals(rectangleList.get(l).getProcessID())) {
						tableModel.setValueAt(String.valueOf(processList.get(k).getWaitingTime()), k, 4);
						processList.get(k).setTurnAroundTime(processList.get(k).getTurnAroundTime()+1);
						tableModel.setValueAt(String.valueOf(processList.get(k).getTurnAroundTime()), k, 5);
					}
					
				}
				
				
				this.add(rectangleList.get(l).paint());
				if(l < rectangleList.size()) {
					for(int i = 0; i < processList.size(); i++) {
						String processId = processList.get(i).getprocessID();
						if(rectangleList.get(l).getProcessID().equals(processId)){
							rowIndex = i;
							break;
						}
					}
					Point p = viewPort.getViewPosition();
					Rectangle rectRow = table.getCellRect(rowIndex, 0, true);
					rectRow.setLocation(rectRow.x - p.x, rectRow.y - p.y);
					viewPort.scrollRectToVisible(rectRow);
					table.requestFocus();
					
				}
				
				l++;
				++rectangleAdded;
			} 
			
			if(l < rectangleList.size()) {
				if(n > rectangleList.get(l).getNth()) {
					n = rectangleList.get(l).getNth() - 1;
				}
			}
		} 
	}
	 
	
	/*
	 * First come first serve
	 */
	
	public void addRectList() {
		
		for(int l = 0; l<processList.size(); l++) {
			System.out.println(processList.get(l).toString());
		}
		
		Collections.sort(processList, new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				return o1.getArrivalTime() - o2.getArrivalTime();
			}	
		});
		 
		
		int x = 20, y = 195;
		int currentTime = 0;
		boolean hasSetWaitingTime = false;
		
		for(Process p: processList) {
			while(currentTime < p.getArrivalTime()) {
				currentTime++;
			}
			System.out.println("remaining time: " +p.getRemainingTime());
			while(p.getRemainingTime() > 0) {
				
				Rectangles rect = new Rectangles(x, y, 15,15, p.getColor(), p.getprocessID(), currentTime, (p.getTurnAroundTime()+1), p.getWaitingTime());
				p.setTurnAroundTime(p.getTurnAroundTime()+1);
				if(x <= 745) {
					x += 20;
				}else {
					x = 20;
					y += 20;
				}
				rectangleList.add(rect);
				p.setRemainingTime(p.getRemainingTime()-1);
				++currentTime;
			}
			
			p.setFinishedTime(currentTime);
		}
		
	}
	
	/*
	 * SHORTEST JOB FIRST
	 */
	
	public void addRecSJF() {
		
		PriorityQueue<Process> processQueue2 = new PriorityQueue<Process>(new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
					return o1.getBurstTime() - o2.getBurstTime();
			}
		});

		int x = 20, y = 195;
		
		int clock = 0;
		boolean hasSetWaitingTime = false;
		Queue<Process> processQueue = new LinkedList<Process>();
		int i = 0, doneProcess = 0;
		Process runningProcess = null;
		
		while(true) {
			if(doneProcess == processList.size()) {
				break;
			}
			
			while(i < processList.size() && processList.get(i).getArrivalTime() <= clock){
				System.out.println("Clock: " +clock);
				System.out.println(processList.get(i).getprocessID());
				System.out.println();
				processQueue2.add(processList.get(i));
				i++;
			}
			
			runningProcess = processQueue2.poll();
			
			if(runningProcess != null) {
				while(runningProcess.getRemainingTime() > 0) {
					int count = 0;
					if(!hasSetWaitingTime) {
						runningProcess.setWaitingTime(clock);
						hasSetWaitingTime = true;
					}
					Rectangles rect = new Rectangles(x, y, 15,15, runningProcess.getColor(), runningProcess.getprocessID(), clock, (runningProcess.getTurnAroundTime()+1), runningProcess.getWaitingTime());
					runningProcess.setTurnAroundTime(runningProcess.getTurnAroundTime() + 1);
					if(x <= 745) {
						x += 20;
					}else {
						x = 20;
						y += 20;
					}
					rectangleList.add(rect);
					++count;
					runningProcess.setRemainingTime(runningProcess.getRemainingTime()-1);
					++clock;
				}
				++doneProcess;
				runningProcess.setFinishedTime(clock);
				
				if(!processQueue.isEmpty()) {
					runningProcess = processQueue2.poll();
				}else {
					runningProcess = null;
				}
			}else {
				clock++;
			}
			
		}
		/*
		int x = 20, y = 195;
		
		int clock = 0;
		boolean hasSetWaitingTime = false;
		Queue<Process> processQueue = new LinkedList<Process>();
		int i = 0, doneProcess = 0;
		Process runningProcess = null;
		
		while(true) {
			if(doneProcess == processList.size()) {
				break;
			}
			
			if(i < processList.size() && processList.get(i).getArrivalTime() <= clock){
				processQueue.add(processList.get(i));
				i++;
			}
			
			if(runningProcess == null || !processQueue.isEmpty() && processQueue.peek().getRemainingTime() < runningProcess.getRemainingTime()) {
				if(runningProcess != null) {
					processQueue2.add(processQueue.poll());
					//processQueue.add(runningProcess);
				}
				runningProcess = processQueue.poll();
			}
			
			if(runningProcess != null) {
				while(runningProcess.getRemainingTime() > 0) {
					int count = 0;
					if(!hasSetWaitingTime) {
						runningProcess.setWaitingTime(clock);
						hasSetWaitingTime = true;
					}
					Rectangles rect = new Rectangles(x, y, 15,15, runningProcess.getColor(), runningProcess.getprocessID(), clock, (runningProcess.getTurnAroundTime()+1), runningProcess.getWaitingTime());
					runningProcess.setTurnAroundTime(runningProcess.getTurnAroundTime() + 1);
					if(x <= 745) {
						x += 20;
					}else {
						x = 20;
						y += 20;
					}
					rectangleList.add(rect);
					++count;
					runningProcess.setRemainingTime(runningProcess.getRemainingTime()-1);
					++clock;
				}
				++doneProcess;
				runningProcess.setFinishedTime(clock);
				
				if(!processQueue.isEmpty()) {
					runningProcess = processQueue2.poll();
				}else {
					runningProcess = null;
				}
			}else {
				clock++;
			}
			
		}*/
	}
	
	
	/*
	 * Shortest Job First (Preemptive)
	 */
	public void addRecSJFPre() {
		int x = 20, y = 195, indexForWaitList = 0;
		boolean hasSetWaitingTime = false;
		
		PriorityQueue<Process> processQueue = new PriorityQueue<Process>(new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				if(o1.getRemainingTime() == o2.getRemainingTime()) {
					if(o2.getBurstTime() == o1.getBurstTime()) {
						return o1.getArrivalTime() - o2.getArrivalTime();
					}
					return o2.getBurstTime() - o1.getBurstTime();
				}
				return o1.getRemainingTime() - o2.getRemainingTime();
			}
		});
		
		int currentTime = 0;
		List<Process> tempProcessList = new ArrayList<Process>(processList);
		int doneProcess = 0;
		
		while(true) {
			if(doneProcess == processList.size()) {
				break;
			}
			
			for(int i = 0; i < tempProcessList.size(); i++) {
				
				Process p = tempProcessList.get(i);
				if(p.getArrivalTime() <= currentTime) {
					processQueue.add(p);
					tempProcessList.remove(p);
					i--;
				}
			}
			
			
			if(!processQueue.isEmpty()) {
				Process currentProcess = processQueue.poll();
				
				currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);				
				Rectangles rect = new Rectangles(x, y, 15,15, currentProcess.getColor(), currentProcess.getprocessID(), currentTime, (currentProcess.getTurnAroundTime()+1), currentProcess.getWaitingTime());
				currentProcess.setTurnAroundTime((currentProcess.getTurnAroundTime()+1));
				if(x <= 745) {
					x += 20;
				}else {
					x = 20;
					y += 20;
				}
				rectangleList.add(rect); 
				
				if(currentProcess.getRemainingTime() == 0) {
					currentProcess.setFinishedTime(currentTime + 1);
					
					++doneProcess;
				}else {
					processQueue.add(currentProcess);
				}
				
			}
			currentTime++;
			
		}
		
	}
	/*
	 * Priority (Preemptive)
	 */
	public void addRecPrioPreempt() {
		int x = 20, y = 195;
		boolean hasSetWaitingTime = false;
		
		if(hasPrio.equals("Higher Number")) {
			processQueue = new PriorityQueue<Process>(new Comparator<Process>() {
				@Override
				public int compare(Process o1, Process o2) {
					return o2.getPriorityNumber() - o1.getPriorityNumber();
				}
			});
		}
		else if(hasPrio.equals("Lower Number")){
			processQueue = new PriorityQueue<Process>(new Comparator<Process>() {
				@Override
				public int compare(Process o1, Process o2) {
					return o1.getPriorityNumber() - o2.getPriorityNumber();
				}
			});
		}
		
		int currentTime = 0;
		List<Process> tempProcessList = new ArrayList<Process>(processList);
		int doneProcess = 0;
		
		while(true) {
			if(doneProcess == processList.size()) {
				break;
			}
			
			for(int i = 0; i < tempProcessList.size(); i++) {
				
				Process p = tempProcessList.get(i);
				if(p.getArrivalTime() <= currentTime) {
					processQueue.add(p);
					tempProcessList.remove(p);
					i--;
				}
			}
			
			
			if(!processQueue.isEmpty()) {
				Process currentProcess = processQueue.poll();
				
				currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
				
				if(!hasSetWaitingTime) {
					currentProcess.setWaitingTime(currentTime);
					hasSetWaitingTime = true;
				}
				Rectangles rect = new Rectangles(x, y, 15,15, currentProcess.getColor(), currentProcess.getprocessID(), currentTime, (currentProcess.getTurnAroundTime()+1), currentProcess.getWaitingTime());
				currentProcess.setTurnAroundTime((currentProcess.getTurnAroundTime()+1));
				if(x <= 745) {
					x += 20;
				}else {
					x = 20;
					y += 20;
				}
				rectangleList.add(rect);
				
				if(currentProcess.getRemainingTime() == 0) {
					currentProcess.setFinishedTime(currentTime + 1);
					hasSetWaitingTime = false;
					++doneProcess;
				}else {
					processQueue.add(currentProcess);
				}
				
			}
			currentTime++;
			
		}
	}
	
	/*
	 * Priority (Non-Preemptive)
	 */
	public void addRecPrio() {
		
		if(hasPrio.equals("Higher Number")) {
			processQueue2 = new PriorityQueue<Process>(new Comparator<Process>() {
				@Override
				public int compare(Process o1, Process o2) {
						return o2.getPriorityNumber() - o1.getPriorityNumber();
				}
			});
		}
		else if(hasPrio.equals("Lower Number")) {
			processQueue2 = new PriorityQueue<Process>(new Comparator<Process>() {
				@Override
				public int compare(Process o1, Process o2) {
						return o1.getPriorityNumber() - o2.getPriorityNumber();
				}
			});
		}
		
		
		int x = 20, y = 195;
		
		int clock = 0;
		boolean hasSetWaitingTime = false;
		Queue<Process> processQueue = new LinkedList<Process>();
		int i = 0, doneProcess = 0;
		Process runningProcess = null;
		
		while(true) {
			if(doneProcess == processList.size()) {
				break;
			}
			
			while(i < processList.size() && processList.get(i).getArrivalTime() <= clock){
				System.out.println("Clock: " +clock);
				System.out.println(processList.get(i).getprocessID());
				System.out.println();
				processQueue2.add(processList.get(i));
				i++;
			}
			
			runningProcess = processQueue2.poll();
			
			if(runningProcess != null) {
				while(runningProcess.getRemainingTime() > 0) {
					int count = 0;
					if(!hasSetWaitingTime) {
						runningProcess.setWaitingTime(clock);
						hasSetWaitingTime = true;
					}
					Rectangles rect = new Rectangles(x, y, 15,15, runningProcess.getColor(), runningProcess.getprocessID(), clock, (runningProcess.getTurnAroundTime()+1), runningProcess.getWaitingTime());
					runningProcess.setTurnAroundTime(runningProcess.getTurnAroundTime() + 1);
					if(x <= 745) {
						x += 20;
					}else {
						x = 20;
						y += 20;
					}
					rectangleList.add(rect);
					++count;
					runningProcess.setRemainingTime(runningProcess.getRemainingTime()-1);
					++clock;
				}
				++doneProcess;
				runningProcess.setFinishedTime(clock);
				
				if(!processQueue.isEmpty()) {
					runningProcess = processQueue2.poll();
				}else {
					runningProcess = null;
				}
			}else {
				clock++;
			}
			
		}
		
	}
	
	/*
	 * Round Robin
	 */
	public void addRoundRobin() {
		
		PriorityQueue<Process> processQueue = new PriorityQueue<Process>(new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				if(o1.getArrivalTime() == o2.getArrivalTime()) {
					return o1.getBurstTime() - o2.getBurstTime();
				}
				return o1.getArrivalTime() - o2.getArrivalTime();
			}
		});
		Queue<Process> processQueue2 = new LinkedList<Process>();
		
		int x = 20, y = 195;
		int currentTime = 0;
		boolean hasSetWaitingTime = false;
		List<Process> tempProcessList = new ArrayList<Process>(processList);
		int doneProcess = 0;
		Process currentProcess = null;
		
		
		while(doneProcess < processList.size()) {
			System.out.println("Clock: "+currentTime);
			
			System.out.println("Before process: " +processQueue2.peek());
			
			
			
			for(int i = 0; i < tempProcessList.size(); i++) {
				Process p = tempProcessList.get(i);
				System.out.println("Process from loop: " +p.getprocessID());
				if(p.getArrivalTime() <= currentTime) {
					System.out.println("Process: " +p.getprocessID()+ " adding to queue.");
					processQueue2.add(p);
					tempProcessList.remove(p);
					i--;
				}
			}
			
			if(currentProcess != null) {
				if(currentProcess.getRemainingTime()>0) {
					System.out.println("Process: " +currentProcess.getprocessID()+ " sending to queue.");
					processQueue2.add(currentProcess);
				}
			}
			
			for(Process p: processQueue2) {
				System.out.println(p.toString());
			}
			
			if(processQueue2.isEmpty()) {
				currentTime++;
				continue;
			}
			System.out.println("Process from queue: " +processQueue2.peek());
			currentProcess = processQueue2.poll();
			System.out.println("Process from queue after head removal: " +processQueue2.peek());
			int executionTime = Math.min(currentProcess.getRemainingTime(), quantum);
			
			for(int i = 0; i < executionTime; i++) {
				
				currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);

				System.out.println("Process: " +currentProcess.getprocessID()+ " printing.");
				Rectangles rect = new Rectangles(x, y, 15,15, currentProcess.getColor(), currentProcess.getprocessID(), currentTime, (currentProcess.getTurnAroundTime()+1), currentProcess.getWaitingTime());
				currentProcess.setTurnAroundTime(currentProcess.getTurnAroundTime() + 1);
				if(x <= 745) {
					x += 20;
				}else {
					x = 20;
					y += 20;
				}
				rectangleList.add(rect);
				currentTime++;
			}
			if(currentProcess.getRemainingTime() == 0) {
				currentProcess.setFinishedTime(currentTime);
				++doneProcess;
				continue;
			}else {
				
				//processQueue.add(currentProcess);
				continue;
			}
			
		}
		
				
																									
	}	
	
	public void initComponents(JPanel panel, JFrame mainFrame, String algo, int numOfprocess, List<Process> processList) {
		
		table = new JTable();
		JLabel lineAbove = new JLabel();
		JLabel lineBelow = new JLabel();
		System.out.println("InitComponents");
		
		timerLabel = new JLabel();
		timerLabel.setBounds(690, 35, 100, 42);
		timerLabel.setFont(new Font("Californian FB", Font.BOLD, 15));
		panel.add(timerLabel);
		
		
		JLabel algs = new JLabel();
		algs.setText("ALGORITHM:  " + algo);
		algs.setFont(new Font("Californian FB", Font.BOLD, 15));
		algs.setForeground(Color.black);
		algs.setBounds(25,30, 600, 50);
		panel.add(algs);
		
		
		JLabel forTimerImage = new JLabel();
		ImageIcon textIcon = new ImageIcon(this.getClass().getResource("resources/timer.png"));
	    Image textIconCopy = textIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	    ImageIcon temptext = new ImageIcon(textIconCopy);
	    forTimerImage.setIcon(temptext);
	    forTimerImage.setBounds(580, 30, 42, 42);
	    panel.add(forTimerImage);
		
	    JLabel timer = new JLabel();
	    timer.setText("TIMER: ");
	    timer.setFont(new Font("Californian FB", Font.BOLD, 15));
	    timer.setForeground(Color.black);
	    timer.setBounds(620,30, 200, 50);
		panel.add(timer);
	    
		Object[] columnNames = {"<html><center>PROCESS<br>ID", "<html><center>BURST<br>TIME", "<html><center>ARRIVAL<br>TIME", "<html><center>PRIORITY<br>NUMBER", "<html><center>WAITING<br>TIME",
				"<html><center>TURNAROUND<br>TIME", "<html><center>AVERAGE<br>WAITING<br>TIME", "<html><center>AVERAGE<br>TURNAROUND<br>TIME"};
		Object[] column = new Object[8];
		Object[] row = new Object[8];
		
		tableModel = new DefaultTableModel() {
			
			private static final long serialVersionUID = 6288708343485652663L;

			public boolean isCellEditable(int row, int column) {
			       //all cells false
					return false;
			    }
		};
		
		int counter = numOfprocess, count = 1;  
		while(counter > 0) {
			tableModel.addRow(row);
			tableModel.addColumn(column);
			--counter;
			++count;
		}
		
		
		tableModel.setColumnIdentifiers(columnNames);
		table.setOpaque(true);
		table.setModel(tableModel);
		table.setBackground(Color.white);
		table.setForeground(Color.black);
		table.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(51,0,51)), BorderFactory.createEmptyBorder(0,0,0,0)));
		table.setFont(new Font("Californian FB", Font.BOLD, 15));
		table.setRowHeight(30);
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setBackground(Color.yellow);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(7).setCellRenderer( centerRenderer );
		JTableHeader header = table.getTableHeader();
		header.setPreferredSize(new Dimension(180, 80));
		
		JScrollPane spane = new JScrollPane(table);
		spane.setForeground(Color.red);
		spane.setBackground(Color.white);
		spane.setBounds(20,80,750,110);
		panel.add(spane);
		
		viewPort = (JViewport) table.getParent();
		
		JLabel done = new JLabel();
        ImageIcon doneIcon = new ImageIcon(this.getClass().getResource("resources/done.png"));
        Image doneIconCopy = doneIcon.getImage().getScaledInstance(128, 42, Image.SCALE_SMOOTH);
        ImageIcon donetext = new ImageIcon(doneIconCopy);
        done.setIcon(donetext);
        done.setBounds(330, 390, 128, 42);
        done.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        done.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mainMenu = new MainMenu(mainFrame);
        		mainFrame.remove(mainFrame.getContentPane());
        		mainFrame.setContentPane(mainMenu.getPanel());
        		mainFrame.revalidate();
        		mainFrame.repaint();
        		mainFrame.pack();
			}
		});
        panel.add(done);
        
		lineAbove.setSize(300,30);
		lineAbove.setBackground(new Color(0,0,0));
		lineAbove.setOpaque(true);
		lineAbove.setBounds(10,20,780,5);
		panel.add(lineAbove);
		
		lineBelow.setSize(300,30);
		lineBelow.setBackground(new Color(0,0,0));
		lineBelow.setOpaque(true);
		lineBelow.setBounds(10,410,780,5);
		panel.add(lineBelow);
	}
	
	public void checkChosenAlgo(String Chosenalgo, List<Process> processList, DefaultTableModel tableModel) {
		for(int i = 0; i<processList.size(); i++) {
			int j = 0;
			tableModel.setValueAt(processList.get(i).getprocessID(), i, j);
			tableModel.setValueAt(processList.get(i).getBurstTime(), i, ++j);
			tableModel.setValueAt(processList.get(i).getArrivalTime(), i, ++j);
			tableModel.setValueAt(processList.get(i).getPriorityNumber(), i, ++j);
			System.out.println(i);
		}
	}
	
	public void FIFO(List<Process> processList, DefaultTableModel tableModel) {
		
	}
	
	
	public JPanel getPanel() {
		return this;
	}
}


class Rectangles extends JLabel{
	Color color;
	//private static final long serialVersionUID = 4704085924742985302L;
	int xposition, yposition, width, height;
	String processID; 
	int nth, turnAroundTime, waitingTime;
	
	Rectangles(int xposition, int yposition, int width, int height, Color color, String processID, int nth, int turnAroundTime, int waitingTime){
		
		//super(xposition, yposition, width, height);
		this.xposition = xposition;
		this.yposition = yposition;
		this.width = width;
		this.height = height;
		this.color = color;
		this.processID = processID;
		this.nth = nth;
		this.turnAroundTime = turnAroundTime;
		this.waitingTime = waitingTime;
		
	}
	public int getNth() {
		return nth;
	}
	
	public int getTurnAroundTime() {
		return turnAroundTime;
	}
	
	public int getWaitingTime() {
		return waitingTime;
	}
	
	public Color getColor() {
		return color;
	}
	
	public String getProcessID() {
		return processID;
	}
	
	public JLabel paint() {
		this.setOpaque(true);
		this.setText(processID);
		this.setFont(new Font("Arial", Font.PLAIN, 8));
		this.setBackground(color);
		this.setBounds(xposition,yposition, width, height);
		return this;
	}
}

