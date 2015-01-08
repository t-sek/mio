package ac.neec.mio.ui.fragment;

import ac.neec.mio.R;
import ac.neec.mio.ui.activity.LoginActivity;
import ac.neec.mio.ui.activity.UserSignUpActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class FirstSignUpSelectFragment extends Fragment {

	private View view;
	private Button buttonSignUp;
	private Button buttonLogin;

	@Override
	public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
		if (transit == FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {
			return AnimationUtils
					.loadAnimation(getActivity(), R.anim.ans_start);
		}
		if (transit == FragmentTransaction.TRANSIT_FRAGMENT_CLOSE) {
			return AnimationUtils.loadAnimation(getActivity(), R.anim.ans_end);
		}
		return super.onCreateAnimation(transit, enter, nextAnim);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_first_sign_up_select, null);
		initFindViews();
		setListener();
		return view;
	}

	private void initFindViews() {
		buttonSignUp = (Button) view.findViewById(R.id.btn_sign_up);
		buttonLogin = (Button) view.findViewById(R.id.btn_login);
	}

	private void setListener() {
		buttonSignUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intentSignUp();
			}
		});
		buttonLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intentLogin();
			}
		});
	}

	private void intentSignUp() {
		Intent intent = new Intent(getActivity(), UserSignUpActivity.class);
		startActivity(intent);
		getActivity().finish();
	}

	private void intentLogin() {
		Intent intent = new Intent(getActivity(), LoginActivity.class);
		startActivity(intent);
		getActivity().finish();
	}

}
