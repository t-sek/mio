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

public class MeasurementPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments = new ArrayList<Fragment>();

	public MeasurementPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		Log.e("adapter", "item " + fragments.get(position));
		return fragments.get(position);
	}

	public List<Fragment> getFragments() {
		return fragments;
	}

	public void addFragment(MeasurementBaseFragment fragment) {
		fragments.add(fragment);
	}

	public void addFragmentAll(List<Fragment> list) {
		fragments.addAll(list);
	}

}