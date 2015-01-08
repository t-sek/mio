package ac.neec.mio.ui.fragment.top;

import ac.neec.mio.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DashBoardFragment extends TopBaseFragment {

	public static final String TITLE = "FEED";

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_dash_board, null);

		return view;
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

}
