package ac.neec.mio.ui.dialog;

import ac.neec.mio.ui.listener.AlertCallbackListener;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class SelectionAlertDialog extends DialogFragment {

	private AlertCallbackListener listener;
	private String message;
	private String positive;
	private String negative;
	private boolean cancel;

	public SelectionAlertDialog(AlertCallbackListener listener, String message,
			String positive, String negative, boolean cancel) {
		this.listener = listener;
		this.message = message;
		this.positive = positive;
		this.negative = negative;
		this.cancel = cancel;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
		dialog.setMessage(message);
		dialog.setPositiveButton(positive,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						listener.onPositiveSelected(message);
					}
				});
		dialog.setNegativeButton(negative,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						listener.onNegativeSelected(message);
					}
				});
		setCancelable(cancel);
		return dialog.create();

	}

}
