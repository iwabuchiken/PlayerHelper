package ph.utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import ph.listeners.DL;
import ph.main.R;
import ph.services.Service_SlideShow;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

// Apache


// REF=> http://commons.apache.org/net/download_net.cgi
//REF=> http://www.searchman.info/tips/2640.html

//import org.apache.commons.net.ftp.FTPReply;

public class Methods {

	static int counter;		// Used => sortFileList()
	
	
	/****************************************
	 * Vars
	 ****************************************/
	public static final int vibLength_click = 35;

	static int tempRecordNum = 20;

	/****************************************
	 * Methods
	 ****************************************/
	public static void sort_list_files(File[] files) {
		// REF=> http://android-coding.blogspot.jp/2011/10/sort-file-list-in-order-by-implementing.html
		/****************************
		 * 1. Prep => Comparator
		 * 2. Sort
			****************************/
		
		/****************************
		 * 1. Prep => Comparator
			****************************/
		Comparator<? super File> filecomparator = new Comparator<File>(){
			
			public int compare(File file1, File file2) {
				/****************************
				 * 1. Prep => Directory
				 * 2. Calculate
				 * 3. Return
					****************************/
				
				int pad1=0;
				int pad2=0;
				
				if(file1.isDirectory())pad1=-65536;
				if(file2.isDirectory())pad2=-65536;
				
				int res = pad2-pad1+file1.getName().compareToIgnoreCase(file2.getName());
				
				return res;
			} 
		 };//Comparator<? super File> filecomparator = new Comparator<File>()
		 
		/****************************
		 * 2. Sort
			****************************/
		Arrays.sort(files, filecomparator);

	}//public static void sort_list_files(File[] files)

	public static boolean
	clearPref (Activity actv,String prefName) {

		SharedPreferences prefs = 
						actv.getSharedPreferences(
								prefName,
								Context.MODE_PRIVATE);
		
		/****************************
		* 2. Get editor
		****************************/
		SharedPreferences.Editor editor = prefs.edit();
		
		/****************************
		* 3. Clear
		****************************/
		try {
		
			editor.clear();
			editor.commit();
			
			// Log
			Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Prefs cleared");
			
			return true;
		
		} catch (Exception e) {
		
			// Log
			Log.e("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Excption: " + e.toString());
			
			return false;
		}

	}//public static boolean clear_prefs_current_path(Activity actv, Strin newPath)

	
	public static void confirm_quit(Activity actv, int keyCode) {
		
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			
			AlertDialog.Builder dialog=new AlertDialog.Builder(actv);
			
			dialog.setTitle(actv.getString(R.string.generic_tv_confirm));
	        dialog.setMessage(actv.getString(R.string.generic_message_quit));
	        
	        dialog.setPositiveButton(
	        				actv.getString(R.string.generic_bt_quit),
	        				new DL(actv, dialog, 0));
	        
	        dialog.setNegativeButton(
	        				actv.getString(R.string.generic_bt_cancel),
	        				new DL(actv, dialog, 1));
	        
	        dialog.create();
	        dialog.show();
			
		}//if (keyCode==KeyEvent.KEYCODE_BACK)
		
	}//public static void confirm_quit(Activity actv, int keyCode)

	/****************************
	 * deleteDirectory(File target)()
	 * 
	 * 1. REF=> http://www.rgagnon.com/javadetails/java-0483.html
		****************************/
	public static boolean deleteDirectory(File target) {
		
		if(target.exists()) {
			//
			File[] files = target.listFiles();
			
			//
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					
					deleteDirectory(files[i]);
					
				} else {//if (files[i].isDirectory())
					
					String path = files[i].getAbsolutePath();
					
					files[i].delete();
					
					// Log
					Log.d("Methods.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", "Removed => " + path);
					
					
				}//if (files[i].isDirectory())
				
			}//for (int i = 0; i < files.length; i++)
			
		}//if(target.exists())
		
		return (target.delete());
	}//public static boolean deleteDirectory(File target)

	public static long getMillSeconds(int year, int month, int date) {
		// Calendar
		Calendar cal = Calendar.getInstance();
		
		// Set time
		cal.set(year, month, date);
		
		// Date
		Date d = cal.getTime();
		
		return d.getTime();
		
	}//private long getMillSeconds(int year, int month, int date)

	/****************************************
	 *	getMillSeconds_now()
	 * 
	 * <Caller> 
	 * 1. ButtonOnClickListener # case main_bt_start
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static long getMillSeconds_now() {
		
		Calendar cal = Calendar.getInstance();
		
		return cal.getTime().getTime();
		
	}//private long getMillSeconds_now(int year, int month, int date)

	public static String get_TimeLabel(long millSec) {
		
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
		 
		return sdf1.format(new Date(millSec));
		
	}//public static String get_TimeLabel(long millSec)

	/*********************************
	 * <Notes>
	 * 1. File names => Sorted alphabetico-ascendantly
	 * 
	 * @return
	 * null		=> 1. File "dpath" doesn't exist<br>
	 * 				2. listFiles() => null returned<br>
	 *********************************/
	public static List<String> get_FileList(File dpath) {
		/*********************************
		 * 1. Directory exists?
		 * 2. Build list
		 * 2-1. Sort list
		 * 
		 * 3. Return
		 *********************************/
		////////////////////////////////

		// Directory exists?

		////////////////////////////////
				
		if (!dpath.exists()) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Dir doesn't exist");
			
			return null;
			
		} else {//if (!dpath.exists() == condition)
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Dir exists: " + dpath.getAbsolutePath());
			
		}//if (!dpath.exists() == condition)

		////////////////////////////////

		// Get: File list

		////////////////////////////////
		
		List<String> list_Dir = new ArrayList<String>();
		
		File[] files_list = dpath.listFiles();
		
		if (files_list == null) {
			
			// Log
			String msg_log = "listFiles() => returned null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return null;
			
		}

		////////////////////////////////

		// Sort list

		////////////////////////////////
		
		Methods.sort_list_files(files_list);
		
		for (File f : files_list) {
			
			list_Dir.add(f.getName());
			
		}//for (File f : files_list)
		
		/*********************************
		 * 3. Return
		 *********************************/
		return list_Dir;
		
	}//public static List<String> get_file_list(File dpath)

	public static String convert_intSec2Digits(int t) {
		
		int sec = t % 60;
		
		if (t / 60 < 1) {
			
//			return "00:00:" + String.valueOf(sec);
			return "00:00:" + Methods.convert_sec2digits(sec, 2);
			
		}//if (t / 60 < 1)
		
//		int min = (t - sec) % 60;
		int min = ((t - sec) % (60 * 60)) / 60;
		
		if ((t - sec) / (60 * 60) < 1) {
			
//			return "00:" + String.valueOf(min) + ":" + String.valueOf(sec);
			return "00:"
				+ Methods.convert_sec2digits(min, 2) + ":"
				+ Methods.convert_sec2digits(sec, 2);
			
		}//if (variable == condition)
		

				
//		return String.valueOf(hour) + ":"
//				+ String.valueOf(min) + ":"
//				+ String.valueOf(sec);

		return Methods.convert_sec2digits(min, 2) + ":"
		+ Methods.convert_sec2digits(min, 2) + ":"
		+ Methods.convert_sec2digits(sec, 2);

		
	}//public static String convert_intSec2Digits(int time)

	/***************************************
	 * 20130320_120437<br>
	 * @param t ... Value in seconds, <i>not</i> in mill seconds
	 ***************************************/
	public static String convert_intSec2Digits_lessThanHour(int t) {
		
		int sec = t % 60;
		
		if (t / 60 < 1) {
			
//			return "00:00:" + String.valueOf(sec);
//			return "00:00:" + Methods.convert_sec2digits(sec, 2);
			return "00:" + Methods.convert_sec2digits(sec, 2);
			
		}//if (t / 60 < 1)
		
//		int min = (t - sec) % 60;
		int min = ((t - sec) % (60 * 60)) / 60;
		
		return Methods.convert_sec2digits(min, 2) + ":"
			+ Methods.convert_sec2digits(sec, 2);
			
	}//public static String convert_intSec2Digits(int time)

	private static String convert_sec2digits(int sec, int i) {
		
		int current_len = String.valueOf(sec).length();
		
		if (current_len < i) {
			
			StringBuilder sb = new StringBuilder();
			
			for (int j = 0; j < i - current_len; j++) {
				
				sb.append("0");
			}
			
			sb.append(String.valueOf(sec));
			
			return sb.toString();
			
		}//if (current_len == condition)
		
		return String.valueOf(sec);
		
	}//private static String convert_sec2digits(int sec, int i)

	public static int getArrayIndex(String[] targetArray, String targetString) {
		int index = -1;
		
		for (int i = 0; i < targetArray.length; i++) {
			
			if (targetArray[i].equals(targetString)) {
				
				index = i;
				
				break;
				
			}//if (targetArray[i] == )
			
		}//for (int i = 0; i < targetArray.length; i++)
		
		return index;
	}//public static int getArrayIndex(String[] targetArray, String targetString)

	/******************************
		@return true => pref set
	 ******************************/
	public static boolean set_Pref_Int
	(Activity actv, String pref_name, String pref_key, int value) {
		SharedPreferences prefs = 
				actv.getSharedPreferences(pref_name, Context.MODE_PRIVATE);

		/****************************
		 * 2. Get editor
			****************************/
		SharedPreferences.Editor editor = prefs.edit();

		/****************************
		 * 3. Set value
			****************************/
		editor.putInt(pref_key, value);
		
		try {
			editor.commit();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Excption: " + e.toString());
			
			return false;
		}

	}//public static boolean set_pref(String pref_name, String value)

	public static int getPref_Int
	(Activity actv, String pref_name, String pref_key, int defValue) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(pref_name, Context.MODE_PRIVATE);

		/****************************
		 * Return
			****************************/
		return prefs.getInt(pref_key, defValue);

	}//public static boolean set_pref(String pref_name, String value)
	
	public static long getPref_Long
	(Activity actv, String pref_name, String pref_key, long defValue) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
		
		/****************************
		 * Return
		 ****************************/
		return prefs.getLong(pref_key, defValue);
		
	}//public static boolean set_pref(String pref_name, String value)

	public static boolean db_Backup(Activity actv)
	{
		/****************************
		 * 1. Prep => File names
		 * 2. Prep => Files
		 * 2-2. Folder exists?
		 * 
		 * 2-3. Dst folder => Files within the limit?
		 * 3. Copy
			****************************/
		String time_label = Methods.get_TimeLabel(Methods.getMillSeconds_now());
		
		String db_Src = StringUtils.join(
					new String[]{
							actv.getDatabasePath(CONS.DB.dbName).getPath()},
//							CONS.fname_db},
					File.separator);
		
		String db_Dst_Folder = StringUtils.join(
					new String[]{
							CONS.DB.dPath_dbFile_backup,
							CONS.DB.fname_DB_Backup_Trunk},
//							CONS.dpath_db_backup,
//							CONS.fname_db_backup_trunk},
					File.separator);
		
		String db_Dst = db_Dst_Folder + "_"
				+ time_label
//				+ MainActv.fileName_db_backup_ext;
				+ CONS.DB.fname_DB_Backup_ext;
//		+ CONS.fname_db_backup_ext;
//				+ MainActv.fname_db_backup_trunk;

		// Log
		String msg_log = "db_Src = " + db_Src
						+ " / "
						+ "db_Dst = " + db_Dst;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_log);
		
		/****************************
		 * 2. Prep => Files
			****************************/
		File src = new File(db_Src);
		File dst = new File(db_Dst);
		
		/****************************
		 * 2-2. Folder exists?
			****************************/
		File db_Backup = new File(CONS.DB.dPath_dbFile_backup);
//		File db_backup = new File(CONS.dpath_db_backup);
		
		if (!db_Backup.exists()) {
			
			try {
				db_Backup.mkdir();
				
				// Log
				Log.d("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "Folder created: " + db_Backup.getAbsolutePath());
			} catch (Exception e) {
				
				// Log
				Log.e("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Create folder => Failed");
				
				return false;
				
			}
			
		} else {//if (!db_backup.exists())
			
			// Log
			Log.i("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Folder exists: ");
			
		}//if (!db_backup.exists())
		
		/*********************************
		 * 2-3. Dst folder => Files within the limit?
		 *********************************/
		File[] files_dst_folder = new File(CONS.DB.dPath_dbFile_backup).listFiles();
//		File[] files_dst_folder = new File(CONS.dpath_db_backup).listFiles();
		
		int num_of_files = files_dst_folder.length;
		
		// Log
		Log.i("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "num of backup files = " + num_of_files);
		
		/****************************
		 * 3. Copy
			****************************/
		try {
			FileChannel iChannel = new FileInputStream(src).getChannel();
			FileChannel oChannel = new FileOutputStream(dst).getChannel();
			iChannel.transferTo(0, iChannel.size(), oChannel);
			iChannel.close();
			oChannel.close();
			
			// Log
			Log.i("ThumbnailActivity.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DB file copied");
			
			// debug
			Toast.makeText(actv, "DB backup => Done", Toast.LENGTH_LONG).show();

		} catch (FileNotFoundException e) {
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			return false;
			
		} catch (IOException e) {
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			return false;
			
		}//try

		return true;
		
	}//public static boolean db_backup(Activity actv)

	public static String
	conv_CurrentPath_to_TableName(String currentPath)
	{
		String full = currentPath;
//		String full = CONS.Paths.dpath_Storage_Sdcard + CONS.Paths.dname_Base;
		
		////////////////////////////////

		// Get: raw strings

		////////////////////////////////
		String head = CONS.Paths.dpath_Storage_Sdcard;
		
		int len = head.length();
		
		String target = full.substring(len + 1);

//		// Log
//		String msg_log = "full = " + full
//						+ " // "
//						+ "target = " + target;
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_log);

		////////////////////////////////

		// Split: target

		////////////////////////////////
		// Log
		String msg_log = "File.separator = " + File.separator;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_log);
		
		String[] tokens = target.split(File.separator);
		
		////////////////////////////////

		// Build: table name

		////////////////////////////////
		if (tokens == null) {
			
			// Log
			msg_log = "Split => returned null";
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return null;
			
		} else if (tokens.length == 1) {
			
			return target;
			
		} else {

			return StringUtils.join(tokens, CONS.DB.jointString_TableName);
			
		}
		
	}//conv_CurrentPath_to_TableName(String currentPath)

	/******************************
		Format => yyyy/MM/dd hh:mm:ss.SSS
	 ******************************/
	public static String
	conv_MillSec_to_TimeLabel(long millSec)
	{
		//REF http://stackoverflow.com/questions/7953725/how-to-convert-milliseconds-to-date-format-in-android answered Oct 31 '11 at 12:59
		String dateFormat = CONS.Admin.format_Date;
//		String dateFormat = "yyyy/MM/dd hh:mm:ss.SSS";
		
		DateFormat formatter = new SimpleDateFormat(dateFormat);

		// Create a calendar object that will convert the date and time value in milliseconds to date. 
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTimeInMillis(millSec);
		
		return formatter.format(calendar.getTime());
		
	}//conv_MillSec_to_TimeLabel(long millSec)

	public static long
	conv_TimeLabel_to_MillSec(String timeLabel)
//	conv_MillSec_to_TimeLabel(long millSec)
	{
//		String input = "Sat Feb 17 2012";
		Date date;
		try {
			date = new SimpleDateFormat(
						CONS.Admin.format_Date, Locale.JAPAN).parse(timeLabel);
			
			return date.getTime();
//			long milliseconds = date.getTime();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			// Log
			String msg_Log = "Exception: " + e.toString();
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -1;
			
		}
		
//		Locale.ENGLISH).parse(input);
		
//		Date date = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH).parse(input);
//		long milliseconds = date.getTime();
		
	}//conv_TimeLabel_to_MillSec(String timeLabel)
	
	/******************************
		REF http://stackoverflow.com/questions/625433/how-to-convert-milliseconds-to-x-mins-x-seconds-in-java answered Mar 9 '09 at 10:01
	 ******************************/
	public static String
	conv_MillSec_to_ClockLabel(long millSec)
	{
		return String.format(
			Locale.JAPAN,
			CONS.Admin.format_Clock, 
//			"%02d:%02d", 
			TimeUnit.MILLISECONDS.toMinutes(millSec),
			TimeUnit.MILLISECONDS.toSeconds(millSec) - 
			TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millSec))
		);
		
	}//conv_MillSec_to_ClockLabel(long millSec)
	
	public static long
	conv_ClockLabel_to_MillSec(String clockLabel)
	{
		
		String[] tokens = clockLabel.split(":");
		
		/******************************
			Validate
		 ******************************/
		if (tokens == null || tokens.length != 2) {

			// Log
			String msg_Log = "Label format => unknown: " + clockLabel;
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -1;
			
		}
		
		/******************************
			Build: long number
		 ******************************/
		long mill_Min = Integer.parseInt(tokens[0]) * (60 * 1000);
		long mill_Sec = Integer.parseInt(tokens[1]) * (1000);
		
		return mill_Min + mill_Sec;
		
//		SimpleDateFormat formatter = 
//				new SimpleDateFormat("mm:ss"); // I assume d-M, you may refer to M-d for month-day instead.
////		new SimpleDateFormat("d-M-yyyy hh:mm"); // I assume d-M, you may refer to M-d for month-day instead.
//		Date date;
//		try {
//			
//			date = formatter.parse(clockLabel);
//			return date.getTime();
//			
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			// Log
//			String msg_Log = "Exception: " + e.toString();
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			return -1;
//			
//		} // You will need try/catch around this
//		long millis = date.getTime();
		
	}//conv_ClockLabel_to_MillSec(String clockLabel)

	public static String get_Pref_String
	(Activity actv, String pref_name,
			String pref_key, String defValue) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(
						pref_name, Context.MODE_PRIVATE);

		/****************************
		 * Return
			****************************/
		return prefs.getString(pref_key, defValue);

	}//public static String get_Pref_String
	
	public static int get_Pref_Int
	(Activity actv, String pref_name,
			String pref_key, int defValue) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(
						pref_name, Context.MODE_PRIVATE);
		
		/****************************
		 * Return
		 ****************************/
		return prefs.getInt(pref_key, defValue);
		
	}//public static String get_Pref_String
	
	public static boolean
	setPref_Long
	(Activity actv, String pName, String pKey, long value) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(pName, Context.MODE_PRIVATE);

		/****************************
		 * 2. Get editor
			****************************/
		SharedPreferences.Editor editor = prefs.edit();

		/****************************
		 * 3. Set value
			****************************/
		editor.putLong(pKey, value);
		
		try {
			
			editor.commit();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Excption: " + e.toString());
			
			return false;
			
		}

	}//public static boolean setPref_long(Activity actv, String pref_name, String pref_key, long value)
	
	public static boolean
	setPref_String
	(Activity actv, String pName, String pKey, String value) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(pName, Context.MODE_PRIVATE);
		
		/****************************
		 * 2. Get editor
		 ****************************/
		SharedPreferences.Editor editor = prefs.edit();
		
		/****************************
		 * 3. Set value
		 ****************************/
		editor.putString(pKey, value);
		
		try {
			
			editor.commit();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Excption: " + e.toString());
			
			return false;
			
		}
		
	}//public static boolean setPref_long(Activity actv, String pref_name, String pref_key, long value)

	public static void 
	start_Service(Activity actv) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// get: file list

		////////////////////////////////
		File dpath_Pictures = new File(
					CONS.Paths.dpath_Storage_Internal,
					CONS.Paths.dname_Pictures);
		
		if (!dpath_Pictures.exists()) {
			
			// Log
			String msg_Log = String.format(
							"Dir => not exist: %s",
							dpath_Pictures.getAbsolutePath());
			
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		}
		
		CONS.MainActv.image_Files = dpath_Pictures.listFiles();
		
		/******************************
			validate
		 ******************************/
		if (CONS.MainActv.image_Files == null 
				|| CONS.MainActv.image_Files.length < 1) {
			
			// Log
			String msg_Log = "image_Files => Null or no content";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			// debug
			Toast.makeText(actv, msg_Log, Toast.LENGTH_SHORT).show();
			
			return;
			
		}
		
		////////////////////////////////

		// sort: array

		////////////////////////////////
		Arrays.sort(CONS.MainActv.image_Files);

		////////////////////////////////

		// Activity: Main

		////////////////////////////////
		CONS.MainActv.mainActv = actv;
		
		////////////////////////////////
		
		// view: set first image
		
		////////////////////////////////
		Methods.set_Initial_Image(actv);
		
		////////////////////////////////

		// view: set first radio button

		////////////////////////////////
		Methods.set_RadioButton(0);
		
		////////////////////////////////

		// start: intent

		////////////////////////////////
		Intent i = new Intent((Context) actv, Service_SlideShow.class);
		
		actv.startService(i);
		
		// Log
		String msg_Log = "Service => started";
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}//start_Service(Activity actv)

	private static void 
	set_Initial_Image(Activity actv) {
		// TODO Auto-generated method stub
		
		if (CONS.MainActv.image_Files == null) {
			
			File dpath_Pictures = new File(
					CONS.Paths.dpath_Storage_Internal,
					CONS.Paths.dname_Pictures);
	
			if (!dpath_Pictures.exists()) {
				
				// Log
				String msg_Log = String.format(
								"Dir => not exist: %s",
								dpath_Pictures.getAbsolutePath());
				
				Log.d("MainActv.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);
				
				return;
				
			}		
			
			CONS.MainActv.image_Files = dpath_Pictures.listFiles();
			
			Arrays.sort(CONS.MainActv.image_Files);
			
		}//if (CONS.MainActv.image_Files == null)
		
		
		
		File fpath_Target = CONS.MainActv.image_Files[0];
				
//		File fpath_Target = new File(
//								dpath_Pictures, 
//								"IMG_20140615_212753.jpg");
//		File fpath_Target = new File(dpath_Pictures, "2013-12-07_20-01-34_990.jpg");
//		File fpath_Target = fpath_Pictures[0];
		
		// Log
		String msg_Log = "Preparing => CONS.MainActv.bm";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		//test
		if (CONS.MainActv.bm != null) {
			
			CONS.MainActv.bm = null;
			
			// Log
			msg_Log = "CONS.MainActv.bm => nulled";
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {
			
			// Log
			msg_Log = "CONS.MainActv.bm => null";
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		//test
		//REF https://groups.google.com/forum/#!topic/android-group-japan/-kD-TSUFQfU ohisama
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inSampleSize=4;
		
		CONS.MainActv.bm = BitmapFactory.decodeFile(
								fpath_Target.getAbsolutePath(), opt);
//		CONS.MainActv.bm = BitmapFactory.decodeFile(fpath_Target.getAbsolutePath());
//		Bitmap bm = BitmapFactory.decodeFile(fpath_Target.getAbsolutePath());
		
		// Log
		msg_Log = "CONS.MainActv.bm => initialized";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		
		CONS.MainActv.bm_Modified = Methods.modify_Bitmap(CONS.MainActv.bm, 90, 100);
//		Bitmap bm_Modified = _modify_Bitmap(bm, 90, 100);

		// free bm
		if (CONS.MainActv.bm != null) {
		    CONS.MainActv.bm.recycle();
		    CONS.MainActv.bm = null;


		}
		
		CONS.MainActv.iv_MainActv = (ImageView) actv.findViewById(R.id.actv_main_iv);
//		ImageView iv = (ImageView) this.findViewById(R.id.actv_main_iv);
		
		CONS.MainActv.iv_MainActv.setImageBitmap(CONS.MainActv.bm_Modified);		
//		CONS.MainActv.iv_MainActv.setImageBitmap(bm_Modified);		
		
	}//set_Initial_Image(Activity actv)

	public static void stop_Service(Activity actv) {
		// TODO Auto-generated method stub
		Intent i = new Intent((Context) actv, Service_SlideShow.class);
		
		actv.stopService(i);
		
		// Log
		String msg_Log = "Service => stopped";
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

	}

	public static void 
	show_BMP(int counter) {
		// TODO Auto-generated method stub
		
		/******************************
			validate
		 ******************************/
		if (CONS.MainActv.image_Files == null) {
			
			File dpath_Pictures = new File(
					CONS.Paths.dpath_Storage_Internal,
					CONS.Paths.dname_Pictures);
	
			if (!dpath_Pictures.exists()) {
				
				// Log
				String msg_Log = String.format(
								"Dir => not exist: %s",
								dpath_Pictures.getAbsolutePath());
				
				Log.d("MainActv.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);
				
				return;
				
			}		
			
			CONS.MainActv.image_Files = dpath_Pictures.listFiles();
			
			Arrays.sort(CONS.MainActv.image_Files);
			
		}//if (CONS.MainActv.image_Files == null)
		
		int resi = counter % CONS.MainActv.image_Files.length;
//		int resi = counter % 3;
		
		////////////////////////////////

		// reset: Bitmap

		////////////////////////////////
		if (CONS.MainActv.bm != null) {
		    CONS.MainActv.bm.recycle();
		    CONS.MainActv.bm = null;


		}

		////////////////////////////////

		// set: Bitmap

		////////////////////////////////
		// Log
		String msg_Log = "Calling => BitmapFactory.decodeFile()";
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inSampleSize=4;
		
//		Bitmap bm = BitmapFactory.decodeFile(
		CONS.MainActv.bm = BitmapFactory.decodeFile(
							CONS.MainActv.image_Files[resi].getAbsolutePath(),
							opt);
//		CONS.MainActv.image_Files[resi].getAbsolutePath());
		
		CONS.MainActv.bm_Modified = Methods.modify_Bitmap(CONS.MainActv.bm, 90, 100);
//		Bitmap bm_Modified = Methods.modify_Bitmap(bm, 90, 100);

		// free bm
		if (CONS.MainActv.bm != null) {
		    CONS.MainActv.bm.recycle();
		    CONS.MainActv.bm = null;


		}
		
		if (CONS.MainActv.iv_MainActv == null) {
			
			CONS.MainActv.iv_MainActv = 
					(ImageView) CONS.MainActv.mainActv.findViewById(
															R.id.actv_main_iv);
			
		}
//		ImageView iv = (ImageView) this.findViewById(R.id.actv_main_iv);
		
		CONS.MainActv.iv_MainActv.setImageBitmap(CONS.MainActv.bm_Modified);
//		CONS.MainActv.iv_MainActv.setImageBitmap(bm_Modified);
		
		
		
		
	}//show_BMP(int counter)

	private static Bitmap
	modify_Bitmap
	(Bitmap bm, int numerator, int denominator) {
		// TODO Auto-generated method stub
		int bm_w = bm.getWidth();
		int bm_h = bm.getHeight();
		
		Display disp=((WindowManager)
				CONS.MainActv.mainActv.getSystemService(
						Context.WINDOW_SERVICE)).getDefaultDisplay();
		
		Point point = new Point();
		
		disp.getSize(point);
		
		int disp_Height = point.y;
		int disp_Width = point.x;
		
		int w;
		int h;
		
		if (bm_w > bm_h) {
			
			h = disp_Height;
			
			w = (int) (h * ((float) bm_w / bm_h));
			
		} else {//if (bm_w > bm_h)
			
			w = disp_Width;
			
			h = (int) (w * ((float) bm_h / bm_w));
			
		}//if (bm_w > bm_h)

		// resize
		w = w * numerator / denominator;
		h = h * numerator / denominator;
		
		return Bitmap.createScaledBitmap(bm, w, h, false);
		
	}//private Bitmap _modify_Bitmap(Bitmap bm)

	public static void 
	set_Pref_Period
	(Activity actv, Dialog dlg) {
		// TODO Auto-generated method stub
		
		RadioGroup rg = 
				(RadioGroup) dlg.findViewById(R.id.dlg_actionbar_mainactv_rg);
		
		int tmp_i = rg.getCheckedRadioButtonId();
		
		RadioButton rb_Target = (RadioButton) dlg.findViewById(tmp_i);
		
		/******************************
			validate
		 ******************************/
		String input_Number = rb_Target.getText().toString();
		
		if (!Methods.is_Numeric(input_Number)) {
			
			// Log
			String msg_Log = "Input => not numeric: " + input_Number;
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			// debug
			Toast.makeText(actv, msg_Log, Toast.LENGTH_SHORT).show();
			
			return;
			
		}
		
		////////////////////////////////

		// set: pref

		////////////////////////////////
		boolean res = Methods.set_Pref_Int(
							actv, 
							CONS.Pref.pname_MainActv, 
							CONS.Pref.pkey_TaskPeriod, 
							Integer.parseInt(input_Number));
		
		/******************************
			validate
		 ******************************/
		if (res == false) {
			
			// Log
			String msg_Log = "Set pref => not done: " + input_Number;
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			// debug
			Toast.makeText(actv, msg_Log, Toast.LENGTH_SHORT).show();
			
			return;
			
		} else {
			
			// Log
			String msg_Log = "Set pref => done: " + input_Number;
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			// debug
			Toast.makeText(actv, msg_Log, Toast.LENGTH_SHORT).show();

		}
		
		////////////////////////////////

		// dialog: dismiss

		////////////////////////////////
		dlg.dismiss();
		
//		// debug
//		String msg_Toast = "Chosen number => " + rb_Target.getText().toString();
//		Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
		
	}//set_Pref_Period

	public static boolean is_Numeric(String num_string) {
		
//		return num_string.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+ ");
		return num_string.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

	}//public static boolean is_numeric(String num_string)

	public static void 
	set_RadioButton(int counter) {
		// TODO Auto-generated method stub
		
		/******************************
		validate
		 ******************************/
		if (CONS.MainActv.image_Files == null) {
			
			File dpath_Pictures = new File(
					CONS.Paths.dpath_Storage_Internal,
					CONS.Paths.dname_Pictures);
	
			if (!dpath_Pictures.exists()) {
				
				// Log
				String msg_Log = String.format(
								"Dir => not exist: %s",
								dpath_Pictures.getAbsolutePath());
				
				Log.d("MainActv.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);
				
				return;
				
			}		
			
			CONS.MainActv.image_Files = dpath_Pictures.listFiles();
			
			Arrays.sort(CONS.MainActv.image_Files);
			
		}//if (CONS.MainActv.image_Files == null)
		
		int resi = counter % CONS.MainActv.image_Files.length;
		
		////////////////////////////////

		// set: radio button

		////////////////////////////////
		/******************************
			validate
		 ******************************/
		if (CONS.MainActv.mainActv == null) {
			
			// Log
			String msg_Log = "CONS.MainActv.mainActv => null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);

			return;
			
		}
		
		RadioGroup rg = (RadioGroup) CONS.MainActv.mainActv
							.findViewById(R.id.actv_main_rg);
		
		RadioButton rb = (RadioButton) rg.getChildAt(resi);
		
		rb.setChecked(true);
		
	}//set_RadioButton(int counter)

	
}//public class Methods

