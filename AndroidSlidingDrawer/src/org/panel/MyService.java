package org.panel;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.google.android.maps.GeoPoint;
/** 
* Clase que se ocupa de la comunicacion con la pulsera.  
* @author Jon Arribas
* @author Javier Martin
* @version 1.0, 13/06/2012
*/
public class MyService extends Service {

	private Timer timer = new Timer();
	private static final long UPDATE_INTERVAL = 1000;
	public static ServiceUpdateUIListener UI_UPDATE_LISTENER;
	int la = 43269612,lo = -2496943;
	ComunicacionFacade fac = new ComunicacionFacade(1);
	
	GeoPoint g_point = new GeoPoint(35410000, 139460000);
	
	public static void setUpdateListener(ServiceUpdateUIListener l) {
		UI_UPDATE_LISTENER = l;
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("", "dentro de onCreate de service");
		//startServer();
		fac.startServer();
		_startService();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		fac.stopServer();
		_shutdownService();
	}
	/*
	 * Inicio de la tarea para refrescar la posicion.
	 */
	private void _startService() {
		timer.scheduleAtFixedRate(
			new TimerTask() {
				public void run() {
					
					//fac.startServer();
					Posicion pos= fac.updateCoordenadas();
					la=pos.getLat();
					lo=pos.getLon();
					
					//la = la - 5000;
					//lo = lo + 5000;	
					Log.d("Lat", Integer.toString(la));
					Log.d("Lon", Integer.toString(lo));
					//updateCoordenadas();
					Log.d("", "service running");
					handler.sendEmptyMessage(0);
				}
			},
			0,
			UPDATE_INTERVAL);
	}
	
	private void _shutdownService() {
		if (timer != null) timer.cancel();
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			UI_UPDATE_LISTENER.update(la,lo);//g_point
		}
	};

}
