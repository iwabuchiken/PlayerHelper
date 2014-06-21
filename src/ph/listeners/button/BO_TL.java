package ph.listeners.button;

import ph.utils.Tags;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class BO_TL implements OnTouchListener {

	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;
	
	public BO_TL(Activity actv) {
		//
		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
	}

//	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO ?��?��?��?��?��?��?��?��?��?��?��ꂽ?��?��?��\?��b?��h?��E?��X?��^?��u
		Tags.ButtonTags tag = (Tags.ButtonTags) v.getTag();
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN: //----------------------------
			
			switch (tag) {
			
			case ACTVMAIN_BT_START:
//				_onTouch_SetColors(v, Color.WHITE, Color.BLACK);
				_onTouch_SetColors(v, Color.BLACK, Color.GRAY);
				
				break;
				
			case ACTVMAIN_BT_STOP:
				
				_onTouch_SetColors(v, Color.RED, Color.GRAY);
				
				break;

				
			}//switch (tag)
			
			break;//case MotionEvent.ACTION_DOWN:

			
		case MotionEvent.ACTION_UP: //----------------------------
			switch (tag) {
				
			case ACTVMAIN_BT_START:

//				this._onTouch_SetColors(v, Color.BLACK, Color.WHITE);
				_onTouch_SetColors(v, Color.BLACK, Color.WHITE);
				
				break;
				
			case ACTVMAIN_BT_STOP:

				_onTouch_SetColors(v, Color.RED, Color.WHITE);
				
				break;
				
			}//switch (tag)
			
			break;//case MotionEvent.ACTION_UP:
		}//switch (event.getActionMasked())
		return false;
	}

	/******************************
		private void _onTouch_SetColors()
	 ******************************/
	private void
	_onTouch_SetColors
	(View v, int color_Text, int color_Back) {
		// TODO Auto-generated method stub
		
		v.setBackgroundColor(color_Back);
		
		TextView tv = (TextView) v;
		
		tv.setTextColor(color_Text);
		
	}

}//public class BO_TL implements OnTouchListener
