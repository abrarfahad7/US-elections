import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class US_elections {

	public static int solution(int num_states, int[] delegates, int[] votes_Biden, int[] votes_Trump, int[] votes_Undecided){
		
		int bidenMajorityToWin;
		int trumpMajorityToWin;
		
		int totalDelegates = 0;
		
		List<Integer> undeclaredStates = new ArrayList<Integer>();
		
		for (int i = 0; i < num_states; i++) {
			totalDelegates += delegates[i];
		}
		
		if (totalDelegates%2 == 0) {                       // if even number
			bidenMajorityToWin = (totalDelegates/2)+1;
			trumpMajorityToWin = (totalDelegates/2);
		}
		else {											   // if odd number
			bidenMajorityToWin = (totalDelegates/2)+1;
			trumpMajorityToWin = (totalDelegates/2)+1;
		}
		
		for (int i = 0; i < num_states; i++) {
			
			//biden already won that state
			if ((votes_Biden[i] > votes_Trump[i]) && ((votes_Biden[i] - votes_Trump[i]) > votes_Undecided[i])) {   
				bidenMajorityToWin = bidenMajorityToWin - delegates[i];
			}
			
			//trump already won that state
			else if ((votes_Trump[i] > votes_Biden[i]) && ((votes_Trump[i] - votes_Biden[i]) >= votes_Undecided[i])) {    
				trumpMajorityToWin = trumpMajorityToWin - delegates[i];
			}
			
			//undeclared state
			else {   
				undeclaredStates.add(i);
			}
		}
		
		if (bidenMajorityToWin <= 0) {
			return 0;
		} 
		
		if (trumpMajorityToWin <= 0) {
			return -1;
		}
		
		int values[] = new int[undeclaredStates.size()];
		int weights[] = new int[undeclaredStates.size()];
		
		for (int j = 0; j < undeclaredStates.size(); j++) {
			int i = undeclaredStates.get(j);
			values[j] = ((votes_Undecided[i])/2) + 1;
			weights[j] = delegates[i];
		}
		
		int totalWeight = 0;
		for(int weight: weights){
		    totalWeight += weight;
		}
		
		
		return findMinimumNeeded(bidenMajorityToWin, values, weights, totalWeight);
		
	}
	
	public static int findMinimumNeeded(int bidenMajorityToWin, int[] values, int[] weights, int totalWeight) {
		
		
		
		int[] minCost = new int[totalWeight + 1];
		
		int i = 1;
		
		while(i <= totalWeight){
		    minCost[i] = Integer.MAX_VALUE;
		    i++;
		}
		
		int idx = 0;
		int undecidedStatesCount = weights.length;
		while(idx < undecidedStatesCount){
			
		    for(int j = totalWeight; j >= weights[idx]; j--){
		        if(minCost[j - weights[idx]] != Integer.MAX_VALUE){
		            minCost[j] = Math.min(minCost[j], minCost[j - weights[idx]] + values[idx]);
		        }
		    }
		    idx++;
		}
		int minVoteNeeded = Integer.MAX_VALUE;
		int x = bidenMajorityToWin;
		
		while(x <= totalWeight){
		    minVoteNeeded = Math.min(minVoteNeeded, minCost[x]);
		    x++;
		}
		
		return minVoteNeeded;
	}
	

	public static void main(String[] args) {
		  
		
	 try {
			String path = args[0];
      File myFile = new File(path);
      Scanner sc = new Scanner(myFile);
      int num_states = sc.nextInt();
      int[] delegates = new int[num_states];
      int[] votes_Biden = new int[num_states];
      int[] votes_Trump = new int[num_states];
 			int[] votes_Undecided = new int[num_states];	
      for (int state = 0; state<num_states; state++){
			  delegates[state] =sc.nextInt();
				votes_Biden[state] = sc.nextInt();
				votes_Trump[state] = sc.nextInt();
				votes_Undecided[state] = sc.nextInt();
      }
      sc.close();
      int answer = solution(num_states, delegates, votes_Biden, votes_Trump, votes_Undecided);
      	System.out.println(answer);
    	} catch (FileNotFoundException e) {
      	System.out.println("An error occurred.");
      	e.printStackTrace();
    	}     
  	}  	    

}  