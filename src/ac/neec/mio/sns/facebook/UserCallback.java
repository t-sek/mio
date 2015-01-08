package ac.neec.mio.sns.facebook;

import com.facebook.Response;
import com.facebook.model.GraphUser;

public interface UserCallback {
	 void onCompleted(GraphUser user, Response response);
}
