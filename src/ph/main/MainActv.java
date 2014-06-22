package ph.main;

import java.io.File;
import java.util.Arrays;

import ph.listeners.button.BO_CL;
import ph.listeners.button.BO_TL;
import ph.utils.CONS;
import ph.utils.Methods_dlg;
import ph.utils.Tags;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActv extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actv_main);

		// Log
		String msg_Log = "onCreate()";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }
		
//		_debug();
		
    }


    private void _debug() {
		// TODO Auto-generated method stub
		_debug_D_3_V_1_1_P_1();
	}


	private void 
	_debug_D_3_V_1_1_P_1() {
		// TODO Auto-generated method stub
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
		
		File[] files = dpath_Pictures.listFiles();
		
		for (File file : files) {
			
			// Log
			String msg_Log = "file.getName() = " + file.getName();
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		// Log
		String msg_Log = "Sorting...";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		Arrays.sort(files);
		
		for (File file : files) {
			
			// Log
			msg_Log = "file.getName() = " + file.getName();
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}

	}//_debug_D_3_V_1_1_P_1()


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_actv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
            
        } else if (id == R.id.action_pref) {
        	
//        	// debug
//			String msg_Toast = "Search!";
//			Toast.makeText(this, msg_Toast, Toast.LENGTH_SHORT).show();
        	
        	Methods_dlg.dlg_Pref_Period(this);
			
			return true;
        	
        }
        
        return super.onOptionsItemSelected(item);
    }

	private void
	_onStart__Set_Initial_Image() {
		// TODO Auto-generated method stub
		// Log
		String msg_Log = "_onStart__Set_Initial_Image()";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// list in "Pictures"

		////////////////////////////////
		File dpath_Pictures = new File(
						CONS.Paths.dpath_Storage_Internal,
						CONS.Paths.dname_Pictures);
		
		if (!dpath_Pictures.exists()) {
			
			// Log
			msg_Log = String.format(
							"Dir => not exist: %s",
							dpath_Pictures.getAbsolutePath());
			
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		}		
		
		////////////////////////////////

		// set: image

		////////////////////////////////
//		File[] fpath_Pictures = dpath_Pictures.listFiles();
		
		/******************************
		validate
		 ******************************/
		if (CONS.MainActv.image_Files == null) {
			
//			File dpath_Pictures = new File(
//					CONS.Paths.dpath_Storage_Internal,
//					CONS.Paths.dname_Pictures);
//	
//			if (!dpath_Pictures.exists()) {
//				
//				// Log
//				String msg_Log = String.format(
//								"Dir => not exist: %s",
//								dpath_Pictures.getAbsolutePath());
//				
//				Log.d("MainActv.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", msg_Log);
//				
//				return;
//				
//			}		
			
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
		msg_Log = "Preparing => CONS.MainActv.bm";
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
		
		
		CONS.MainActv.bm_Modified = _modify_Bitmap(CONS.MainActv.bm, 90, 100);
//		Bitmap bm_Modified = _modify_Bitmap(bm, 90, 100);

		// free bm
		if (CONS.MainActv.bm != null) {
		    CONS.MainActv.bm.recycle();
		    CONS.MainActv.bm = null;


		}
		
		CONS.MainActv.iv_MainActv = (ImageView) this.findViewById(R.id.actv_main_iv);
//		ImageView iv = (ImageView) this.findViewById(R.id.actv_main_iv);
		
		CONS.MainActv.iv_MainActv.setImageBitmap(CONS.MainActv.bm_Modified);		
//		CONS.MainActv.iv_MainActv.setImageBitmap(bm_Modified);		
		
	}//_onStart__Set_Initial_Image()

	private Bitmap
	_modify_Bitmap
	(Bitmap bm, int numerator, int denominator) {
		// TODO Auto-generated method stub
		// Log
		String msg_Log = "_modify_Bitmap()";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		
		int bm_w = bm.getWidth();
		int bm_h = bm.getHeight();
		
		Display disp=((WindowManager)getSystemService(
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

    
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_actv, container, false);
            return rootView;
        }
    }


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		// Log
		String msg_Log = "onStart()";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		super.onStart();
		
		////////////////////////////////

		// views

		////////////////////////////////
		_onStart__Set_Views();
		
		_onStart__Set_Initial_Image();
		
		////////////////////////////////

		// listeners

		////////////////////////////////
		_onStart__Set_Listeners();
		
		
		
	}


	private void _onStart__Set_Views() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// Radio buttons

		////////////////////////////////
		////////////////////////////////

		// get: num of files

		////////////////////////////////
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
		
		int numOfFiles = CONS.MainActv.image_Files.length;
		
		////////////////////////////////

		// Radio buttons

		////////////////////////////////
		
		RadioGroup rg = (RadioGroup) findViewById(R.id.actv_main_rg);
		
		int numOfRadioButtons = numOfFiles;
		
		for (int i = 0; i < numOfRadioButtons; i++) {
			
			RadioButton rb = new RadioButton(this);
			
			rb.setText(String.valueOf(i));
//			rb.setText("RB " + i);
			
			rb.setTextSize(25);
			
			rg.addView(rb);
			
		}
		
		RadioButton rb = (RadioButton) rg.getChildAt(0);
		
		rb.setChecked(true);
		
	}


	private void _onStart__Set_Listeners() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// get: views

		////////////////////////////////
		
		Button bt_Start = (Button) this.findViewById(R.id.actv_main_bt_1);
		Button bt_Stop = (Button) this.findViewById(R.id.actv_main_bt_2);
		
		////////////////////////////////

		// tag

		////////////////////////////////
		bt_Start.setTag(Tags.ButtonTags.ACTVMAIN_BT_START);
		bt_Stop.setTag(Tags.ButtonTags.ACTVMAIN_BT_STOP);
		
		////////////////////////////////

		// BO_TL

		////////////////////////////////
		bt_Start.setOnTouchListener(new BO_TL(this));
		bt_Stop.setOnTouchListener(new BO_TL(this));

		////////////////////////////////
		
		// BO_CL
		
		////////////////////////////////
		bt_Start.setOnClickListener(new BO_CL(this));
		bt_Stop.setOnClickListener(new BO_CL(this));
		
	}


	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		// Log
		String msg_Log = "onRestart()";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		super.onRestart();
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// Log
		String msg_Log = "onResume()";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		super.onResume();
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		// Log
		String msg_Log = "onPause()";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		////////////////////////////////

		// bitmap => free

		////////////////////////////////
		// free
		if (CONS.MainActv.bm_Modified != null) {
			
		    CONS.MainActv.bm_Modified.recycle();
		    CONS.MainActv.bm_Modified = null;

		    // Log
			msg_Log = "CONS.MainActv.bm_Modified => freed";
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}

		//test
		CONS.MainActv.iv_MainActv.setImageDrawable(null);
		
		//test
		System.gc();
		
		// Log
		msg_Log = "System.gc() => called";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		
		// Log
		msg_Log = "iv_MainActv => set null";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		
		super.onPause();
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		// Log
		String msg_Log = "onStop()";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		//test
		CONS.MainActv.iv_MainActv.setImageDrawable(null);
		
		//test
		System.gc();
		
		// Log
		msg_Log = "System.gc() => called";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		
		super.onStop();
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// Log
		String msg_Log = "onDestroy()";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		super.onDestroy();
	}

}
