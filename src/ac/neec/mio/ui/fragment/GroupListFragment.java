package ac.neec.mio.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.DaoFactory;
import ac.neec.mio.dao.item.api.ApiDaoFactory;
import ac.neec.mio.dao.item.api.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.Group;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.http.HttpManager;
import ac.neec.mio.http.listener.GroupResponseListener;
import ac.neec.mio.ui.activity.GroupDetailsActivity;
import ac.neec.mio.ui.adapter.GroupListAdapter;
import ac.neec.mio.ui.listener.SearchNotifyListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;

public class GroupListFragment extends Fragment implements
		SearchNotifyListener, Sourceable {

	private static final String TITLE = "全てのグループ";
	private static final int MESSAGE_UPDATE = 1;

	private ListView listView;
	private List<Group> list = new ArrayList<Group>();
	private GroupListAdapter adapter;
	private ApiDao dao;

	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_UPDATE:
				update();
				break;
			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_group_list, container,
				false);
		dao = DaoFacade.getApiDao(getActivity().getApplicationContext(), this);
		dao.selectGroupAll();
		listView = (ListView) view.findViewById(R.id.list_group);
		listView.setEmptyView(view.findViewById(R.id.empty));

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String groupId = list.get(position).getId();
				intentGroupDetails(groupId);
			}
		});
		return view;
	}

	public SearchNotifyListener getListener() {
		return (SearchNotifyListener) this;
	}

	private void intentGroupDetails(String groupId) {
		Intent intent = new Intent(getActivity().getApplicationContext(),
				GroupDetailsActivity.class);
		intent.putExtra("Group_Id", groupId);
		startActivity(intent);
	}

	private void update() {
		adapter = new GroupListAdapter(getActivity().getApplicationContext(),
				R.layout.item_group, list);
		listView.setAdapter(adapter);
	}

	@Override
	public String toString() {
		return TITLE;
	}

	@Override
	public void onSearchText(String text) {
		adapter.getFilter().filter(text);
	}

	@Override
	public void onClear() {
		if (listView != null) {
			listView.clearTextFilter();
		}
	}

	@Override
	public void onSearchEnd() {
		dao.selectGroupAll();
	}

	@Override
	public void onUpdate() {
		dao.selectGroupAll();
	}

	@Override
	public void complete() {
		try {
			list = dao.getResponse();
		} catch (XmlParseException e) {
			e.printStackTrace();
		} catch (XmlReadException e) {
			e.printStackTrace();
		}
		Message message = new Message();
		message.what = MESSAGE_UPDATE;
		handler.sendMessage(message);
	}

	@Override
	public void incomplete() {
		// TODO Auto-generated method stub
		
	}
}