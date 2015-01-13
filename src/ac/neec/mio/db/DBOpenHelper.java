package ac.neec.mio.db;

import static ac.neec.mio.consts.SQLConstants.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.taining.Training;
import ac.neec.mio.taining.TrainingFactory;
import ac.neec.mio.taining.category.TrainingCategory;
import ac.neec.mio.taining.category.TrainingCategoryFactory;
import ac.neec.mio.taining.menu.TrainingMenu;
import ac.neec.mio.taining.menu.TrainingMenuFactory;
import ac.neec.mio.taining.play.TrainingPlay;
import ac.neec.mio.taining.play.TrainingPlayFactory;
import ac.neec.mio.training.framework.ProductDataFactory;
import ac.neec.mio.training.heartrate.HeartRate;
import ac.neec.mio.training.heartrate.HeartRateFactory;
import ac.neec.mio.training.log.TrainingLog;
import ac.neec.mio.training.log.TrainingLogFactory;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {

	private Context context;
	private SQLiteDatabase db;

	protected DBOpenHelper(Context context) {
		super(context, dbName(), null, dbVersion());
		this.context = context;
		db = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		try {
			execCreateSql(sqlCreatePath());
		} catch (IOException e) {
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	protected int insertUser(String userId, String userName, String password,
			String gender, int height, int weight, int age, int quietHeartRate,
			String mail) {
		ContentValues cv = new ContentValues();
		cv.put(userId(), userId);
		cv.put(userName(), userName);
		cv.put(password(), password);
		cv.put(gender(), gender);
		cv.put(height(), height);
		cv.put(weight(), weight);
		cv.put(age(), age);
		cv.put(quietHeartRate(), quietHeartRate);
		cv.put(mail(), mail);
		return (int) db.insert(tableUser(), null, cv);
	}

	protected int insertTrainingCategory(int trainingCategoryId,
			String trainingCategoryName) {

		ContentValues cv = new ContentValues();
		cv.put(trainingCategoryId(), trainingCategoryId);
		cv.put(trainingCategoryName(), trainingCategoryName);
		return (int) db.insert(tableTrainingCategory(), null, cv);
	}

	protected int insertTrainingMenu(int trainingMenuId, String trainingName,
			float mets, int trainingCategoryId, String color) {
		ContentValues cv = new ContentValues();
		cv.put(trainingMenuId(), trainingMenuId);
		cv.put(trainingName(), trainingName);
		cv.put(mets(), mets);
		cv.put(trainingCategoryId(), trainingCategoryId);
		cv.put(color(), color);
		return (int) db.insert(tableTrainingMenu(), null, cv);
	}

	protected int insertTraining(int trainingId, int trainingCategoryId,
			String userId, String date, String startTime, String playTime,
			int targetHeartRate, int targetCal, int consumptionCal,
			int heartRateAvg, int strange, double distance) {
		ContentValues cv = new ContentValues();
		cv.put(trainingId(), trainingId);
		cv.put(trainingCategoryId(), trainingCategoryId);
		cv.put(userId(), userId);
		cv.put(date(), date);
		cv.put(startTime(), startTime);
		cv.put(playTime(), playTime);
		cv.put(targetHeartRate(), targetHeartRate);
		cv.put(targetCal(), targetCal);
		cv.put(consumptionCal(), consumptionCal);
		cv.put(heartRateAvg(), heartRateAvg);
		cv.put(strange(), strange);
		cv.put(distance(), distance);
		return (int) db.insert(tableTraining(), null, cv);
	}

	protected int insertTrainingLog(int id, int heartRate, double disX,
			double disY, String time, String lap, String split,
			int targetHeartRate) {
		ContentValues cv = new ContentValues();
		cv.put(id(), id);
		cv.put(heartRate(), heartRate);
		cv.put(disX(), disX);
		cv.put(disY(), disY);
		cv.put(time(), time);
		cv.put(lap(), lap);
		cv.put(split(), split);
		cv.put(targetHeartRate(), targetHeartRate);
		return (int) db.insert(tableTrainingLog(), null, cv);
	}

	protected int insertHeartRateLog(int trainingId, int userId) {
		ContentValues cv = new ContentValues();
		cv.put(trainingId(), trainingId);
		cv.put(userId(), userId);
		return (int) db.insert(tableHeartRateLog(), null, cv);
	}

	protected int insertHeartRate(int trainingId, String time, int heartRate) {
		ContentValues cv = new ContentValues();
		cv.put(trainingId(), trainingId);
		cv.put(time(), time);
		cv.put(heartRate(), heartRate);
		return (int) db.insert(tableHeartRate(), null, cv);
	}

	protected int insertTrainingPlay(int id, int trainingMenuId,
			int trainingTime) {
		ContentValues cv = new ContentValues();
		cv.put(id(), id);
		cv.put(trainingMenuId(), trainingMenuId);
		cv.put(trainingTime(), trainingTime);
		return (int) db.insert(tableTrainingPlay(), null, cv);
	}

	protected int deleteTraining(int trainingId) {
		String selection = trainingId() + " = ? ";
		String[] selectionArgs = { String.valueOf(trainingId) };
		return db.delete(tableTraining(), selection, selectionArgs);
	}

	protected int updateTraining(int id, int trainingId,
			int trainingCategoryId, String date, String startTime,
			String playTime, int targetHeartRate, int targetCal,
			int consumptionCal, int heartRateAvg, int strange, double distance) {
		ContentValues cv = new ContentValues();
		String selection = id() + " = ? ";
		String[] selectionArgs = { String.valueOf(id) };
		if (trainingId != 0) {
			cv.put(trainingId(), trainingId);
		}
		if (trainingCategoryId != 0) {
			cv.put(trainingCategoryId(), trainingCategoryId);
		}
		if (date != null) {
			cv.put(date(), date);
		}
		if (startTime != null) {
			cv.put(startTime(), startTime);
		}
		if (playTime != null) {
			cv.put(playTime(), playTime);
		}
		if (targetHeartRate != 0) {
			cv.put(targetHeartRate(), targetHeartRate);
		}
		if (targetCal != 0) {
			cv.put(targetCal(), targetCal);
		}
		if (consumptionCal != 0) {
			cv.put(consumptionCal(), consumptionCal);
		}
		if (heartRateAvg != 0) {
			cv.put(heartRateAvg(), heartRateAvg);
		}
		if (strange != 0) {
			cv.put(strange(), strange);
		}
		if (distance != 0) {
			cv.put(distance(), distance);
		}
		return (int) db.update(tableTraining(), cv, selection, selectionArgs);

	}

	protected Training selectTraining(int id) {
		ProductDataFactory factory = new TrainingFactory();
		Training training = null;
		String selection = id() + " = ? ";
		String[] selectionArgs = { String.valueOf(id) };
		Cursor cursor = db.query(tableTraining(), selectTrainingTable(),
				selection, selectionArgs, null, null, null);
		int indexId = cursor.getColumnIndex(id());
		int indexTrainingId = cursor.getColumnIndex(trainingId());
		int indexTrainingCategoryId = cursor
				.getColumnIndex(trainingCategoryId());
		int indexUserId = cursor.getColumnIndex(userId());
		int indexDate = cursor.getColumnIndex(date());
		int indexStartTime = cursor.getColumnIndex(startTime());
		int indexPlayTime = cursor.getColumnIndex(playTime());
		int indexTargetHeartRate = cursor.getColumnIndex(targetHeartRate());
		int indexTargetCal = cursor.getColumnIndex(targetCal());
		int indexConsumptionCal = cursor.getColumnIndex(consumptionCal());
		int indexHeartRateAvg = cursor.getColumnIndex(heartRateAvg());
		int indexStrange = cursor.getColumnIndex(strange());
		int indexDistance = cursor.getColumnIndex(distance());

		while (cursor.moveToNext()) {
			training = (Training) factory.create(cursor.getInt(indexId),
					cursor.getInt(indexTrainingCategoryId),
					cursor.getInt(indexUserId), cursor.getString(indexDate),
					cursor.getString(indexStartTime),
					cursor.getString(indexPlayTime),
					cursor.getInt(indexTargetHeartRate),
					cursor.getInt(indexTargetCal),
					cursor.getInt(indexConsumptionCal),
					cursor.getInt(indexHeartRateAvg),
					cursor.getInt(indexStrange),
					cursor.getDouble(indexDistance));
		}
		return training;
	}

	protected String selectTrainingMenuName(int trainingCategoryId) {
		String trainingName = null;
		String selection = trainingCategoryId() + " = ?";
		String[] selectionArgs = { String.valueOf(trainingCategoryId) };

		Cursor cursor = db.query(tableTrainingMenu(),
				selectTrainingMenuTable(), selection, selectionArgs, null,
				null, null);
		int indexTrainingName = cursor.getColumnIndex(trainingName());

		while (cursor.moveToNext()) {
			trainingName = cursor.getString(indexTrainingName);
		}
		cursor.close();
		return trainingName;
	}

	protected List<TrainingMenu> selectTrainingCategoryMenu(
			int trainingCategoryId) {
		List<TrainingMenu> list = new ArrayList<TrainingMenu>();
		ProductDataFactory factory = new TrainingMenuFactory();
		String selection = trainingCategoryId() + " = ?";
		String[] selectionArgs = { String.valueOf(trainingCategoryId) };

		Cursor cursor = db.query(tableTrainingMenu(),
				selectTrainingMenuTable(), selection, selectionArgs, null,
				null, null);
		int indexTrainingMenuId = cursor.getColumnIndex(trainingMenuId());
		int indexTrainingName = cursor.getColumnIndex(trainingName());
		int indexMets = cursor.getColumnIndex(mets());
		int indexTrainingCategoryId = cursor
				.getColumnIndex(trainingCategoryId());
		int indexColor = cursor.getColumnIndex(color());
		while (cursor.moveToNext()) {
			list.add((TrainingMenu) factory.create(
					cursor.getInt(indexTrainingMenuId),
					cursor.getString(indexTrainingName),
					cursor.getFloat(indexMets),
					cursor.getInt(indexTrainingCategoryId),
					cursor.getString(indexColor)));
		}
		cursor.close();
		return list;
	}

	protected TrainingMenu selectTrainingMenu(String trainingName) {
		ProductDataFactory factory = new TrainingMenuFactory();
		TrainingMenu menu = null;
		String selection = trainingName() + " = ? ";
		String[] selectionArgs = { trainingName };
		Cursor cursor = db.query(tableTrainingMenu(),
				selectTrainingMenuTable(), selection, selectionArgs, null,
				null, null);
		int indexTrainingMenuId = cursor.getColumnIndex(trainingMenuId());
		int indexTrainingName = cursor.getColumnIndex(trainingName());
		int indexMets = cursor.getColumnIndex(mets());
		int indexTrainingCategoryId = cursor
				.getColumnIndex(trainingCategoryId());
		int indexColor = cursor.getColumnIndex(color());

		while (cursor.moveToNext()) {
			menu = (TrainingMenu) factory.create(
					cursor.getInt(indexTrainingMenuId),
					cursor.getString(indexTrainingName),
					cursor.getFloat(indexMets),
					cursor.getInt(indexTrainingCategoryId),
					cursor.getString(indexColor));
		}
		cursor.close();
		return menu;
	}

	protected List<TrainingMenu> selectTrainingMenu() {
		List<TrainingMenu> list = new ArrayList<TrainingMenu>();
		ProductDataFactory factory = new TrainingMenuFactory();
		Cursor cursor = db.query(tableTrainingMenu(),
				selectTrainingMenuTable(), null, null, null, null, null);
		int indexTrainingMenuId = cursor.getColumnIndex(trainingMenuId());
		int indexTrainingName = cursor.getColumnIndex(trainingName());
		int indexMets = cursor.getColumnIndex(mets());
		int indexTrainingCategoryId = cursor
				.getColumnIndex(trainingCategoryId());
		int indexColor = cursor.getColumnIndex(color());
		Log.d("helper", "indexColor " + indexColor);

		while (cursor.moveToNext()) {
			list.add((TrainingMenu) factory.create(
					cursor.getInt(indexTrainingMenuId),
					cursor.getString(indexTrainingName),
					cursor.getFloat(indexMets),
					cursor.getInt(indexTrainingCategoryId),
					cursor.getString(indexColor)));
		}
		return list;
	}

	protected TrainingMenu selectTrainingMenu(int trainingMenuId) {
		TrainingMenu trainingMenu = null;
		ProductDataFactory factory = new TrainingMenuFactory();
		String selection = trainingMenuId() + " = ?";
		String[] selectionArgs = { String.valueOf(trainingMenuId) };

		Cursor cursor = db.query(tableTrainingMenu(),
				selectTrainingMenuTable(), selection, selectionArgs, null,
				null, null);
		int indexTrainingMenuId = cursor.getColumnIndex(trainingMenuId());
		int indexTrainingName = cursor.getColumnIndex(trainingName());
		int indexMets = cursor.getColumnIndex(mets());
		int indexTrainingCategoryId = cursor
				.getColumnIndex(trainingCategoryId());
		int indexColor = cursor.getColumnIndex(color());

		while (cursor.moveToNext()) {
			trainingMenu = (TrainingMenu) factory.create(
					cursor.getInt(indexTrainingMenuId),
					cursor.getString(indexTrainingName),
					cursor.getFloat(indexMets),
					cursor.getInt(indexTrainingCategoryId),
					cursor.getString(indexColor));
		}
		cursor.close();
		return trainingMenu;
	}

	protected TrainingCategory selectTrainingCategory(int categoryId) {
		TrainingCategory category = null;
		ProductDataFactory factory = new TrainingCategoryFactory();
		String selection = trainingCategoryId() + " = ?";
		String[] selectionArgs = { String.valueOf(categoryId) };
		Cursor cursor = db.query(tableTrainingCategory(),
				selectTrainingCategoryTable(), selection, selectionArgs, null,
				null, null);
		int indexTrainingCategoryId = cursor
				.getColumnIndex(trainingCategoryId());
		int indexTrainingCategoryName = cursor
				.getColumnIndex(trainingCategoryName());
		while (cursor.moveToNext()) {
			category = (TrainingCategory) factory.create(
					cursor.getInt(indexTrainingCategoryId),
					cursor.getString(indexTrainingCategoryName));
		}
		return category;
	}

	protected List<TrainingCategory> selectTrainingCategory() {
		List<TrainingCategory> list = new ArrayList<TrainingCategory>();
		ProductDataFactory factory = new TrainingCategoryFactory();
		Cursor cursor = db.query(tableTrainingCategory(),
				selectTrainingCategoryTable(), null, null, null, null, null);
		int indexTrainingCategoryId = cursor
				.getColumnIndex(trainingCategoryId());
		int indexTrainingCategoryName = cursor
				.getColumnIndex(trainingCategoryName());
		while (cursor.moveToNext()) {
			list.add((TrainingCategory) factory.create(
					cursor.getInt(indexTrainingCategoryId),
					cursor.getString(indexTrainingCategoryName)));
		}
		return list;
	}

	// protected void selectHeartRateLog() {
	// Cursor cursor = db.query(tableHeartRateLog(),
	// selectHeartRateLogTable(), null, null, null, null, null);
	// int indexLogId = cursor.getColumnIndex(logId());
	// while (cursor.moveToNext()) {
	// }
	// }

	protected List<HeartRate> selectHeartRate(int trainingId) {
		List<HeartRate> list = new ArrayList<HeartRate>();
		ProductDataFactory factory = new HeartRateFactory();
		String selection = trainingId() + " = ?";
		String[] selectionArgs = { String.valueOf(trainingId) };
		Cursor cursor = db.query(tableHeartRate(), selectHeartRateTable(),
				selection, selectionArgs, null, null, null);
		int indexTime = cursor.getColumnIndex(time());
		int indexHeartRate = cursor.getColumnIndex(heartRate());
		while (cursor.moveToNext()) {
			list.add((HeartRate) factory.create(cursor.getInt(indexHeartRate),
					Time.valueOf(cursor.getString(indexTime))));
		}
		return list;
	}

	protected List<TrainingLog> selectTrainingLog(int id) {
		List<TrainingLog> list = new ArrayList<TrainingLog>();
		ProductDataFactory factory = new TrainingLogFactory();
		String selection = id() + " = ?";
		String[] selectionArgs = { String.valueOf(id) };
		Cursor cursor = db.query(tableTrainingLog(), selectTrainingLogTable(),
				selection, selectionArgs, null, null, null);
		int indexLogId = cursor.getColumnIndex(logId());
		int indexId = cursor.getColumnIndex(id());
		int indexHeartRate = cursor.getColumnIndex(heartRate());
		int indexDisX = cursor.getColumnIndex(disX());
		int indexDisY = cursor.getColumnIndex(disY());
		int indexTime = cursor.getColumnIndex(time());
		int indexLap = cursor.getColumnIndex(lap());
		int indexSplit = cursor.getColumnIndex(split());
		int indexTargetHeartRate = cursor.getColumnIndex(targetHeartRate());
		while (cursor.moveToNext()) {
			list.add((TrainingLog) factory.create(cursor.getInt(indexLogId),
					cursor.getInt(indexId), cursor.getInt(indexHeartRate),
					Double.valueOf(cursor.getDouble(indexDisX)),
					Double.valueOf(cursor.getDouble(indexDisY)),
					cursor.getString(indexTime), cursor.getString(indexLap),
					cursor.getString(indexSplit),
					cursor.getInt(indexTargetHeartRate)));
		}
		return list;
	}

	protected List<TrainingPlay> selectTrainingPlay(int id) {
		List<TrainingPlay> list = new ArrayList<TrainingPlay>();
		ProductDataFactory factory = new TrainingPlayFactory();
		String selection = id() + " = ?";
		String[] selectionArgs = { String.valueOf(id) };
		Cursor cursor = db.query(tableTrainingPlay(),
				selectTrainingPlayTable(), selection, selectionArgs, null,
				null, null);
		int indexTrainingMenuId = cursor.getColumnIndex(trainingMenuId());
		int indexTrainingTime = cursor.getColumnIndex(trainingTime());
		while (cursor.moveToNext()) {
			list.add((TrainingPlay) factory.create(
					cursor.getInt(indexTrainingMenuId),
					cursor.getInt(indexTrainingTime)));
		}
		return list;
	}

	protected int deleteTrainingTableAll() {
		return (int) db.delete(tableTraining(), null, null);
	}

	protected int deleteTrainingLogTableAll() {
		return (int) db.delete(tableHeartRateLog(), null, null);
	}

	protected int deleteTrainingPlayTableAll() {
		return (int) db.delete(tableTrainingPlay(), null, null);
	}

	protected int deleteTrainingCategoryTableAll() {
		return (int) db.delete(tableTrainingCategory(), null, null);
	}

	protected int deleteTrainingMenuTableAll() {
		return (int) db.delete(tableTrainingMenu(), null, null);
	}

	private void execCreateSql(String assetsDir) throws IOException {
		AssetManager assetManager = context.getResources().getAssets();
		try {
			InputStream is = assetManager.open(assetsDir);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String str;
			StringBuilder sql = new StringBuilder();
			while ((str = br.readLine()) != null) {
				if (str.equals("/")) {
					db.execSQL(sql.toString());
					sql.delete(0, sql.length());
				} else {
					sql.append(str);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void insertcategorySql(String assetsDir) throws IOException {
		AssetManager as = context.getResources().getAssets();
		List<String> list = new ArrayList<String>();
		try {
			String str = readFile(as.open(assetsDir));
			String[] dataStr;
			for (String sql : str.split("/\n")) {
				dataStr = sql.split("@");
				int id = Integer.valueOf(dataStr[0]);
				insertTrainingCategory(id, dataStr[1]);
				list.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void insertTrainingMenuSql(String assetsDir) {
		AssetManager as = context.getResources().getAssets();
		List<String> list = new ArrayList<String>();
		try {
			String str = readFile(as.open(assetsDir));
			String[] dataStr;
			for (String sql : str.split("/\n")) {
				dataStr = sql.split("@");
				int trainingMenuId = Integer.valueOf(dataStr[0]);
				float mets = Float.valueOf(dataStr[2]);
				float trainingCategoryId = Float.valueOf(dataStr[3]);
				// insertTrainingMenu(trainingMenuId, dataStr[1], mets,
				// (int) trainingCategoryId);
				list.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ファイルから文字列を読み込みます。
	 * 
	 */
	private String readFile(InputStream is) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is));

			StringBuilder sb = new StringBuilder();
			String str;
			while ((str = br.readLine()) != null) {
				sb.append(str + "\n");
			}
			return sb.toString();
		} finally {
			if (br != null)
				br.close();
		}
	}

}
