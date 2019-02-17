package frc.team5104.util;

import java.io.PrintWriter;

public class CSV {
	public static interface CSVLoggable {
		String[] getHeader();
		String[] getData();
	}
	
	private static String content;
	private static CSVLoggable target;
	
	/**
	 * Initialized the CSV class with a specified CSV target
	 * @param csvTarget A class that can be logged to a csv
	 */
	public static void init(CSVLoggable csvTarget) {
		target = csvTarget;
		content = stringArrayToString(target.getHeader()) + '\n';
	}
	
	/**
	 * Updated with new data from the pre-specified CSV target
	 */
	public static void update() {
		if (target != null)
			content += stringArrayToString(target.getData()) + '\n';
	}
	
	/**
	 * Saves the file onto the roborio
	 * @param folder The folder on the roborio
	 * @param fileName The name of the file to save on the roboio
	 */
	public static void writeFile(String folder, String file) {
		try {
			//Anti dumb
			if (file.indexOf(".") != -1)
				file = file.substring(0, file.indexOf("."));
			file += ".csv";
			if (folder.indexOf("lvuser") != -1)
				folder = folder.substring(folder.indexOf("lvuser") + 6);
			if (folder.charAt(0) == '/')
				folder = folder.substring(1);
			folder = "/home/lvuser/" + folder;
			if (folder.charAt(folder.length() - 1) != '/')
				folder += "/";
			
			PrintWriter writer = new PrintWriter(folder + file, "UTF-8");
			writer.print(content);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Takes a string array and turns it into a string (separated by commas)
	private static String stringArrayToString(String[] stringArray) {
		String returnValue = "";
		for(int i = 0; i < stringArray.length; i++) 
			returnValue += stringArray[i] + (i < stringArray.length - 1 ? ", " : "");
		return returnValue;
	}
}
