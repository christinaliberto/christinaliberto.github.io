import java.io.*;
import java.util.*;
import java.text.*;

public class traceDump {
    public static void main(String args[]) {

	String file = "tcpdump.txt";
	String result = "output.txt";
	String line = null;

	try {
	
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
	    ArrayList<String> information = new ArrayList<String>();
	    FileWriter fileWriter = new FileWriter(result);
	    PrintWriter printWriter = new PrintWriter(fileWriter);

	    while ((line = bufferedReader.readLine()) != null) {
	    information.add(line);
	}

	int holder = 0;
	int write = 0;

	double time;

	for (String x: information) {
	    String current = information.get(holder);
	    String id = null;
	    String ttl = null;
	    String time1 = null;
	    String time2 = null;

	    if (current.indexOf("id") >= 0) {
		id = current.substring(current.indexOf("id"), current.indexOf(", o")).trim();
		ttl = current.substring(current.indexOf("ttl"), current.indexOf(", i")).trim().toUpperCase();
		time1 = current.substring(0, current.indexOf("IP")).trim();
	    }

	    for (int i = holder + 1; i < information.size(); i++) {

		String compare = information.get(i);
	    
		if (compare.indexOf("id") >= 0) {
		    String checkID = compare.substring(compare.indexOf("id"), compare.indexOf(", o")).trim();

		    if (checkID.equals(id) && !checkID.equals("id 0")) {
			String IPLoc = information.get(i-1);
			String IP = IPLoc.substring(0, IPLoc.indexOf(" >")).trim();
			String timeLoc = information.get(i - 2);
			time2 = timeLoc.substring(0, timeLoc.indexOf("IP")).trim();
			time = (Double.parseDouble(time2) - Double.parseDouble(time1)) * 1000;
			time = Math.round(time * 1000d)/1000d;

			if(write == 0) {
			    printWriter.println(ttl);
			    printWriter.println(IP);
			    printWriter.println(time + " ms");
			    write ++;
			} else if (write % 3 == 0 && write != 0) {
			    printWriter.println();
			    printWriter.println(ttl);
			    printWriter.println(IP);
			    printWriter.println(time + " ms");
			    write ++;
			} else {
			    printWriter.println(time + " ms");
			    write++;
			}
		    }
		}
	    }

	    holder++;
	}
    

	fileWriter.close();
	printWriter.close();
	bufferedReader.close();
	
	} catch(FileNotFoundException exception) {
	    System.out.println("Unable to open file");
	} catch (IOException exception) {
	    System.out.println("Error Reading File");
	}
    }
}
