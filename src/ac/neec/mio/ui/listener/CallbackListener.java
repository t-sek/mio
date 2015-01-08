package ac.neec.mio.ui.listener;

public interface CallbackListener {
	void onQuietHeartRateClick();
	void onBirthClick();
	void onGenderClick();
	void onHeightClick();
	void onWeightClick();
	void onGenderSelected(String gender);
	void onBirthSelected(String birth);
	void onHeightSelected(String height);
	void onWeightSelected(String weight);
}
