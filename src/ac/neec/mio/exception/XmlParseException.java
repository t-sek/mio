package ac.neec.mio.exception;

import org.xmlpull.v1.XmlPullParserException;

/**
 * XMLの解析に失敗
 */
public class XmlParseException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String ERROR_TEXT = "XMLの解析に失敗しました";

	public XmlParseException() {
		super(ERROR_TEXT);
	}

}
