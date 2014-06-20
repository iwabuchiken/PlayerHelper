package ph.main;

import java.io.File;

import ph.utils.CONS;
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
import app.listeners.button.BO_CL;
import app.listeners.button.BO_TL;

public class MainActv extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actv_main);

//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }
    }


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
        }
        return super.onOptionsItemSelected(item);
    }

	private void
	_onStart__Set_Initial_Image() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// list in "Pictures"

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
		
		////////////////////////////////

		// set: image

		////////////////////////////////
//		File[] fpath_Pictures = dpath_Pictures.listFiles();
		
		File fpath_Target = new File(
								dpath_Pictures, 
								"IMG_20140615_212753.jpg");
//		File fpath_Target = new File(dpath_Pictures, "2013-12-07_20-01-34_990.jpg");
//		File fpath_Target = fpath_Pictures[0];
		
		Bitmap bm = BitmapFactory.decodeFile(fpath_Target.getAbsolutePath());
		
		Bitmap bm_Modified = _modify_Bitmap(bm, 90, 100);

		ImageView iv = (ImageView) this.findViewById(R.id.actv_main_iv);
		
		iv.setImageBitmap(bm_Modified);		
		
	}//_onStart__Set_Initial_Image()

	private Bitmap
	_modify_Bitmap
	(Bitmap bm, int numerator, int denominator) {
		// TODO Auto-generated method stub
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
		super.onStart();
		
		_onStart__Set_Initial_Image();
		
		////////////////////////////////

		// listeners

		////////////////////////////////
		_onStart__Set_Listeners();
		
		
		
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
		super.onRestart();
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
