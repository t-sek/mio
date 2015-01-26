package ac.neec.mio.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.ui.fragment.GroupListFragment;
import ac.neec.mio.ui.fragment.MyGroupListFragment;
import ac.neec.mio.ui.listener.SearchNotifyListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class GroupListPagerAdapter extends FragmentStatePagerAdapter {

	private List<Fragment> fragments = new ArrayList<Fragment>();
	private List<SearchNotifyListener> groupList = new ArrayList<SearchNotifyListener>();

	public GroupListPagerAdapter(FragmentManager fm) {
		super(fm);
		setFragments();
	}

	private void setFragments() {
		SearchNotifyListener my = new MyGroupListFragment();
		SearchNotifyListener all = new GroupListFragment();
		groupList.add(my);
		groupList.add(all);
		fragments.add((Fragment) my);
		fragments.add((Fragment) all);
	}

	public List<SearchNotifyListener> getSearchListener() {
		return groupList;
	}

	@Override
	public Fragment getItem(int i) {
		return fragments.get(i);
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return fragments.get(position).toString();
	}
}