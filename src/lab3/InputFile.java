package lab3;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class InputFile {
	JFileChooser fileChooser = new JFileChooser();
	File file;
	TableForInput tabInput;
	String input;
	int numOfprocess;
	List<Process> processList = new ArrayList<Process>();
	
	InputFile() throws IOException{
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt files", "txt", "text");
		fileChooser.setFileFilter(filter);
		int response = fileChooser.showOpenDialog(null);
		if(response == JFileChooser.APPROVE_OPTION) {
			file  = new File(fileChooser.getSelectedFile().getAbsolutePath());
			System.out.println(file);
		}
   
		Scanner scanner = new Scanner(file);
		while(scanner.hasNextLine()) {
            input = scanner.nextLine();
            addToList(input);
        }
        scanner.close();  
        
	}
	
	public void addToList(String input) {
		String[] tokens = input.split(" ");
		Process process = new Process(tokens[0], Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]));
		processList.add(process);
        
	}
	
	public List<Process> getProcessList(){
		Collections.sort(processList, new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				if(o1.getArrivalTime() == o2.getArrivalTime()) {
					return o1.getBurstTime() - o2.getBurstTime();
				}
				return o1.getArrivalTime() - o2.getArrivalTime();
			}	
		});
		return processList;
	}
}
