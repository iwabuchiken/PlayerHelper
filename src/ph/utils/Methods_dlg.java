package ph.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ph.listeners.dialog.DB_OCL;
import ph.listeners.dialog.DB_OTL;
import ph.main.R;
//import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Methods_dlg {

	public static void dlg_Pref_Period(Activity actv) {
		// TODO Auto-generated method stub
		
		Dialog dlg = Methods_dlg.dlg_Tmpl_OkCancel_simple(
							actv, R.layout.dlg_actionbar_mainactv, 
							R.string.dlg_actionbar_mainactv_title, 
							
							R.id.dlg_actionbar_mainactv_bt_ok, 
							R.id.dlg_actionbar_mainactv_bt_cancel,
							Tags.DialogButtonTags.DLG_ACTIONBAR_MAINACTV_BT_OK, 
							Tags.DialogButtonTags.DLG_GENERIC_DISMISS);
		
		dlg.show();
		
	}

	public static
	Dialog dlg_Tmpl_OkCancel_simple
		(Activity actv, int layoutId, int titleStringId,
			int okButtonId, int cancelButtonId,
			Tags.DialogButtonTags okTag, Tags.DialogButtonTags cancelTag
			
			) {
		/****************************
		* Steps
		* 1. Set up
		* 2. Add listeners => OnTouch
		* 3. Add listeners => OnClick
		****************************/
		
		// 
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(layoutId);
		
		// Title
		dlg.setTitle(titleStringId);
		
		/****************************
		* 2. Add listeners => OnTouch
		****************************/
		//
		Button bt_Ok = (Button) dlg.findViewById(okButtonId);
		Button bt_Cancel = (Button) dlg.findViewById(cancelButtonId);
		
		//
		bt_Ok.setTag(okTag);
		bt_Cancel.setTag(cancelTag);
		
		//
		bt_Ok.setOnTouchListener(new DB_OTL(actv, dlg));
		bt_Cancel.setOnTouchListener(new DB_OTL(actv, dlg));
		
		/****************************
		* 3. Add listeners => OnClick
		****************************/
		//
		bt_Ok.setOnClickListener(new DB_OCL(actv, dlg));
		bt_Cancel.setOnClickListener(new DB_OCL(actv, dlg));
		
		//
		//dlg2.show();
		
		return dlg;
	
	}//public static Dialog dlg_template_okCancel_SecondDialog()

}//public class Methods_dialog
