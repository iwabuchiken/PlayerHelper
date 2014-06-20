package ph.tasks;

import java.util.TimerTask;

import ph.utils.Methods;
import android.os.Handler;
import android.util.Log;

public class Task_SlideShow extends TimerTask {

	Handler handler;
	
	int counter;
	
	public Task_SlideShow(Handler handler) {
		// TODO Auto-generated constructor stub
		
		this.handler	= handler;
		
		counter = 0;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		handler.post(new Runnable(){

//						@Override
			public void run() {
				
				// Log
				String msg_Log = "counter = " + counter;
				Log.d("Task_Timer.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				counter ++;
				
				////////////////////////////////

				// show: image

				////////////////////////////////
				Methods.show_BMP(counter);
				
			}//public void run() // Runnable
			
		});//handler.post()
		
	}//public void run()
			

}
