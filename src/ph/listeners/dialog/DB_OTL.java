package ph.listeners.dialog;

import ph.utils.Tags;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DB_OTL implements OnTouchListener {

	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;
	Dialog dlg;
	
	public DB_OTL(Activity actv, Dialog dlg) {
		//
		this.actv = actv;
		this.dlg = dlg;
	}
	
	public DB_OTL(Activity actv) {
		//
		this.actv = actv;
	}

//	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		Tags.DialogButtonTags tag_name = (Tags.DialogButtonTags) v.getTag();
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
				switch (tag_name) {
				
				case DLG_GENERIC_DISMISS:
				case dlg_generic_dismiss_third_dialog:
				case dlg_generic_dismiss_second_dialog:
					
				case DLG_ACTIONBAR_MAINACTV_BT_OK:
					
					//
					v.setBackgroundColor(Color.GRAY);
					
					break;
				}//switch (tag_name)
		
			break;//case MotionEvent.ACTION_DOWN:
			
		case MotionEvent.ACTION_UP:
			switch (tag_name) {

			case DLG_GENERIC_DISMISS:
			case dlg_generic_dismiss_second_dialog:
			case dlg_generic_dismiss_third_dialog:

			case DLG_ACTIONBAR_MAINACTV_BT_OK:
				//
					v.setBackgroundColor(Color.WHITE);
					
					break;
				}//switch (tag_name)
		
			break;//case MotionEvent.ACTION_UP:
		
		}//switch (event.getActionMasked())
		
		return false;
		
	}//public boolean onTouch(View v, MotionEvent event)

}//public class DB_OTL implements OnTouchListener
