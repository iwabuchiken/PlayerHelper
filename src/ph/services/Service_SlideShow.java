package ph.services;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;











import ph.main.MainActv;
import ph.tasks.Task_SlideShow;
import ph.utils.CONS;
import ph.utils.Methods;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Service_SlideShow extends Service {

	Timer timer;

	int counter;
	
//	int period = 1000;	// in mill seconds
	int period;	// in mill seconds
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		// Log
		Log.d("Service_ShowProgress.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "onBind()");
		
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		// Log
		Log.d("Service_ShowProgress.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "onCreate()");
		
		_onCreate__Setup();
		
	}

	private void 
	_onCreate__Setup() {
		// TODO Auto-generated method stub
		
	}//_onCreate__Setup()

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		// Log
		Log.d("Service_ShowProgress.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "onDestroy()");

		/***************************************
		 * Cancel counting
		 ***************************************/
		timer.cancel();
		
		//test
		
		
		// Log
		Log.d("Service_ShowProgress.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "Timer => Cancelled");
		
	}//public void onDestroy()

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
//		super.onStart(intent, startId);
		
		// Log
		Log.d("Service_ShowProgress.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "onStart()");
		
		start_SlideShow();
//		startCount();

	}//public void onStart(Intent intent, int startId)

	private void start_SlideShow() {
		// TODO Auto-generated method stub
		
		final android.os.Handler handler = new android.os.Handler();
		
		counter = 0;
		
		//
		if (timer != null) {
			timer.cancel();
		}//if (timer != null)
		
		timer = new Timer();

		timer.schedule(new Task_SlideShow(handler), 0, period);
		
	}

	private void startCount() {
		
		final android.os.Handler handler = new android.os.Handler();
		
		counter = 0;
		
		//
		if (timer != null) {
			timer.cancel();
		}//if (timer != null)
		
		timer = new Timer();

		timer.schedule(new Task_SlideShow(handler), 0, period);
		
	}//private void startCount()

	//REF http://stackoverflow.com/questions/9999038/how-to-instantiate-android-service-with-an-constructor answered Apr 3 '12 at 18:33
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

//		Activity actv = new Activity();
		
		/******************************
			validate
		 ******************************/
		if (CONS.MainActv.mainActv == null) {
			
			// Log
			String msg_Log = "CONS.MainActv.mainActv => null";
			Log.d("Service_SlideShow.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);

			this.stopSelf();
			
			// Log
			msg_Log = "Service => stopped";
			Log.d("Service_SlideShow.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -1;
			
		}
		
//		CONS.MainActv.mainActv
		int tmp = Methods.get_Pref_Int(
							CONS.MainActv.mainActv,
//							actv, 
//							new Activity(), 
							CONS.Pref.pname_MainActv, 
							CONS.Pref.pkey_TaskPeriod, 
							CONS.Pref.dflt_IntExtra_value);
//		int tmp = intent.getIntExtra(
//				CONS.Intent.iKey_MainActv_TaskPeriod_SlideShow, 
//				CONS.Intent.dflt_IntExtra_value);
		
		if (tmp != CONS.Pref.dflt_IntExtra_value) {
//			if (tmp != CONS.Intent.dflt_IntExtra_value) {
			
			this.period = tmp * 1000;
//			this.period = tmp;
			
			// Log
			String msg_Log = "period set to => " + this.period;
			Log.d("Service_SlideShow.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {
			
			this.period = 1000;
			
			// Log
			String msg_Log = "period set to => " + this.period;
			Log.d("Service_SlideShow.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		return super.onStartCommand(intent, flags, startId);
	}
	

}//public class Service_ShowProgress extends Service
