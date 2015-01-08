package ac.neec.mio.exception;

public class NoResponseException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String ERROR_TEXT = "レスポンスがありません";

	public NoResponseException() {
		super(ERROR_TEXT);
	}

}
