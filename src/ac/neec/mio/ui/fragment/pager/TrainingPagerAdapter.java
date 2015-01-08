package ac.neec.mio.ui.fragment.pager;

import java.util.List;

import ac.neec.mio.ui.fragment.top.MeasurementFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

public class TrainingPagerAdapter extends FragmentPagerAdapter implements
		IconPagerAdapter {

	private List<String> trainingName;

	public TrainingPagerAdapter(FragmentManager fm, List<String> trainingName) {
		super(fm);
		this.trainingName = trainingName;
	}

	@Override
	public Fragment getItem(int position) {
		return new MeasurementFragment();
	}

	@Override
	public int getCount() {
		return trainingName.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return trainingName.get(position);
	}

	@Override
	public int getIconResId(int index) {
		return 0;
	}

}