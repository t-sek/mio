package ac.neec.mio.ui.activity;

import java.io.File;
import java.util.List;

import ac.neec.mio.R;
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

import com.viewpagerindicator.TabPageIndicator;

/**
 * トップ画面クラス
 *
 */
public class TopActivity extends FragmentActivity implements
		TopCallbackListener {

	/**
	 * タブのアダプター
	 */
	private TopPagerAdapter adapter;
	/**
	 * タブ
	 */
	private ViewPager pager;
	/**
	 * タブナビゲーション
	 */
	private TabPageIndicator indicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initFindViews();
		setPager();
	}

	/**
	 * 画面の初期化処理をする
	 */
	private void initFindViews() {
		adapter = new TopPagerAdapter(getSupportFragmentManager(), this,
				R.id.pager_top);
		pager = (ViewPager) findViewById(R.id.pager_top);
		pager.setId(R.id.pager_top);
		indicator = (TabPageIndicator) findViewById(R.id.indicator_top);
	}

	/**
	 * タブを設定する
	 */
	private void setPager() {
		pager.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
		pager.setAdapter(adapter);
		pager.setOffscreenPageLimit(2);
		indicator.setViewPager(pager);
		indicator.setCurrentItem(1);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// ギャラリーから画像取得
		int index = adapter.getFragments().size() - 1;
		ContentResolver cr = getContentResolver();
		String[] columns = { MediaStore.Images.Media.DATA };
		if (data == null) {
			return;
		}
		Cursor c;
		try {
			c = cr.query(data.getData(), columns, null, null, null);
		} catch (NullPointerException e) {
			return;
		}
		c.moveToFirst();
		Log.d("activity", "file path " + c.getString(0));
		File file = new File(c.getString(0));
		Log.d("activity", "file name " + file.getName());
		User.getInstance().setImageUri(c.getString(0));
		adapter.getFragments().get(index)
				.onActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDeviceScanClicked() {
		// TODO Auto-generated method stub

	}

}
