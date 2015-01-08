package ac.neec.mio.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import ac.neec.mio.dao.item.api.parser.TrainingIdXmlParser;
import ac.neec.mio.dao.item.api.parser.TrainingLogXmlParser;
import ac.neec.mio.dao.item.api.parser.TrainingPlayXmlParser;
import ac.neec.mio.dao.item.api.parser.TrainingXmlParser;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.http.item.TrainingItem;
import ac.neec.mio.http.item.TrainingLogItem;
import ac.neec.mio.http.item.TrainingPlayItem;
import ac.neec.mio.http.listener.GroupResponseListener;
import ac.neec.mio.http.listener.HttpMyGroupResponseListener;
import ac.neec.mio.http.listener.HttpResponseListener;
import ac.neec.mio.http.listener.HttpTrainingCategoryResponseListener;
import ac.neec.mio.http.listener.HttpTrainingMenuResponseListener;
import ac.neec.mio.http.listener.HttpUserResponseListener;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class HttpManager {

	// ver 0.00000001
	private static final String URL_DOWNLOAD_TRAINING_MENU_ALL = "http://anninsuika.com/mioapi/TrainingMenu.php?func=SelectALL";
	private static final String URL_DOWNLOAD_TRAINING_MENU = "http://anninsuika.com/mioapi/TrainingMenu.php?func=Select";
	private static final String URL_DOWNLOAD_TRAINING_CATEGORY_ALL = "http://anninsuika.com/mioapi/Category.php?func=SelectALL";
	private static final String URL_DOWNLOAD_TRAINING_ALL = "http://anninsuika.com/mioapi/TrainingInfo.php?func=Select&option=findAll&user=testid";

	// ver 0.1
	private static final String URL_CREATE_TRAINING = "http://anninsuika.com/mioapi/TrainingInfo.php?func=Insert";
	private static final String URL_UPLOAD_TRAINING = "http://anninsuika.com/mioapi/TrainingInfo.php?func=Insert";
	private static final String URL_UPLOAD_TRAINING_LOG = "http://anninsuika.com/mioapi/POST_TrainingLog.php?func=Insert";
	private static final String URL_UPLOAD_TRAINING_PLAY = "http://anninsuika.com/mioapi/Play.php?func=Insert";
	private static final String URL_DOWNLOAD_TRAINING = "http://anninsuika.com/mioapi/TrainingInfo.php?func=Select&option=findAll";
	private static final String URL_DOWNLOAD_TRAINING_DATE = "http://anninsuika.com/mioapi/TrainingInfo.php?func=Select&option=Date";
	private static final String URL_DOWNLOAD_TRAINING_LOG = "http://anninsuika.com/mioapi/GET_TrainingLog.php?func=Select";
	private static final String URL_DOWNLOAD_TRAINING_PLAY = "http://anninsuika.com/mioapi/Play.php?func=Select";
	private static final String URL_UPLOAD_USER_INFO = "https://anninsuika.com/Mio/api/useradd/";
	private static final String URL_DOWNLOAD_USER_INFO = "https://anninsuika.com/Mio/api/userinfo/";
	private static final String URL_DOWNLOAD_USER_LOGIN = "http://anninsuika.com/Mio/api/userinfo/";
	private static final String URL_UPDATE_USER_INFO = "https://anninsuika.com/Mio/api/useredit/";
	private static final String URL_DOWNLOAD_GROUP_ALL = "https://anninsuika.com/Mio/api/groupall.xml";
	private static final String URL_DOWNLOAD_GROUP_INFO = "https://anninsuika.com/Mio/api/groupinfo/";
	private static final String URL_UPLOAD_GROUP_REQUEST = "https://anninsuika.com/Mio/api/affiliationedit/";
	private static final String URL_UPLOAD_USER_WEIGHT = "https://anninsuika.com/Mio/api/weightadd/";
	private static final String URL_UPLOAD_USER_QUIET_HEART_RATE = "https://anninsuika.com/Mio/api/quiethredit/";
	private static final String URL_UPLOAD_NEW_GROUP = "https://anninsuika.com/Mio/api/groupadd/";
	private static final String URL_UPLOAD_EDIT_GROUP = "https://anninsuika.com/Mio/api/groupedit/";

	private static final String SECTION = "/";
	private static final String URL_END = ".xml";

	private static final String ITEM_USER_ID = "&user=";
	private static final String ITEM_TIME = "&time=";
	private static final String ITEM_TRAINING_ID = "&tid=";
	private static final String ITEM_LOG_TRAINING_ID = "&trid=";
	private static final String ITEM_TRAINING_MENU_ID = "&tmi=";
	private static final String ITEM_START_TIME = "&st=";
	private static final String ITEM_PLAY_TIME = "&pt=";
	private static final String ITEM_TARGET_HEART_RATE = "&thb=";
	private static final String ITEM_TARGET_CALORIE = "&tc=";
	private static final String ITEM_HEART_RATE_AVG = "&ha=";
	private static final String ITEM_TARGET_TRAINING_TIME = "&tpt=";
	private static final String ITEM_HEART_RATE = "&rate=";
	private static final String ITEM_DIS_X = "&disX=";
	private static final String ITEM_DIS_Y = "&disY=";
	private static final String ITEM_LAP = "&rap=";
	private static final String ITEM_SPLIT = "&sprit=";
	private static final String ITEM_NUM = "&num=";
	private static final String ITEM_LOG_TARGET_HEART_RATE = "&th=";
	private static final String ITEM_CAL = "&cal=";
	private static final String ITEM_DIS = "&dis=";
	private static final String ITEM_CATEGORY = "&ctg=";
	private static final String ITEM_DATE = "&date=";
	private static final String ITEM_PLAY_ID = "&pid=";
	private static final String ITEM_TRAINING_TIME = "&tt=";

	private static RequestQueue requestQueue;

	// public static void uploadTrainingPlay(final HttpResponseListener
	// listener,
	// Context context, int trainingId, int playId, int trainingMenuId,
	// String trainingTime) {
	// requestQueue = VolleyHelper.getRequestQueue(context);
	// StringRequest request = new StringRequest(Request.Method.POST,
	// setUploadTrainingPlayUrl(trainingId, playId, trainingMenuId,
	// trainingTime), new Response.Listener<String>() {
	// @Override
	// public void onResponse(String response) {
	// if (response == null) {
	// return;
	// }
	// listener.onResponse();
	// }
	// }, new Response.ErrorListener() {
	// @Override
	// public void onErrorResponse(VolleyError error) {
	// listener.networkError(error);
	// }
	// }) {
	// };
	// request.setShouldCache(false);
	// requestQueue.add(request);
	// requestQueue.start();
	// }

	private static String setUploadTrainingPlayUrl(int trainingId, int playId,
			int trainingMenuId, String trainingTime) {
		StringBuilder sb = new StringBuilder();
		sb.append(URL_UPLOAD_TRAINING_PLAY);
		sb.append(ITEM_TRAINING_ID);
		sb.append(trainingId);
		sb.append(ITEM_PLAY_ID);
		sb.append(playId);
		sb.append(ITEM_TRAINING_MENU_ID);
		sb.append(trainingMenuId);
		sb.append(ITEM_TRAINING_TIME);
		sb.append(trainingTime);
		return sb.toString();
	}

	public static void uploadTrainingLog(final HttpResponseListener listener,
			Context context, int trainingId, int heartRate, double disX,
			double disY, String time, String lapTime, String splitTime,
			int trainingLogId, int targetHeartRate) {
		// requestQueue = Volley.newRequestQueue(context);
		requestQueue = VolleyHelper.getRequestQueue(context);
		StringRequest request = new StringRequest(Request.Method.POST,
				setUploadTrainingLogUrl(trainingId, heartRate, disX, disY,
						time, lapTime, splitTime, trainingLogId,
						targetHeartRate), new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if (response == null) {
							return;
						}
						listener.onResponse();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.networkError(error);
					}
				}) {
		};
		request.setShouldCache(false);
		requestQueue.add(request);
		requestQueue.start();
	}

	public static String setUploadTrainingLogUrl(int trainingId, int heartRate,
			double disX, double disY, String time, String lapTime,
			String splitTime, int trainingLogId, int targetHeartRate) {
		StringBuilder sb = new StringBuilder();
		sb.append(URL_UPLOAD_TRAINING_LOG);
		sb.append(ITEM_LOG_TRAINING_ID);
		sb.append(trainingId);
		sb.append(ITEM_HEART_RATE);
		sb.append(heartRate);
		sb.append(ITEM_DIS_X);
		sb.append(disX);
		sb.append(ITEM_DIS_Y);
		sb.append(disY);
		sb.append(ITEM_TIME);
		sb.append(time);
		sb.append(ITEM_LAP);
		sb.append(lapTime);
		sb.append(ITEM_SPLIT);
		sb.append(splitTime);
		sb.append(ITEM_NUM);
		sb.append(trainingLogId);
		sb.append(ITEM_LOG_TARGET_HEART_RATE);
		sb.append(targetHeartRate);
		Log.e("manager", "url " + sb);
		return sb.toString();
	}

	public static void uploadTraining(final HttpResponseListener listener,
			Context context, String userId, String date, String startTime,
			String playTime, int targetHeartRate, int targetCal,
			int heartRateAvg, String targetPlayTime, int cal, int categoryId,
			double dis) {
		requestQueue = Volley.newRequestQueue(context);
		Request<InputStream> inputStreamRequest = new InputStreamRequest(
				Method.GET, setUploadTrainingUrl(userId, date, startTime,
						playTime, targetHeartRate, targetCal, heartRateAvg,
						targetPlayTime, cal, categoryId, dis),
				new Listener<InputStream>() {
					@Override
					public void onResponse(InputStream response) {

						ac.neec.mio.dao.item.api.parser.XmlParser p;
						int id = 0;
						try {
							p = new TrainingIdXmlParser();
							id = Integer.valueOf((String) p.getXmlParseObject());
						} catch (XmlReadException e1) {
							e1.printStackTrace();
						} catch (XmlParseException e1) {
							e1.printStackTrace();
						} catch (NumberFormatException e) {
							e.printStackTrace();
							// } catch (XmlPullParserException e) {
							// e.printStackTrace();
							// } catch (IOException e) {
							// e.printStackTrace();
							// }
						}
						listener.responseTrainingId(id);
						// listener.responseTrainingId(XmlParser
						// .createTrainingXml(response));
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.networkError(error);
					}
				});
		requestQueue.add(inputStreamRequest);
		requestQueue.start();
	}

	public static String setUploadTrainingUrl(String userId, String date,
			String startTime, String playTime, int targetHeartRate,
			int targetCal, int heartRateAvg, String targetPlayTime, int cal,
			int categoryId, double dis) {
		StringBuilder sb = new StringBuilder();
		sb.append(URL_UPLOAD_TRAINING);
		sb.append(ITEM_USER_ID);
		sb.append(userId);
		sb.append(ITEM_TIME);
		sb.append(date);
		sb.append(ITEM_START_TIME);
		sb.append(startTime);
		sb.append(ITEM_PLAY_TIME);
		sb.append(playTime);
		sb.append(ITEM_TARGET_HEART_RATE);
		sb.append(targetHeartRate);
		sb.append(ITEM_TARGET_CALORIE);
		sb.append(targetCal);
		sb.append(ITEM_HEART_RATE_AVG);
		sb.append(heartRateAvg);
		sb.append(ITEM_TARGET_TRAINING_TIME);
		sb.append(targetPlayTime);
		sb.append(ITEM_CAL);
		sb.append(cal);
		sb.append(ITEM_CATEGORY);
		sb.append(categoryId);
		sb.append(ITEM_DIS);
		sb.append(dis);
		return sb.toString();
	}

	public static void uploadCreateTraining(
			final HttpResponseListener listener, Context context,
			String userId, String date) {
		requestQueue = Volley.newRequestQueue(context);
		Request<InputStream> inputStreamRequest = new InputStreamRequest(
				Method.GET, setUploadCreateTrainingUrl(userId, date),
				new Listener<InputStream>() {
					@Override
					public void onResponse(InputStream response) {
						// listener.responseTrainingId(XmlParser
						// .createTrainingXml(response));
						ac.neec.mio.dao.item.api.parser.XmlParser p;
						int id = 0;
						try {
							p = new TrainingIdXmlParser();
							id = Integer.valueOf((String) p.getXmlParseObject());
						} catch (XmlReadException e1) {
							e1.printStackTrace();
						} catch (XmlParseException e1) {
							e1.printStackTrace();
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
						listener.responseTrainingId(id);

					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.networkError(error);
					}
				});
		requestQueue.add(inputStreamRequest);
		requestQueue.start();
	}

	private static String setUploadCreateTrainingUrl(String userId, String date) {
		StringBuilder sb = new StringBuilder();
		sb.append(URL_CREATE_TRAINING);
		sb.append(ITEM_USER_ID);
		sb.append(userId);
		sb.append(ITEM_TIME);
		sb.append(date);
		return sb.toString();
	}

	// public static void uploadRequestGroup(Context context,
	// final GroupResponseListener listener, String userId, String groupId) {
	// new HttpTask(listener, setUploadRequestGroupUrl(userId, groupId),
	// context, HttpTask.RESPONSE_GROUP).execute();
	// }

	// private static String setUploadRequestGroupUrl(String userId, String
	// groupId) {
	// StringBuilder sb = new StringBuilder();
	// sb.append(URL_UPLOAD_GROUP_REQUEST);
	// sb.append(userId + SECTION);
	// sb.append(groupId + SECTION);
	// sb.append(5 + URL_END);
	// return sb.toString();
	// }

	// public static void uploadNewGroup(Context context,
	// final GroupResponseListener listener, String groupId,
	// String groupName, String comment) {
	// new HttpTask(listener,
	// setUploadNewGroupUrl(groupId, groupName, comment), context,
	// HttpTask.RESPONSE_GROUP).execute();
	// }

	// private static String setUploadNewGroupUrl(String groupId,
	// String groupName, String comment) {
	// StringBuilder sb = new StringBuilder();
	// sb.append(URL_UPLOAD_NEW_GROUP);
	// sb.append(groupId + SECTION);
	// sb.append(groupName + SECTION);
	// sb.append(comment + URL_END);
	// return sb.toString();
	// }

	// public static void uploadEditGroup(Context context,
	// final GroupResponseListener listener, String groupId,
	// String groupName, String comment) {
	// new HttpTask(listener, setUploadEditGroupUrl(groupId, groupName,
	// comment), context, HttpTask.RESPONSE_GROUP).execute();
	// }
	//
	// private static String setUploadEditGroupUrl(String groupId,
	// String groupName, String comment) {
	// StringBuilder sb = new StringBuilder();
	// sb.append(URL_UPLOAD_EDIT_GROUP);
	// sb.append(groupId + SECTION);
	// sb.append(groupName + SECTION);
	// sb.append(comment + URL_END);
	// return sb.toString();
	// }

//	public static void downloadTrainingCategory(
//			HttpTrainingCategoryResponseListener listener, Context context) {
//		new HttpTask(listener, URL_DOWNLOAD_TRAINING_CATEGORY_ALL).execute();
//	}
//
//	public static void downloadTrainingMenu(
//			HttpTrainingMenuResponseListener listener, Context context) {
//		new HttpTask(listener, URL_DOWNLOAD_TRAINING_MENU_ALL).execute();
//	}

	public static void downloadTrainingMenu2(
			final HttpTrainingCategoryResponseListener listener, Context context) {
		requestQueue = Volley.newRequestQueue(context);
		Request<InputStream> inputStreamRequest = new InputStreamRequest(
				Method.GET, URL_DOWNLOAD_TRAINING_MENU,
				new Listener<InputStream>() {
					@Override
					public void onResponse(InputStream response) {
						// parseXml(response);
						// XmlParser.parseXml(response);
						// XmlParser.parseTrainingMenuXml(listener, response);
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
		requestQueue.add(inputStreamRequest);
		requestQueue.start();
	}

	public static void downloadTrainingLog(Context context,
			final HttpResponseListener listener, String userId, int trainingId) {
		requestQueue = VolleyHelper.getRequestQueue(context);
		Request<InputStream> inputStreamRequest = new InputStreamRequest(
				Method.GET, setDownloadTrainingLogUrl(userId, trainingId),
				new Listener<InputStream>() {
					@Override
					public void onResponse(InputStream response) {
						// List<TrainingLogItem> list = new
						// ArrayList<TrainingLogItem>();
						ac.neec.mio.dao.item.api.parser.XmlParser p;
						TrainingLogItem log = null;
						try {
							p = new TrainingLogXmlParser();
							log = (TrainingLogItem) p.getXmlParseObject();
						} catch (XmlReadException e) {
							e.printStackTrace();
						} catch (XmlParseException e) {
							e.printStackTrace();
						}
						// TrainingLogItem item =
						// XmlParser.parseLogXml(response);
						listener.responseTrainingLog(log);
						// listener.responseTrainingLog(list);
						// XmlParser.paeseLogLibXml(response);
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.networkError(error);
					}
				});
		inputStreamRequest.setShouldCache(false);
		requestQueue.add(inputStreamRequest);
		requestQueue.start();

	}

	private static String setDownloadTrainingLogUrl(String userId,
			int trainingId) {
		StringBuilder sb = new StringBuilder();
		sb.append(URL_DOWNLOAD_TRAINING_LOG);
		sb.append(ITEM_USER_ID);
		sb.append(userId);
		sb.append(ITEM_TRAINING_ID);
		sb.append(trainingId);
		return sb.toString();
	}

	public static void downloadTrainingDate(Context context,
			final HttpResponseListener listener, String userId,
			final String date) {
		requestQueue = VolleyHelper.getRequestQueue(context);
		// requestQueue = Volley.newRequestQueue(context);
		Request<InputStream> inputStreamRequest = new InputStreamRequest(
				Method.GET, setDownloadTrainingDateUrl(userId, date),
				new Listener<InputStream>() {
					@Override
					public void onResponse(InputStream response) {
						// List<TrainingItem> item = XmlParser
						// .parseTrainingXml(response);
						// XmlParser.parseXml(response);
						// if (item != null) {
						// listener.responseTraining(item);
						// }
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.networkError(error);
					}
				});
		inputStreamRequest.setShouldCache(false);
		requestQueue.add(inputStreamRequest);
		requestQueue.start();
	}

	private static String setDownloadTrainingDateUrl(String userId, String date) {
		StringBuilder sb = new StringBuilder();
		sb.append(URL_DOWNLOAD_TRAINING_DATE);
		sb.append(ITEM_USER_ID);
		sb.append(userId);
		sb.append(ITEM_DATE);
		sb.append(date);
		return sb.toString();
	}

	// public static void downloadTrainingDateNum(Context context,
	// final HttpResponseListener listener, String userId,
	// final String date) {
	// requestQueue = VolleyHelper.getRequestQueue(context);
	// // requestQueue = Volley.newRequestQueue(context);
	// Request<InputStream> inputStreamRequest = new InputStreamRequest(
	// Method.GET,
	// // setDownloadTrainingDateNumUrl(userId, date),
	// "http://anninsuika.com/mioapi/TrainingInfo.php?func=Select&option=Date&user=testid&date=2014-09-26",
	// new Listener<InputStream>() {
	// @Override
	// public void onResponse(InputStream response) {
	// DateNumItem item = XmlParser.parseTrainingDateNumXml(
	// response, date);
	// if (item != null) {
	// listener.responseNum(item);
	// }
	// }
	// }, new ErrorListener() {
	// @Override
	// public void onErrorResponse(VolleyError error) {
	// listener.networkError(error);
	// }
	// });
	// requestQueue.add(inputStreamRequest);
	// requestQueue.start();
	// }

	private static String setDownloadTrainingDateNumUrl(String userId,
			String date) {
		StringBuilder sb = new StringBuilder();
		sb.append(URL_DOWNLOAD_TRAINING_DATE);
		sb.append(ITEM_USER_ID);
		sb.append(userId);
		sb.append(ITEM_DATE);
		sb.append(date);
		return sb.toString();
	}

	// public static void downloadTraining(Context context,
	// final HttpResponseListener listener, String userId, int trainingId,
	// String date) {
	// // requestQueue = Volley.newRequestQueue(context);
	// requestQueue = VolleyHelper.getRequestQueue(context);
	//
	// String url;
	// if (date != null) {
	// url = setDownloadDateTrainingUrl(userId, date);
	// } else {
	// url = setDownloadTrainingUrl(userId, trainingId);
	// }
	// Request<InputStream> inputStreamRequest = new InputStreamRequest(
	// Method.GET, url, new Listener<InputStream>() {
	// @Override
	// public void onResponse(InputStream response) {
	// List<TrainingItem> list = new ArrayList<TrainingItem>();
	// // list = XmlParser.parseTrainingXml(response);
	// ac.neec.mio.http.parser.XmlParser p;
	// try {
	// p = new TrainingXmlParser();
	// list = p.getXmlParseObject();
	// } catch (XmlReadException e) {
	// e.printStackTrace();
	// } catch (XmlParseException e) {
	// e.printStackTrace();
	// } catch (XmlPullParserException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// // list = XmlParser.parseTrainingXml(response);
	// // XmlParser.parseXml(response);
	// listener.responseTraining(list);
	// }
	// }, new ErrorListener() {
	// @Override
	// public void onErrorResponse(VolleyError error) {
	// listener.networkError(error);
	// }
	// });
	// inputStreamRequest.setShouldCache(false);
	// requestQueue.add(inputStreamRequest);
	// requestQueue.start();
	// }

	private static String setDownloadTrainingUrl(String userId, int trainingId) {
		StringBuilder sb = new StringBuilder();
		sb.append(URL_DOWNLOAD_TRAINING);
		sb.append(ITEM_USER_ID);
		sb.append(userId);
		sb.append(ITEM_TRAINING_ID);
		sb.append(trainingId);
		return sb.toString();
	}

	// public static void downloadTrainingAll(Context context,
	// final HttpResponseListener listener) {
	// requestQueue = Volley.newRequestQueue(context);
	//
	// Request<InputStream> inputStreamRequest = new InputStreamRequest(
	// Method.GET,
	// // setDownloadTrainingUrl(trainingId),
	// URL_DOWNLOAD_TRAINING_ALL, new Listener<InputStream>() {
	// @Override
	// public void onResponse(InputStream response) {
	// // parseCreateTrainingXml(listener, respon
	// List<TrainingItem> list = new ArrayList<TrainingItem>();
	// // list = XmlParser.parseTrainingXml(response);
	// ac.neec.mio.http.parser.XmlParser p;
	// try {
	// p = new TrainingXmlParser();
	// list = p.getXmlParseObject();
	// } catch (XmlReadException e) {
	// e.printStackTrace();
	// } catch (XmlParseException e) {
	// e.printStackTrace();
	// } catch (XmlPullParserException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// // list = XmlParser.parseTrainingXml(response);
	// // XmlParser.parseXml(response);
	// listener.responseTraining(list);
	//
	// }
	// }, new ErrorListener() {
	// @Override
	// public void onErrorResponse(VolleyError error) {
	// }
	// });
	// inputStreamRequest.setShouldCache(false);
	// requestQueue.add(inputStreamRequest);
	// requestQueue.start();
	//
	// }

	private static String setDownloadDateTrainingUrl(String userId, String date) {
		StringBuilder sb = new StringBuilder();
		sb.append(URL_DOWNLOAD_TRAINING_DATE);
		sb.append(ITEM_USER_ID);
		sb.append(userId);
		sb.append(ITEM_DATE);
		sb.append(date);
		return sb.toString();
	}

	public static void downloadTrainingPlay(Context context,
			final HttpResponseListener listener, String userId, int trainingId) {
		requestQueue = VolleyHelper.getRequestQueue(context);
		Request<InputStream> inputStreamRequest = new InputStreamRequest(
				Method.GET, setDownloadTrainingPlayUrl(userId, trainingId),
				new Listener<InputStream>() {
					@Override
					public void onResponse(InputStream response) {
						ac.neec.mio.dao.item.api.parser.XmlParser p;
						List<TrainingPlayItem> list = null;
						try {
							p = new TrainingPlayXmlParser();
							list = p.getXmlParseObject();
						} catch (XmlReadException e) {
							e.printStackTrace();
						} catch (XmlParseException e) {
							e.printStackTrace();
						}
						listener.responseTrainingPlay(list);
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
		inputStreamRequest.setShouldCache(false);
		requestQueue.add(inputStreamRequest);
		requestQueue.start();
	}

	private static String setDownloadTrainingPlayUrl(String userId,
			int trainingId) {
		StringBuilder sb = new StringBuilder();
		sb.append(URL_DOWNLOAD_TRAINING_PLAY);
		sb.append(ITEM_USER_ID);
		sb.append(userId);
		sb.append(ITEM_TRAINING_ID);
		sb.append(trainingId);
		return sb.toString();
	}

//	public static void downloadUserLogin(Context context,
//			final HttpUserResponseListener listener, String userId,
//			String password) {
//		new HttpTask(listener, setDownloadUserLoginUrl(userId, password),
//				context, HttpTask.RESPONSE_USER).execute();
//	}

	private static String setDownloadUserLoginUrl(String userId, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(URL_DOWNLOAD_USER_LOGIN);
		sb.append(userId + SECTION);
		sb.append(password + URL_END);
		return sb.toString();
	}

	// public static void downloadUserInfo(Context context,
	// final HttpUserResponseListener listener, String userId) {
	// requestQueue = VolleyHelper.getRequestQueue(context);
	// Request<InputStream> inputStreamRequest = new InputStreamRequest(
	// Method.GET, setDownloadUserInfoUrl(userId),
	// new Listener<InputStream>() {
	// @Override
	// public void onResponse(InputStream response) {
	// // XmlParser.parseXml(response);
	// ac.neec.mio.http.parser.XmlParser p;
	// try {
	// p = new UserInfoXmlParser(
	// response);
	// } catch (XmlReadException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (XmlParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// UserInfo info;
	// try {
	// info = (UserInfo) p.getXmlParseObject();
	// } catch (XmlPullParserException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// // listener.responseUserInfo(XmlParser
	// // .parseUserInfo(response));
	// listener.responseUserInfo(info);
	// }
	// }, new ErrorListener() {
	// @Override
	// public void onErrorResponse(VolleyError error) {
	// }
	// });
	// inputStreamRequest.setShouldCache(false);
	// requestQueue.add(inputStreamRequest);
	// requestQueue.start();
	// }

	// private static String setDownloadUserInfoUrl(String userId) {
	// StringBuilder sb = new StringBuilder();
	// sb.append(URL_DOWNLOAD_USER_INFO);
	// sb.append(userId);
	// sb.append(URL_END);
	// return sb.toString();
	// }

//	public static void updateUserInfo(Context context,
//			final HttpUserResponseListener listener, String userId,
//			String name, String birth, String height, String mail) {
//		new HttpTask(listener, setUpdateUserInfo(userId, name, birth, height,
//				mail), context, HttpTask.RESPONSE_USER).execute();
//	}
//
//	public static void uploadUserInfo(Context context,
//			final HttpUserResponseListener listener, String userId,
//			String name, String birth, String gender, String height,
//			String mail, String pass, String weight) {
//		new HttpTask(listener, setUploadUserInfoUrl(userId, name, birth,
//				gender, height, mail, pass, weight), context,
//				HttpTask.RESPONSE_USER).execute();
//	}

	private static String setUpdateUserInfo(String userId, String name,
			String birth, String height, String mail) {
		StringBuilder sb = new StringBuilder();
		sb.append(URL_UPDATE_USER_INFO);
		sb.append(userId + SECTION);
		sb.append(name + SECTION);
		sb.append(birth + SECTION);
		sb.append(height + SECTION);
		sb.append(mail + URL_END);
		return sb.toString();
	}

	private static String setUploadUserInfoUrl(String userId, String name,
			String birth, String gender, String height, String mail,
			String pass, String weight) {
		StringBuilder sb = new StringBuilder();
		sb.append(URL_UPLOAD_USER_INFO);
		sb.append(userId + SECTION);
		sb.append(name + SECTION);
		sb.append(birth + SECTION);
		sb.append(gender + SECTION);
		sb.append(height + SECTION);
		sb.append(mail + SECTION);
		sb.append(pass + SECTION);
		sb.append(weight + URL_END);
		return sb.toString();
	}

//	public static void uploadUserWeight(Context context,
//			final HttpUserResponseListener listener, String userId,
//			String weight) {
//		new HttpTask(listener, setUploadUserWeight(userId, weight), context,
//				HttpTask.RESPONSE_WEIGHT).execute();
//	}
//
//	private static String setUploadUserWeight(String userId, String weight) {
//		StringBuilder sb = new StringBuilder();
//		sb.append(URL_UPLOAD_USER_WEIGHT);
//		sb.append(userId + SECTION);
//		sb.append(weight + URL_END);
//		return sb.toString();
//	}
//
//	public static void uploadUserQuietHeartRate(Context context,
//			final HttpUserResponseListener listener, String userId,
//			String quietHeartRate) {
//		new HttpTask(listener, setUploadUserQuietHeartRate(userId,
//				quietHeartRate), context, HttpTask.RESPONSE_QUIET_HEART_RATE)
//				.execute();
//	}

	private static String setUploadUserQuietHeartRate(String userId,
			String quietHeartRate) {
		StringBuilder sb = new StringBuilder();
		sb.append(URL_UPLOAD_USER_QUIET_HEART_RATE);
		sb.append(userId + SECTION);
		sb.append(quietHeartRate + URL_END);
		return sb.toString();
	}

	// public static void downloadMyGroup(Context context,
	// HttpMyGroupResponseListener listener, String userId, String password) {
	// new HttpTask(listener, setDownloadMyGroupUrl(userId, password), context)
	// .execute();
	// }

	// private static String setDownloadMyGroupUrl(String userId, String
	// password) {
	// StringBuilder sb = new StringBuilder();
	// sb.append(URL_DOWNLOAD_USER_INFO);
	// sb.append(userId + SECTION);
	// sb.append(password + URL_END);
	// return sb.toString();
	// }

	// public static void downloadGroupAll(Context context,
	// final GroupResponseListener listener) {
	// new HttpTask(listener, setDownloadGroupAllUrl(), context,
	// HttpTask.RESPONSE_GROUP_ALL).execute();
	// }
	//
	// private static String setDownloadGroupAllUrl() {
	// return URL_DOWNLOAD_GROUP_ALL;
	// }

	// public static void downloadGroupInfo(Context context,
	// final GroupResponseListener listener, String groupId) {
	// new HttpTask(listener, setDownloadGroupInfoUrl(groupId), context,
	// HttpTask.RESPONSE_GROUP).execute();
	// }
	//
	// private static String setDownloadGroupInfoUrl(String groupId) {
	// StringBuilder sb = new StringBuilder();
	// sb.append(URL_DOWNLOAD_GROUP_INFO);
	// sb.append(groupId);
	// sb.append(URL_END);
	// return sb.toString();
	// }

}
