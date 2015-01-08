package ac.neec.mio.ble;

import ac.neec.mio.R;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DisconnectDialogFragment extends DialogFragment{
	
	private static final String ALERT_MESSAGE = "MIOとの接続が切れました";
	
	@Override  
	public Dialog onCreateDialog(Bundle b)  
	{  
	  Dialog dialog = super.onCreateDialog(b);  
	  // タイトル  
	  dialog.setTitle(ALERT_MESSAGE);  
	  // ダイアログ外タップで消えないように設定  
	  dialog.setCanceledOnTouchOutside(false);  
	  return dialog;  
	}  
	  
	/** 
	 * UIを生成する。 
	 */  
	@Override  
	public View onCreateView(LayoutInflater i, ViewGroup c, Bundle b)  
	{  
	  View content = i.inflate(R.layout.dialog_ble_disconnect, null);  
	  return content;  
	}  
}
