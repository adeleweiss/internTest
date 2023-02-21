import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;  



/**
 *	Class with main method to read from a CSV file, inputing data into a 
 *	2D array. Each array in the 2D array holds the "payer", points, and time.
 *	There should be two CLAs, the file name and the amount of points to spend.
 *	The amount of points will be subtracted by each payer going in chronological 
 *	order, making sure no points go negative. The output is total number of points
 *	left per payer. 
 */
public class spendPoints {

	static class payer{
        protected String name;
        protected int points;
        protected String date;
  
        public payer(String name, int points, String date) {
            this.name = name;
            this.points = points;
            this.date = date;
        }

		public String getName(){
			return this.name;
		}

		public int getPoints(){
			return this.points;
		}

		public String getDate(){
			return this.date;
		}
  }
	public static void main(String[] args) throws FileNotFoundException {
		//check if CLAs are valid.
		if(args.length < 2){
			System.out.println("Not enough command line arguments.");
			return;
		} else if(args.length > 2){
			System.out.println("Too many command line arguments.");
			return;
		} 
		
		//attempt to open file.
		try {
		BufferedReader br = new BufferedReader(new FileReader(args[1]));		
	
		//Initialize necessary variables	
		String line;
		String payer;
		int points;
		String time; 
		int lineCount;
		lineCount = 0;
		List<payer> data = new ArrayList<payer>();		

		//read from the csv file 
		while ((line = br.readLine()) != null) {
			String[] split = line.split(",");
			payer = split[0].replace("\"", "");

			if (payer.equals("payer")){
				continue;
			}
			
			points = Integer.parseInt(split[1]);
			time = split[2].replace("\"", "");

			data.add(new payer(payer, points, time));
			
			Collections.sort(data.getDate());
		}

		
		} catch(FileNotFoundException e){
			System.out.println("File not found.");
			return;
		} catch(IOException e){
			System.out.println("Unexpected IO Exception");
			return;
		}
	}
}

