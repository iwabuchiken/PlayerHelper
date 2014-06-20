package app.listeners.button;

import java.io.File;

import ph.services.Service_SlideShow;
import ph.utils.CONS;
import ph.utils.Methods;
import ph.utils.Tags;
import android.app.ListActivity;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class BO_CL implements OnClickListener {
	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;
	
	//
	int position;
	
	//
	ListView lv;
	
	public BO_CL(Activity actv) {
		//
		this.actv = actv;
		
		//
		CONS.Admin.vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		Tags.ButtonTags tag = (Tags.ButtonTags) v.getTag();

		CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
		
		switch (tag) {
		
		case ACTVMAIN_BT_START:
			
			case_MainActv_Bt_Start();
			
			break;
			
		case ACTVMAIN_BT_STOP:
			
			case_MainActv_Bt_Stop();
			
			break;
			
		}//switch (tag)
		
	}//public void onClick(View v)

	private void 
	case_MainActv_Bt_Start() {
		// TODO Auto-generated method stub
		
		// free
		if (CONS.MainActv.bm_Modified != null) {
			
		    CONS.MainActv.bm_Modified.recycle();
		    CONS.MainActv.bm_Modified = null;

		    // Log
			String msg_Log = "CONS.MainActv.bm_Modified => freed";
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			System.gc();
			
			// Log
			msg_Log = "System.gc() => called";
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			
		}
		
		
		Methods.start_Service(actv);
		
	}//case_MainActv_Bt_Start()
	
	private void 
	case_MainActv_Bt_Stop() {
		// TODO Auto-generated method stub
		
		Methods.stop_Service(actv);
		
//		Intent i = new Intent((Context) actv, Service_SlideShow.class);
//		
//		actv.stopService(i);
//		
//		// Log
//		String msg_Log = "Service => stopped";
//		Log.d("BO_CL.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		
	}//case_MainActv_Bt_Start()

}//public class ButtonOnClickListener implements OnClickListener

	