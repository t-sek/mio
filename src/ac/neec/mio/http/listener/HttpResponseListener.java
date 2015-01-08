package ac.neec.mio.http.listener;

import java.util.List;

import ac.neec.mio.http.item.DateNumItem;
import ac.neec.mio.http.item.TrainingItem;
import ac.neec.mio.http.item.TrainingLogItem;
import ac.neec.mio.http.item.TrainingPlayItem;

import com.android.volley.VolleyError;

public interface HttpResponseListener {
	void responseTrainingId(int trainingId);

	void responseTraining(List<TrainingItem> list);

	void responseNum(DateNumItem item);

	void responseTrainingLog(TrainingLogItem item);

	void responseTrainingPlay(List<TrainingPlayItem> list);

	void onResponse();

	void networkError(VolleyError error);
}
