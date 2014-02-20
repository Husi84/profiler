package mhu.playground.model;

public class ProfileStatistic {

	private int counter;
	private double minDuration;
	private double maxDuration;
	private double totalDuration;

	public ProfileStatistic() {
		this.counter = 0;
		this.minDuration = 0;
		this.maxDuration = 0;
		this.totalDuration = 0;
	}

	public ProfileStatistic(double duration) {
		this.counter = 1;
		this.minDuration = duration;
		this.maxDuration = duration;
		this.totalDuration = duration;
	}

	public void add(double duration) {
		this.counter++;
		this.totalDuration += duration;
		if (this.minDuration > duration) {
			this.minDuration = duration;
		}
		if (this.maxDuration < duration) {
			this.maxDuration = duration;
		}
	}

	public double avg() {
		return this.totalDuration / this.counter;
	}

	public String toParsableString() {
		return counter + ";" + String.format("%.6f", minDuration) + ";"
				+ String.format("%.6f", maxDuration) + ";"
				+ String.format("%.6f", totalDuration) + ";"
				+ String.format("%.6f", this.avg());
	}

	@Override
	public String toString() {
		return "Count: " + counter + "; Min:"
				+ String.format("%.6f", minDuration) + ", Max: "
				+ String.format("%.6f", maxDuration) + ", Total: "
				+ String.format("%.6f", totalDuration) + ", Avg: "
				+ String.format("%.6f", this.avg());
	}
}
