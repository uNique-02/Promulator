package lab3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomInput {
		Random randomizer;
		List<Process> processList = new ArrayList<Process>();
		int min = 3, max = 31, min1 = 1, max2 = 20;
		int numOfprocess;
		int forPrioNum = 0;
		
		RandomInput(){
			randomizer = new Random();
			int randNumber = randomizer.nextInt(max+1 - min) + min;
			numOfprocess = randNumber;
			createList(numOfprocess);
			
		}
		
		public void createList(int numOfprocess) {
			
			for(int i = 0; i < numOfprocess; i++) {
				Set<Integer> usedColors = new HashSet<Integer>();
				while(true) {
					int randomPrio = randomizer.nextInt((max - min1) + min1);
					if(!usedColors.contains(randomPrio)) {
						forPrioNum = randomPrio;
						usedColors.add(forPrioNum);
						break;
					}
				}
					Process process = new Process("P"+(i+1), randomizer.nextInt(max2+1 - min1) + min1, randomizer.nextInt(max2+1 - min1) + min1, forPrioNum);
					processList.add(process);
				} 
			
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
		
		public List<Process> getProcessList(){
			return processList;
		}
}
