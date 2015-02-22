package ac.neec.mio.ui.fragment.pager;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.ui.fragment.top.DashBoardFragment;
import ac.neec.mio.ui.fragment.top.DeviceDataFragment;
import ac.neec.mio.ui.fragment.top.TopBaseFragment;
import ac.neec.mio.ui.fragment.top.MeasurementFragment;
import ac.neec.mio.ui.fragment.top.ProfileFragment;
import ac.neec.mio.ui.fragment.top.TrainingDataFragment;
import ac.neec.mio.ui.listener.TopCallbackListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.google.android.gms.internal.hk;
import com.viewpagerindicator.IconPagerAdapter;

public class TopPagerAdapter extends FragmentPagerAdapter implements
		IconPagerAdapter {

	private List<String> contents = new ArrayList<String>();
	private List<TopBaseFragment> fragments = new ArrayList<TopBaseFragment>();
	private List<Integer> icons = new ArrayList<Integer>();

	private TopCallbackListener listener;
	private FragmentTransaction transaction;
	private FragmentManager manager;
	private int id;
	private DeviceDataFragment fragmentDeviceData;
	private boolean isDeviceDataShow = false;

	public TopPagerAdapter(FragmentManager manager,
			TopCallbackListener listener, int id) {
		super(manager);
		this.manager = manager;
		this.listener = listener;
		this.id = id;
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

	@Override
	public void notifyDataSetChanged() {
		if (transaction != null) {
			transaction.commitAllowingStateLoss();
			transaction = null;
		}
		super.notifyDataSetChanged();
	}

	public List<TopBaseFragment> getFragments() {
		return fragments;
	}

	public void updateDiviceFragment() {
		fragmentDeviceData = new DeviceDataFragment();
		replace(fragments.size() - 1, fragmentDeviceData);

	}

	public void updateHeartRate(String heartRate) {
		fragmentDeviceData.updateHeartRate(heartRate);
	}

	private void replace(int position, TopBaseFragment fragment) {
		String tag = makeFragmentName(id, position);
		if (transaction == null) {
			transaction = manager.beginTransaction();
		}
		transaction.replace(id, fragment, tag);
		fragments.set(position, fragment);
		for (TopBaseFragment fragment2 : fragments) {
			Log.e("adapter", "fragment " + fragment2);
		}
	}

	private String makeFragmentName(int viewId, int index) {
		return "android:switcher:" + viewId + ":" + index;
	}

	public void setContents() {
		fragments.add(new TrainingDataFragment());
		TopBaseFragment fragmentMeasurement = new MeasurementFragment();
		fragments.add(fragmentMeasurement);
		fragments.add(new ProfileFragment());
		for (TopBaseFragment fragment : fragments) {
			contents.add(fragment.getTitle());
		}

		icons.add(R.drawable.tab_data);
		// icons.add(R.drawable.perm_group_camera);
		icons.add(R.drawable.tab_training);
		icons.add(R.drawable.tab_profile);
		// icons.add(R.drawable.perm_group_device_alarms);
	}

}
