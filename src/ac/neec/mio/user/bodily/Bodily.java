package ac.neec.mio.user.bodily;

import ac.neec.mio.framework.ProductData;
import ac.neec.mio.user.bodily.weight.Weight;

public class Bodily extends ProductData {

	private float height;
	private Weight weight;
	private float bmi;
	private int quietHeartRate;

	protected Bodily(float height, Weight weight, int quietHeartRate) {
		this.height = height;
		this.weight = weight;
		this.quietHeartRate = quietHeartRate;
	}

	public float getHeight() {
		return height;
	}

	public Weight getWeight() {
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
