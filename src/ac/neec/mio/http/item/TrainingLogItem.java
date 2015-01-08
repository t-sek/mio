package ac.neec.mio.http.item;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class TrainingLogItem {

	private List<Integer> heartRate = new ArrayList<Integer>();
	private List<String> time = new ArrayList<String>();
	private List<Double> disX = new ArrayList<Double>();
	private List<Double> disY = new ArrayList<Double>();
	private List<String> lap = new ArrayList<String>();
	private List<String> split = new ArrayList<String>();
	private List<Integer> logId = new ArrayList<Integer>();
	private List<Integer> targetHeartRate = new ArrayList<Integer>();

	public void reset() {
		// heartRate = 0;
		// time = null;
		// disX = 0;
		// disY = 0;
		// lap = null;
		// split = null;
		// targetHeartRate = 0;
	}

	public void addHeartRate(int heartRate) {
		// this.heartRate = heartRate;
		Log.e("item", "heartRate call " + heartRate);
		this.heartRate.add(heartRate);
	}

	public void addTime(String time) {
		// this.time = time;
		this.time.add(time);
	}

	public void addDisX(double disX) {
		// this.disX = disX;
		this.disX.add(disX);
	}

	public void addDisY(double disY) {
		// this.disX = disY;
		this.disY.add(disY);
	}

	public void addLap(String lap) {
		// this.lap = lap;
		this.lap.add(lap);
	}

	public void addSplit(String split) {
		// this.split = split;
		this.split.add(split);
	}

	public void addLogId(int logId) {
		// this.logId = logId;
		this.logId.add(logId);
	}

	public void addTargetHeartRate(int targetHeartRate) {
		// this.targetHeartRate = targetHeartRate;
		this.targetHeartRate.add(targetHeartRate);
	}

	public int getHeartRate(int index) {
		return heartRate.get(index);
	}

	public String getTime(int index) {
		return time.get(index);
	}

	public double getDisX(int index) {
		return disX.get(index);
	}

	public double getDisY(int index) {
		return disY.get(index);
	}

	public String getLap(int index) {
		return lap.get(index);
	}

	public String getSplit(int index) {
		return split.get(index);
	}

	public int getLogId(int index) {
		return logId.get(index);
	}

	public int getTargetHeartRate(int index) {
		return targetHeartRate.get(index);
	}

	public int getSize() {
		return logId.size();
	}

}
