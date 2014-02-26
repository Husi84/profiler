package mhu.playground.main;

public class Main {

	@SuppressWarnings("unused")
	private static final String OUTPUT_CSV_FILE = "profile.csv";
	@SuppressWarnings("unused")
	private static final String INPUT_LOGFILE = "profile.txt";

	public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println("Use: <Input filename> <output filename>");
			return;
		}

		try {
			new ProfileLogParser(args[0], args[1]);

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

}
