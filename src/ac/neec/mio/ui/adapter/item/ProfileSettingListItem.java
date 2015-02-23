package ac.neec.mio.ui.adapter.item;

/**
 * ユーザ情報リストビューの項目を定義するクラス
 *
 */
public class ProfileSettingListItem {

	/**
	 * 項目名
	 */
	private String name;
	/**
	 * 項目数
	 */
	private String value;

	/**
	 * 
	 * @param name
	 *            項目名
	 * @param value
	 *            項目数
	 */
	public ProfileSettingListItem(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * 項目名を取得する
	 * 
	 * @return 項目名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 項目数を取得する
	 * 
	 * @return 項目数
	 */
	public String getValue() {
		return value;
	}

}
