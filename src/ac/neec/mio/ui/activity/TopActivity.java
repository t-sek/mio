package ac.neec.mio.ui.activity;

import java.io.File;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.http.item.DateNumItem;
import ac.neec.mio.http.item.TrainingItem;
import ac.neec.mio.http.item.TrainingLogItem;
import ac.neec.mio.http.item.TrainingPlayItem;
import ac.neec.mio.http.listener.HttpResponseListener;
import ac.neec.mio.ui.fragment.pager.TopPagerAdapter;
import ac.neec.mio.ui.listener.TopCallbackListener;
import ac.neec.mio.user.User;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;
import com.viewpagerindicator.TabPageIndicator;

public class TopActivity extends FragmentActivity implements
		TopCallbackListener, HttpResponseListener {

	private TopPagerAdapter adapter;
	private TabPageIndicator indicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		adapter = new TopPagerAdapter(getSupportFragmentManager(), this,
				R.id.pager_top);
		ViewPager pager = (ViewPager) findViewById(R.id.pager_top);
		pager.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
		pager.setAdapter(adapter);
		pager.setId(R.id.pager_top);
		indicator = (TabPageIndicator) findViewById(R.id.indicator_top);
		indicator.setViewPager(pager);
		// indicator.setCurrentItem(2);
		indicator.setCurrentItem(1);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// ギャラリーから画像取得
		int index = adapter.getFragments().size() - 1;
		ContentResolver cr = getContentResolver();
		String[] columns = { MediaStore.Images.Media.DATA };
		Cursor c = cr.query(data.getData(), columns, null, null, null);
		c.moveToFirst();
		User.getInstance().setImageUri(c.getString(0));
		// File picture = new File(c.getString(0));
		// Log.e("activity", "image uri " + c.getString(0));
		adapter.getFragments().get(index)
				.onActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void responseTrainingId(int trainingId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void responseTraining(List<TrainingItem> list) {
		// TODO Auto-generated method stub
		Log.d("activity", "response " + list.get(0).getTrainingId());
		Log.d("activity", "response " + list.get(0).getDate());
		Log.d("activity", "response " + list.get(0).getStartTime());
		Log.d("activity", "response " + list.get(0).getPlayTime());
		Log.d("activity", "response " + list.get(0).getTargetHeartRate());
		Log.d("activity", "response " + list.get(0).getTargetCal());
		Log.d("activity", "response " + list.get(0).getHeartRateAvg());
	}

	@Override
	public void onResponse() {
		// TODO Auto-generated method stub

	}

	@Override
	public void networkError(VolleyError error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void responseNum(DateNumItem item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeviceScanClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	public void responseTrainingLog(TrainingLogItem item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void responseTrainingPlay(List<TrainingPlayItem> list) {
		// TODO Auto-generated method stub

	}
}
