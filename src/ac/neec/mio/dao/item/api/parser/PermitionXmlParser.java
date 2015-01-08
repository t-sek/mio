package ac.neec.mio.dao.item.api.parser;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.group.Permission;
import ac.neec.mio.group.GroupFactory;
import ac.neec.mio.training.framework.ProductDataFactory;
import android.util.Log;

public class PermitionXmlParser extends XmlParser {

	private static final String TITLE = "Permition";
	private static final String ID = "permition_id";
	private static final String NAME = "permition_name";
	private static final String COMPEL = "compel_withdrawal";
	private static final String DIS = "dissolution";
	private static final String P_C = "permition_change";
	private static final String G_INFO_CHANGE = "g_info_change";
	private static final String M_AD_MANAGE = "m_ad_manage";
	private static final String M_DATA_CHECK = "m_data_check";
	private static final String M_LIST_VIEW = "m_list_view";
	private static final String G_INFO_VIEW = "g_info_view";
	private static final String WITH = "withdrawal";
	private static final String JOIN_STATUS = "join_status";
	private static final String GROUP_NEWS = "g_news";

	private List<Permission> list;
	private ProductDataFactory factory;
	private Permission item;
	private String tagName;
	private int id;
	private String name;
	private int compelWithdrawal;
	private int dissolution;
	private int permissionChange;
	private int groupInfoChange;
	private int memberAddManage;
	private int memberDataCheck;
	private int memberListView;
	private int groupInfoView;
	private int withdrawal;
	private int joinStatus;
	private int groupNews;

	@Override
	protected void startDocument() {
		list = new ArrayList<Permission>();
		factory = new GroupFactory();
	}

	@Override
	protected void endDocument() {

	}

	@Override
	protected void startTag(String text) {
		tagName = text;
	}

	@Override
	protected void endTag(String text) {
		if (text.equals(TITLE)) {
			list.add((Permission) factory.create(id, name, compelWithdrawal,
					dissolution, permissionChange, groupInfoChange,
					memberAddManage, memberDataCheck, memberListView,
					groupInfoView, withdrawal, joinStatus, groupNews));
		}
	}

	@Override
	protected void text(String text) {
		if (tagName.equals(ID)) {
			id = Integer.valueOf(text);
		} else if (tagName.equals(NAME)) {
			name = text;
		} else if (tagName.equals(COMPEL)) {
			compelWithdrawal = Integer.valueOf(text);
		} else if (tagName.equals(DIS)) {
			dissolution = Integer.valueOf(text);
		} else if (tagName.equals(P_C)) {
			permissionChange = Integer.valueOf(text);
		} else if (tagName.equals(G_INFO_CHANGE)) {
			groupInfoChange = Integer.valueOf(text);
		} else if (tagName.equals(M_AD_MANAGE)) {
			memberAddManage = Integer.valueOf(text);
		} else if (tagName.equals(M_DATA_CHECK)) {
			memberDataCheck = Integer.valueOf(text);
		} else if (tagName.equals(M_LIST_VIEW)) {
			memberListView = Integer.valueOf(text);
		} else if (tagName.equals(G_INFO_VIEW)) {
			groupInfoView = Integer.valueOf(text);
		} else if (tagName.equals(WITH)) {
			withdrawal = Integer.valueOf(text);
		} else if (tagName.equals(JOIN_STATUS)) {
			joinStatus = Integer.valueOf(text);
		} else if (tagName.equals(GROUP_NEWS)) {
			groupNews = Integer.valueOf(text);
		}
	}

	@Override
	protected List<Permission> getParseObject() {
		return list;
	}

}
