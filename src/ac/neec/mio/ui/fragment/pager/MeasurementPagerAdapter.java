package ac.neec.mio.ui.fragment.pager;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.ui.fragment.GraphicalMeasurementFragment;
import ac.neec.mio.ui.fragment.MapMeasurementFragment;
import ac.neec.mio.ui.fragment.MeasurementBaseFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * 計測画面のタブを設定するアダプタークラス
 *
 */
public class MeasurementPagerAdapter extends FragmentPagerAdapter {

	/**
	 * タブに表示する画面フラグメントクラス
	 */
	private List<Fragment> fragments = new ArrayList<Fragment>();

	/**
	 * 
	 * @param fm
	 *            フラグメントマネージャー
	 */
	public MeasurementPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	/**
	 * 表示するフラグメントを追加する
	 * 
	 * @param list
	 *            表示するフラグメント
	 */
	public void addFragmentAll(List<Fragment> list) {
		fragments.addAll(list);
	}

}