package ac.neec.mio.ui.adapter.item;

import android.graphics.Bitmap;

/**
 * グループメニューリストビューの項目を定義するクラス
 *
 */
public class GroupInfoListItem {
	/**
	 * メニュー名
	 */
	private String operation;
	/**
	 * アイコン
	 */
	private Bitmap greenIcon;
	/**
	 * 項目数
	 */
	private int num;

	/**
	 * 
	 * @param opr
	 *            メニュー名
	 * @param gi
	 *            アイコン
	 * @param num
	 *            項目数
	 */
	public GroupInfoListItem(String opr, Bitmap gi, int num) {
		this.operation = opr;
		this.greenIcon = gi;
		this.num = num;
	}

	/**
	 * アイコンを取得する
	 * 
	 * @return アイコン
	 *
	 */
	public Bitmap getIcon() {
		return greenIcon;
	}

	/**
	 * メニュー名を取得する
	 * 
	 * @return メニュー名
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * 項目数を取得する
	 * 
	 * @return 項目数
	 */
	public int getNum() {
		return num;
	}
}
