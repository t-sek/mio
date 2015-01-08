package ac.neec.mio.ui.activity;

import java.util.Set;

import ac.neec.mio.R;
import ac.neec.mio.ui.dialog.ProfileBirthSettingDialog;
import ac.neec.mio.ui.dialog.ProfileBodilySelectDialog;
import ac.neec.mio.ui.dialog.ProfileSettingGenderDialog;
import ac.neec.mio.ui.listener.CallbackListener;
import ac.neec.mio.user.User;
import ac.neec.mio.util.BodilyUtil;
import ac.neec.mio.util.DateUtil;
import ac.neec.mio.consts.PreferenceConstants;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.util.Log;

public class ProfileSettingsPrefActivity extends Activity implements
		CallbackListener {
	private PrefFragment fragment;
	private User user = User.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragment = new PrefFragment();
		fragment.setListener(this);
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, fragment).commit();
	}

	public void setChanged(boolean b) {
		// Intent intent = new Intent();
		// // 設定変更があったことを呼び出し元に返す。
		// setResult(b ? RESULT_OK : RESULT_CANCELED, intent);
	}

	public static class PrefFragment extends PreferenceFragment implements
			OnSharedPreferenceChangeListener, OnPreferenceClickListener {

		private CallbackListener listener;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.pref_profile_bodily); // res/xml/adkterm_pref.xml
			addPreferencesFromResource(R.xml.pref_profile_master); // res/xml/adkterm_pref.xml

			getPreferenceScreen().setOnPreferenceClickListener(this);
			setClickListener();

			Set<String> preferenceNames = getPreferenceManager()
					.getSharedPreferences().getAll().keySet();
			for (String prefName : preferenceNames) {
				Preference preference = findPreference(prefName);
				if (preference != null) {
					preference.setOnPreferenceClickListener(this);
				}
			}
		}

		private void setClickListener() {
			PreferenceScreen height = (PreferenceScreen) getPreferenceScreen()
					.findPreference(PreferenceConstants.height());
			height.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					listener.onHeightClick();
					return false;
				}
			});
			PreferenceScreen weight = (PreferenceScreen) getPreferenceScreen()
					.findPreference(PreferenceConstants.weight());
			weight.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					listener.onWeightClick();
					return false;
				}
			});

			PreferenceScreen gender = (PreferenceScreen) getPreferenceScreen()
					.findPreference(PreferenceConstants.gender());
			gender.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					listener.onGenderClick();
					return false;
				}
			});
			PreferenceScreen birth = (PreferenceScreen) getPreferenceScreen()
					.findPreference(PreferenceConstants.birth());
			birth.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					listener.onBirthClick();
					return false;
				}
			});
			PreferenceScreen quiet = (PreferenceScreen) getPreferenceScreen()
					.findPreference(PreferenceConstants.quietHeartRate());
			quiet.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					listener.onQuietHeartRateClick();
					return false;
				}
			});

		}

		@Override
		public void onResume() {
			super.onResume();
			resetSummary();
			getPreferenceScreen().getSharedPreferences()
					.registerOnSharedPreferenceChangeListener(this);
		}

		@Override
		public void onPause() {
			super.onPause();
			getPreferenceScreen().getSharedPreferences()
					.unregisterOnSharedPreferenceChangeListener(this);
		}

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences paramSharedPreferences, String paramString) {
			resetSummary();
			((ProfileSettingsPrefActivity) getActivity()).setChanged(true);
		}

		public void resetSummary() {
			SharedPreferences sharedPrefs = getPreferenceManager()
					.getSharedPreferences();
			PreferenceScreen screen = this.getPreferenceScreen();
			for (int i = 0; i < screen.getPreferenceCount(); i++) {
				Preference pref = screen.getPreference(i);
				if (pref instanceof CheckBoxPreference
						|| pref instanceof SwitchPreference) {
					continue;
				}
				String key = pref.getKey();
				if (key == null) {
					continue;
				}
				String val = null;
				try {
					val = sharedPrefs.getString(key, "");
				} catch (ClassCastException e) {
					e.printStackTrace();
				}
				if (!key.equals(PreferenceConstants.password())
						&& !key.equals(PreferenceConstants.weight())) {
					pref.setSummary(val);
				}
			}
		}

		protected void setListener(CallbackListener listener) {
			this.listener = listener;
		}

		@Override
		public boolean onPreferenceClick(Preference preference) {
			String key = preference.getKey();
			if (key.equals(PreferenceConstants.height())) {
				listener.onHeightClick();
			} else if (key.equals(PreferenceConstants.weight())) {
				listener.onWeightClick();
			} else if (key.equals(PreferenceConstants.quietHeartRate())) {
				listener.onQuietHeartRateClick();
			} else if (key.equals(PreferenceConstants.birth())) {
				listener.onBirthClick();
			} else if (key.equals(PreferenceConstants.gender())) {
				listener.onGenderClick();
			}
			return false;
		}

	}

	@Override
	public void onQuietHeartRateClick() {
//		Intent intent = new Intent(ProfileSettingsPrefActivity.this,
//				QuietHeartRateMeasurementActivity.class);
//		startActivity(intent);
	}

	@Override
	public void onBirthClick() {
//		new ProfileBirthSettingDialog(this)
//				.show(getFragmentManager(), "dialog");
	}

	@Override
	public void onHeightClick() {
//		new ProfileBodilySelectDialog(this, BodilyUtil.height(),
//				ProfileBodilySelectDialog.HEIGHT).show(getFragmentManager(),
//				"dialog");

	}

	@Override
	public void onWeightClick() {
//		new ProfileBodilySelectDialog(this, BodilyUtil.weight(),
//				ProfileBodilySelectDialog.WEIGHT).show(getFragmentManager(),
//				"dialog");
	}

	@Override
	public void onGenderClick() {
		new ProfileSettingGenderDialog(this, user.getGender()).show(
				getFragmentManager(), "dialog");
	}

	@Override
	public void onGenderSelected(String gender) {
		user.setGender(gender);
	}

	@Override
	public void onBirthSelected(String birth) {
		user.setBirth(birth);
	}

	@Override
	public void onHeightSelected(String height) {
		user.setHeight(Float.valueOf(height));
	}

	@Override
	public void onWeightSelected(String weight) {
		user.setWeight(Float.valueOf(weight));
	}
}