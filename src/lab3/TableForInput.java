package lab3;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TableForInput {
	
	final int WIDTH = 600, HEIGHT = 450;
    final Dimension gameDimension = new Dimension(WIDTH, HEIGHT);
	JPanel panel;
	static Main main;
	String chosenAlgo;
	Output output;
	List<Process> processList;
	boolean comboBoxIsTouched = false;
	JTable table;
	String hasPrio = "";
	
	
	TableForInput(JFrame mainFrame,int numOfprocess){
		panel = new JPanel();
		panel.setPreferredSize(gameDimension);
		panel.setBackground(new Color(255,255,255));
		panel.setVisible(true);
		panel.setLayout(null);
		initComponents(panel, mainFrame, numOfprocess);
		mainFrame.setContentPane(panel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
	}
	TableForInput(JFrame mainFrame, List<Process> processList){
		this.processList = processList;
		panel = new JPanel();
		panel.setPreferredSize(gameDimension);
		panel.setBackground(new Color(255,255,255));
		panel.setVisible(true);
		panel.setLayout(null);
		initComponents(panel, mainFrame, processList.size());
		mainFrame.setContentPane(panel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
	}
	
	public void initComponents(JPanel panel, JFrame mainFrame, int numOfprocess) {
		table = new JTable();
		JLabel lineAbove = new JLabel();
		JLabel lineBelow = new JLabel();
		JLabel algs = new JLabel();
		JLabel header = new JLabel();
		JLabel reminder = new JLabel();
		
		header.setText("FILL IT UP!");
		header.setFont(new Font("Californian FB", Font.BOLD, 20));
		header.setForeground(Color.black);
		header.setBounds(30, 55, 200, 50);
		panel.add(header);
		
		reminder.setText("<html>Reminder: <br>"
				+ "Burst time: 1 - 30 <br>"
				+ "Arrival time: 1 - 30 <br>"
				+ "Priority number: 1 - 20 </html>");
		reminder.setFont(new Font("Californian FB", Font.BOLD, 12));
		reminder.setForeground(Color.black);
		reminder.setBounds(40, 200, 150,100);
		panel.add(reminder);
		
		algs.setText("ALGORITHMS");
		algs.setFont(new Font("Californian FB", Font.BOLD, 15));
		algs.setForeground(Color.black);
		algs.setBounds(35,300, 100, 50);
		panel.add(algs);
		
		Object[] columnNames = {"PROCESS ID", "BURST TIME", "ARRIVAL TIME", "PRIORITY NUMBER"};		
		//Object[][] row = new Object[20][4];
		Object[] column = new Object[4];
		Object[] row = new Object[4];
		DefaultTableModel tableModel = new DefaultTableModel() {
			
			public boolean isCellEditable(int row, int column) {
			       //all cells true
					if(column == 0) {
						return false;
					}
			       return true;
			    }
		};
		
		for(int i = 0; i < 4; i++) {
			tableModel.addColumn(column);
		}
		
		if(processList == null) {
			processList = new ArrayList<Process>();
			int counter = numOfprocess, count = 1;  
			while(counter > 0) {
				Process process = new Process("P"+count, 0,0,0);
				processList.add(process);
				tableModel.addRow(row);
				tableModel.setValueAt(process.getprocessID(), count-1, 0);
				--counter;
				++count;
			}
		}else {			
			System.out.println("ProcessList size " +processList.size());
			int counter = 0;
			while(counter < processList.size()) {
				tableModel.addRow(row);
				++counter;
			}
			for(int i = 0; i < processList.size(); i++) {
				tableModel.setValueAt(processList.get(i).getprocessID(), i, 0);
				tableModel.setValueAt(processList.get(i).getBurstTime(), i, 1);
				tableModel.setValueAt(processList.get(i).getArrivalTime(), i, 2);
				tableModel.setValueAt(processList.get(i).getPriorityNumber(), i, 3);
			}
		}
		
		tableModel.setColumnIdentifiers(columnNames);
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
		//table.getColumnModel().getColumn(0).setCellEditor(new IntegerCellEditor(new JTextField()));
		table.getModel().addTableModelListener(new TableModelListener() {
			
			boolean checkIfExists(int data) {
				boolean found = false;
				for(int i = 0; i < processList.size(); i++) {
					if(processList.get(i).getPriorityNumber() == data) {
						found = true;
						break;
					}
				}
				return found;
			}
			
			public int validate(int columnIndex, int data, int rowIndex) {
				if(((columnIndex == 1 || columnIndex == 2) && (data < 1 || data > 30)) || (columnIndex == 3 && (data < 1 || data > 20))) {
					JOptionPane.showMessageDialog(panel, "Enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
					data = 1;
					table.getModel().setValueAt(String.valueOf(data), rowIndex, columnIndex);
				}
				if(columnIndex == 3) {
					if(checkIfExists(data)) {
						int temp = Integer.parseInt(String.valueOf(table.getModel().getValueAt(rowIndex-1, columnIndex)));
						while(checkIfExists(temp)) {
							temp++;
						}
						data = temp;
						
						table.getModel().setValueAt(String.valueOf(data), rowIndex, columnIndex);
					}
				}
					return data;
			}
			
			
			@Override
			public void tableChanged(TableModelEvent e) {
				String ModifiedData;
				
				int rowIndex 	= table.getSelectedRow();
				int columnIndex = table.getSelectedColumn();
				ModifiedData = (String) table.getValueAt(rowIndex, columnIndex);
				
				int data;
				
				
				try {
					data = Integer.parseInt(ModifiedData);
				}catch(Exception evt) {
					data = 0;
				}
				
				switch(columnIndex) {
				case 1:		data = validate(1, data, rowIndex);
							processList.get(rowIndex).setBurstTime(data);
							processList.get(rowIndex).setRemainingTime(data);
							break;
				case 2:		data = validate(2, data, rowIndex);
							processList.get(rowIndex).setArrivalTime(data);
							break;
				case 3:		data = validate(3, data, rowIndex);
							processList.get(rowIndex).setPriorityNumber(data);
							break;
				default:
				}
				
				/*switch(columnIndex) { 
				case 1:		processList.get(rowIndex).setBurstTime(data); break;
				case 2:		processList.get(rowIndex).setArrivalTime(data); break;
				case 3:		processList.get(rowIndex).setPriorityNumber(data); break;
				default:	
				} */
			}
			
		});
		
		
		JScrollPane spane = new JScrollPane(table);
		spane.setForeground(Color.red);
		spane.setBackground(Color.white);
		spane.setBounds(30,100,560,115);
		panel.add(spane);
		
		

		String[] algorithms = {"First Come First Serve", "Round Robin", "Shortest Job First (Preemptive)"
				,"Shortest Job First (Non-preemptive)", "Priority (Preemptive)", "Priority (Non-preemptive)"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox algoBox = new JComboBox(algorithms);
		algoBox.setBounds(30,340,250,30);
		algoBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == algoBox) {
					comboBoxIsTouched = true;
					chosenAlgo = (String) algoBox.getSelectedItem();
				} 
			}
			
		});
		panel.add(algoBox);
		
		
		JLabel simulate = new JLabel();
        ImageIcon simulateIcon = new ImageIcon(this.getClass().getResource("resources/simulate.png"));
        Image simulateIconCopy = simulateIcon.getImage().getScaledInstance(128, 42, Image.SCALE_SMOOTH);
        ImageIcon simulatetext = new ImageIcon(simulateIconCopy);
        simulate.setIcon(simulatetext);
        simulate.setBounds(420, 332, 128, 42);
        simulate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        simulate.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Arrange(processList);
				
				int quantum = 0;
				if(!comboBoxIsTouched) {
					chosenAlgo = "First Come First Serve";
				}
				if(chosenAlgo.equals("Round Robin")) {
					do {
						try {
							quantum = Integer.parseInt(JOptionPane.showInputDialog(panel, "Enter time quantum (1 - 10): "));
							if(quantum < 1 || quantum > 10) {
								JOptionPane.showMessageDialog(panel, "Time quantum must be 1 - 10", "Error", JOptionPane.WARNING_MESSAGE);
								continue;
							}else {
								break;
							}
						}catch(NumberFormatException e1) {
							JOptionPane.showMessageDialog(panel, "Time quantum must be 1 - 10", "Error", JOptionPane.WARNING_MESSAGE);
						}
					}while(true);
				}
				if(chosenAlgo.equals("Priority (Preemptive)") || chosenAlgo.equals("Priority (Non-preemptive)")) {
					Object[] options = {"Higher Number", "Lower Number"};
					int result = JOptionPane.showOptionDialog(null, "Which number has the higher priority?", "Priority", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if(result == JOptionPane.YES_OPTION) {
						hasPrio = "Higher Number";
						System.out.println("Higher number");
					}else if(result == JOptionPane.NO_OPTION){
						hasPrio = "Lower Number";
						System.out.println("Lower number");
					}
					
				}
				mainFrame.remove(mainFrame.getContentPane());
        		output = new Output(mainFrame, chosenAlgo, numOfprocess, processList, quantum, table, hasPrio);
        		mainFrame.setContentPane(output.getPanel());
        		mainFrame.revalidate();
        		mainFrame.repaint();
        		mainFrame.pack();
			}
		});
        panel.add(simulate);
		
		lineAbove.setSize(300,30);
		lineAbove.setBackground(new Color(0,0,0));
		lineAbove.setOpaque(true);
		lineAbove.setBounds(10,50,580,5);
		panel.add(lineAbove);
		
		lineBelow.setSize(300,30);
		lineBelow.setBackground(new Color(0,0,0));
		lineBelow.setOpaque(true);
		lineBelow.setBounds(10,400,580,5);
		panel.add(lineBelow);
		 
	}
	
	
	public void Arrange(List<Process> processList) {
		Collections.sort(processList, new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				if(o1.getArrivalTime() == o2.getArrivalTime()) {
					return o1.getBurstTime() - o2.getBurstTime();
				}
				return o1.getArrivalTime() - o2.getArrivalTime();
			}	
		});
	}
		
	public JPanel getPanel() {
		return panel;
	}
}

class Process{
	
	
	String processID;
	int burstTime;
	int arrivalTime;
	int priorityNumber;
	int origBurstTime;
	int remainingTime;
	int waitingTime;
	int turnAroundTime;
	int timeOfInterruption;
	int startTime;
	int finishedTime;
	Color color;
	boolean lockStart = false, isDone = false;
	
	
	Process(String processID, int burstTime, int arrivalTime, int priorityNumber){
		this.processID = processID;
		this.burstTime = burstTime;
		System.out.println("Burst Time in constructor: " +burstTime);
		this.arrivalTime = arrivalTime;
		this.priorityNumber = priorityNumber;
		this.remainingTime = burstTime;
		waitingTime = 0;
		turnAroundTime = 0;
		timeOfInterruption = 0;
		startTime = 0;
		finishedTime = 0;
		getRandomColor();
	}
	
	public void getRandomColor() {
		Random rand = new Random();
		Set<Color> usedColors = new HashSet<Color>();
		
		while(true) {
			float luminanceThreashold = 0.4f;
			float luminance = 0;
			Color randColor;
			do {
				int red = rand.nextInt(256);
				int green = rand.nextInt(256);
				int blue = rand.nextInt(256);
				luminance = (0.2126f *red + 0.7152f*green + 0.0722f*blue) / 255.00f;
				randColor = new Color(red, green, blue);
			}while(luminance < luminanceThreashold);
							
			if(!usedColors.contains(randColor)) {
				color = randColor;
				usedColors.add(color);
				break;
			}
			
		}
		
	}
	public Color getColor() {
		return this.color;
	}
	
	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}
	public int getWaitingTime() {
		return waitingTime;
	}
	
	public int getFinishedTime() {
		return finishedTime;
	}
	public void setFinishedTime(int time) {
		finishedTime = time;
		System.out.println("Burst Time: " +burstTime);
		System.out.println("Arrival Time: " +arrivalTime);
		waitingTime = finishedTime - (burstTime + arrivalTime);
		turnAroundTime = waitingTime;
		
	}
	
	public void setTurnAroundTime(int turnAroundTime) {
		this.turnAroundTime = turnAroundTime;
	}
	public int getTurnAroundTime() {
		return turnAroundTime;
	}
	
	public String getprocessID() {
		return processID;
	}
	
	public int getBurstTime() {
		return burstTime;
	}
	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}
	
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	public int getRemainingTime() {
		return remainingTime;
	}
	public void setRemainingTime(int remainTime) {
		this.remainingTime = remainTime;
	}
	
	public void setIsDone(boolean isDone) {
		this.isDone = isDone;
	}
	public boolean IsDone() {
		return isDone;
	}
	
	public int getPriorityNumber() {
		return priorityNumber;
	}
	public void setPriorityNumber(int priorityNumber) {
		this.priorityNumber = priorityNumber;
	}
	
	public void setTimeOfInterruption(int time) {
		timeOfInterruption = time;
	}
	public int getTimeOfInterruption() {
		return timeOfInterruption;
	}
	
	public void setStartTime(int time) {
		if(!lockStart) {
			startTime = time;
			lockStart = true;
		}
		
	}
	public int getStartTime() {
		return startTime;
	}
	
	public int getRectangleArrival() { 
		int arrivalRec =arrivalTime + ( origBurstTime - burstTime ); 
		return arrivalRec;
	}
	public String toString() {
		return processID + " " + burstTime + " " +arrivalTime+ " " + priorityNumber;
	}

	
}

class IntegerCellEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 5780475387600733077L;
	private static final Border red = new LineBorder(Color.red);
    private static final Border black = new LineBorder(Color.black);
    private JTextField textField;

    public IntegerCellEditor(JTextField textField) {
        super(textField);
        this.textField = textField;
        this.textField.setHorizontalAlignment(JTextField.RIGHT);
    }

    @Override
    public boolean stopCellEditing() {
        try {
            int v = Integer.valueOf(textField.getText());
            if (v < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            textField.setBorder(red);
            return false;
        }
        return super.stopCellEditing();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table,
        Object value, boolean isSelected, int row, int column) {
        textField.setBorder(black);
        return super.getTableCellEditorComponent(
            table, value, isSelected, row, column);
    }
}
