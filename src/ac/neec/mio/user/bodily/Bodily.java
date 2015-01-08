package ac.neec.mio.user.bodily;

import ac.neec.mio.training.framework.ProductData;

public class Bodily extends ProductData {

	private float height;
	private float weight;
	private float bmi;
	private int quietHeartRate;

	protected Bodily(float height, float weight, int quietHeartRate) {
		this.height = height;
		this.weight = weight;
		this.quietHeartRate = quietHeartRate;
	}

	public float getHeight() {
		return height;
	}

	public float getWeight() {
		return weight;
	}

	public float getBmi() {
		return bmi;
	}

	public int getQuietHeartRate() {
		return quietHeartRate;
	}

	private void calcBMI() {

	}

}
