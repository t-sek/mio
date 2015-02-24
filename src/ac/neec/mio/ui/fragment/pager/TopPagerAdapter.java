package ac.neec.mio.ui.fragment.pager;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.ui.fragment.DeviceDataFragment;
import ac.neec.mio.ui.fragment.MeasurementFragment;
import ac.neec.mio.ui.fragment.ProfileFragment;
import ac.neec.mio.ui.fragment.TopBaseFragment;
import ac.neec.mio.ui.fragment.TrainingDataFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.google.android.gms.internal.hk;
import com.viewpagerindicator.IconPagerAdapter;

/**
 * トップ画面のタブを設定するアダプタークラス
 *
 */
public class TopPagerAdapter extends FragmentPagerAdapter implements
		IconPagerAdapter {

	/**
	 * タブ名リスト
	 */
	private List<String> contents = new ArrayList<String>();
	/**
	 * タブ画面リスト
	 */
	private List<TopBaseFragment> fragments = new ArrayList<TopBaseFragment>();
	/**
	 * タブアイコンリスト
	 */
	private List<Integer> icons = new ArrayList<Integer>();

	/**
	 * 
	 * @param manager
	 *            フラグメントマネージャー
	 */
	public TopPagerAdapter(FragmentManager manager) {
		super(manager);
		setContents();
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return contents.get(position);
	}

	@Override
	public int getIconResId(int index) {
		return icons.get(index);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	/**
	 * タブ画面リストを取得する
	 * 
	 * @return タブ画面リスト
	 */
	public List<TopBaseFragment> getFragments() {
		return fragments;
	}

	/**
	 * タイトル、アイコンを設定する
	 */
	public void setContents() {
		fragments.add(new TrainingDataFragment());
		TopBaseFragment fragmentMeasurement = new MeasurementFragment();
		fragments.add(fragmentMeasurement);
		fragments.add(new ProfileFragment());
		for (TopBaseFragment fragment : fragments) {
			contents.add(fragment.getTitle());
		}
		icons.add(R.drawable.tab_data);
		icons.add(R.drawable.tab_training);
		icons.add(R.drawable.tab_profile);
	}

}
