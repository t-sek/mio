package ac.neec.mio.ui.fragment;

import android.support.v4.app.Fragment;

/**
 * トップ画面のタブ画面抽象クラス<br>
 * タブ名を取得するメソッドを定義する
 *
 */
public abstract class TopBaseFragment extends Fragment {
	/**
	 * タブのタイトルを取得する
	 * 
	 * @return タブのタイトル
	 */
	public abstract String getTitle();

}
