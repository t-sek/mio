package ac.neec.mio.dao.item.api.parser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import android.content.res.Resources;
import android.util.Log;

public abstract class XmlParser {

	private static final String CHARSET = "UTF-8";
	private BufferedInputStream bufferedInputStream;
	private XmlPullParser xmlPullParser;
	protected InputStream response;

	private void init() throws XmlParseException, XmlReadException {
		bufferedInputStream = new BufferedInputStream(response);
		XmlPullParserFactory xmlPullParserFactory;
		try {
			xmlPullParserFactory = XmlPullParserFactory.newInstance();
			xmlPullParser = xmlPullParserFactory.newPullParser();
			xmlPullParser.setInput(bufferedInputStream, CHARSET);
			parseXml();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			throw new XmlParseException();
		}
	}

	public void setResponse(InputStream response) {
		this.response = response;
		Log.d("parser", "response "+this.response);
	}

	public <T> T getXmlParseObject() throws XmlParseException, XmlReadException {
		init();
		return getParseObject();
	}

	private void parseXml() throws XmlParseException, XmlReadException {
		int eventType;
		try {
			eventType = xmlPullParser.getEventType();
			while (true) {
				if (eventType == XmlPullParser.START_DOCUMENT) {
					startDocument();
				} else if (eventType == XmlPullParser.END_DOCUMENT) {
					endDocument();
					break;
				} else if (eventType == XmlPullParser.START_TAG) {
					startTag(xmlPullParser.getName());
				} else if (eventType == XmlPullParser.END_TAG) {
					endTag(xmlPullParser.getName());
				} else if (eventType == XmlPullParser.TEXT) {
					text(xmlPullParser.getText());
				}
				eventType = xmlPullParser.next();
			}
		} catch (XmlPullParserException e) {
			throw new XmlParseException();
		} catch (IOException e) {
			throw new XmlReadException();
		}
		if (bufferedInputStream != null) {
			try {
				bufferedInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (response != null) {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 文書開始
	 */
	protected abstract void startDocument();

	/**
	 * 文書終了
	 */
	protected abstract void endDocument();

	/**
	 * 開きタグ
	 * 
	 * @param text
	 *            タグ名
	 */
	protected abstract void startTag(String text);

	/**
	 * 閉じタグ
	 * 
	 * @param text
	 *            タグ名
	 */
	protected abstract void endTag(String text);

	/**
	 * 
	 * @param text
	 *            タグの値
	 */
	protected abstract void text(String text);

	/**
	 * 解析したデータを返す
	 * 
	 * @param <T>
	 * 
	 * @return
	 */
	protected abstract <T> T getParseObject();

}
