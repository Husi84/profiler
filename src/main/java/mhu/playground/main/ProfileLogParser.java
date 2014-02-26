package mhu.playground.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import mhu.playground.model.ProfileStatistic;

public class ProfileLogParser {

	private static HashMap<String, ProfileStatistic> map = new HashMap<String, ProfileStatistic>();

	public ProfileLogParser(File inputFile) {
		try {
			this.parseInputFile(inputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ProfileLogParser(String inputFilename, String outputFilename) {

		try {
			parseInputFile(inputFilename);
			writeOutputFile(outputFilename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ProfileLogParser(File inputFile, File outputFile) {

		try {
			parseInputFile(inputFile);
			writeOutputFile(outputFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void parseInputFile(String filename) throws IOException {
		this.parseInputFile(new File(filename));
	}

	private void writeOutputFile(String filename) throws IOException {
		this.writeOutputFile(new File(filename));
	}

	private void parseInputFile(File inputfile) throws IOException {
		// Open the file that is the first
		// command line parameter
		final FileInputStream fstream = new FileInputStream(inputfile);
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

	private void parseLine(String strLine) {
		if (!strLine.startsWith("[Profiler]")) {
			return;
		}
		String[] split = strLine.split("[|]");
		Double duration = Double.parseDouble(split[1].trim().replace(',', '.'));
		String methodSignature = split[3].trim();

		if (getMap().get(methodSignature) == null) {
			getMap().put(methodSignature, new ProfileStatistic(duration));
		} else {
			getMap().get(methodSignature).add(duration);
		}
	}

	private void writeOutputFile(File outputfile) throws IOException {
		PrintWriter pW = new PrintWriter(outputfile);

		pW.println("Count;min;max;total;avg;funktion");
		for (String key : getMap().keySet()) {
			pW.println(getMap().get(key).toParsableString() + ";" + key);
		}
		pW.close();
	}

	public static HashMap<String, ProfileStatistic> getMap() {
		return map;
	}
}
