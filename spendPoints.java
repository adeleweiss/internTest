import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;  
import java.sql.Timestamp;  


/**
 *	Class with main method to read from a CSV file, inputing data into a 
 *	2D array. Each array in the 2D array holds the "payer", points, and time.
 *	There should be two CLAs, the file name and the amount of points to spend.
 *	The amount of points will be subtracted by each payer going in chronological 
 *	order, making sure no points go negative. The output is total number of points
 *	left per payer. 
 */
public class spendPoints {
	public static void main(String[] args) throws FileNotFoundException {
		//check if CLAs are valid.
		if(args.length < 2){
			System.out.println("Not enough command line arguments.");
			return;
		} else if(args.length > 2){
			System.out.println("Too many command line arguments.");
			return;
		} 
		try {
			if (Integer.parseInt(args[0]) < 0){
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e){
			System.out.println("Invalid number of points, please enter valid non-negative number.");
			return;
		}		
		//attempt to open file.
		try {
		BufferedReader count = new BufferedReader(new FileReader(args[1]));			
		String line;
		//get line count for array size
		int lines = 0;	
		while((line = count.readLine()) != null){
			lines++;
		}
		
		count.close();
	
		BufferedReader br = new BufferedReader(new FileReader(args[1]));
		//Initialize necessary variables	
		String time; 
		String[][] data = new String[lines-1][3];	
		
		//read from the csv file 
		for(int i = 0; i < lines;){	
			line = br.readLine();
			if (line == null){
				break;
			}
			String[] split = line.split(",");

			time = split[2].replace("\"", "");
			time = time.replace("T", " ");
			time = time.replace("Z", "");

			data[i][0] = split[0].replace("\"", "");
			if (data[i][0].equals("payer")){
                  continue;
            }
			data[i][1] = split[1];
			data[i][2] = time;
			i++;
		}
	
		//Sort data array by timestamp
		Arrays.sort(data, (a, b) -> Timestamp.valueOf(a[2]).compareTo(Timestamp.valueOf(b[2])));


		//Remove points in timestamp order

		int points = Integer.parseInt(args[0]);
		int payerPoints;
		for(int i = 0; i < data.length; i++){
			payerPoints = Integer.parseInt(data[i][1]);
			if(payerPoints < 0){
				points -= payerPoints;
				data[i][1] = "0";
			} else if(payerPoints > 0) {
				if(points > payerPoints){
					points -= payerPoints;
					data[i][1] = "0";
				} else {
					payerPoints -= points;
					points = 0;
					data[i][1] = String.valueOf(payerPoints);
					break;
				}
			}	
		}

	
		//organize output data
		List<String[]> output = new ArrayList<String[]>();
		List<String> names = new ArrayList<String>();

		for(int i = 0; i < data.length; i++){
			if(names.contains(data[i][0])){
				for(int j = 0; j < output.size(); j++){
					if(output.get(j)[0].equals(data[i][0])){
						output.get(j)[1] = String.valueOf(Integer.parseInt(output.get(j)[1])+ Integer.parseInt(data[i][1]));
					}
				}
			} else {
				names.add(data[i][0]);
				output.add(data[i]);
			}
		}
		

		//print output data
		for(int i = 0; i < output.size(); i ++){
			System.out.print("\"" + output.get(i)[0] + "\": " + output.get(i)[1]);
			if(i != output.size() - 1){
				System.out.println(",");
			} else {
				System.out.println();
			}
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

