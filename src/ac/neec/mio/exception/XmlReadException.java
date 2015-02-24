package ac.neec.mio.exception;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

/**
 * XML読み込みエラー
 */
public class XmlReadException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String ERROR_TEXT = "XMLの読み込みに失敗しました";

	public XmlReadException() {
		super(ERROR_TEXT);
	}

}
