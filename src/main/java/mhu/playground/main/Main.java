package mhu.playground.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import mhu.playground.model.ProfileStatistic;

public class Main {

	@SuppressWarnings("unused")
	private static final String OUTPUT_CSV_FILE = "profile.csv";
	@SuppressWarnings("unused")
	private static final String INPUT_LOGFILE = "profile.txt";

	private static HashMap<String, ProfileStatistic> map = new HashMap<String, ProfileStatistic>();

	public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println("Use: <Input filename> <output filename>");
			return;
		}

		try {
			parseInputFile(args[0]);

			writeOutputFile(args[1]);

			System.out.println("Count;min;max;total;avg;funktion");
			for (String key : map.keySet()) {
				System.out.println(map.get(key).toParsableString() + ";" + key);
			}

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	private static void writeOutputFile(String filename) throws IOException {
		PrintWriter pW = new PrintWriter(new FileWriter(filename));

		pW.println("Count;min;max;total;avg;funktion");
		for (String key : map.keySet()) {
			pW.println(map.get(key).toParsableString() + ";" + key);
		}
		pW.close();
	}

	private static void parseInputFile(String filename)
			throws FileNotFoundException, IOException {
		// Open the file that is the first
		// command line parameter
		final FileInputStream fstream = new FileInputStream(filename);
		// Get the object of DataInputStream
		final DataInputStream in = new DataInputStream(fstream);
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		// Read File Line By Line
		int lineCounter = 0;
		while ((strLine = br.readLine()) != null) {
			// Print the content on the console
			if (++lineCounter % 500 == 0) {
				System.out.print(".");
			}
			parseLine(strLine);
		}
		// Close the input stream
		in.close();
	}

	private static void parseLine(String strLine) {
		if (!strLine.startsWith("[Profiler]")) {
			return;
		}
		String[] split = strLine.split("[|]");
		Double duration = Double.parseDouble(split[1].trim().replace(',', '.'));
		String methodSignature = split[3].trim();

		if (map.get(methodSignature) == null) {
			map.put(methodSignature, new ProfileStatistic(duration));
		} else {
			map.get(methodSignature).add(duration);
		}
	}

}
