package ac.neec.mio.taining.play;

import ac.neec.mio.training.framework.ProductData;
import android.os.Parcel;
import android.os.Parcelable;

public class TrainingPlay extends ProductData implements Parcelable{

	private int playId;
	private int trainingMenuId;
	private int trainingTime;

	protected TrainingPlay(int playId, int trainingMenuId, int trainingTime) {
		this.playId = playId;
		this.trainingMenuId = trainingMenuId;
		this.trainingTime = trainingTime;
	}

	public int getPlayId() {
		return playId;
	}

	public int getTrainingMenuId() {
		return trainingMenuId;
	}

	public int getTrainingTime() {
		return trainingTime;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

}
