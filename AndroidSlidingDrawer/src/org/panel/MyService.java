package org.panel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.maps.GeoPoint;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class MyService extends Service {

	private Timer timer = new Timer();
	private static final long UPDATE_INTERVAL = 1000;
	public static ServiceUpdateUIListener UI_UPDATE_LISTENER;
	int la = 43269612,lo = -2496943;
	
	
	GeoPoint g_point = new GeoPoint(35410000, 139460000);
	//*****************************************/
	DataInputStream dis = null; 
	DataOutputStream dos = null;

	ServerSocket ss = null; 
	String IP = "";
	int port = 1233;
	long ack;
	//*****************************************/
	
	
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
		startServer();
		_startService();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopServer();
		_shutdownService();
	}
	
	private void _startService() {
		timer.scheduleAtFixedRate(
			new TimerTask() {
				public void run() {
					//count++;
					la = la - 1000;
					lo = lo + 1000;
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
	private void startServer(){
		// Primero indicamos la direccion IP local 
   	 try{ 
   		 IP = InetAddress.getLocalHost().toString();
   		// System.out.println("LocalHost = " + IP); 
   		// tost("IP local: "+ getLocalIpAddressString() + "\n");
   		 
   	    } catch (UnknownHostException uhe)
   		  { 
   	    	//tost("No puedo saber la direccion IP local : " + uhe);
   		  } 
   	
   	 try
   		{
   			ss = new ServerSocket(port);
   			//System.out.println("Server socket running on port: 44444");
   			//tost("socket1 en "+ port +" IP local: " + getLocalIpAddressString());
   	    } catch (IOException ioe)
   		  { 
   			  //System.err.println("Error al abrir el socket de servidor : " + ioe); 
   			  //System.exit(-1); 
   	    	//tost("excepcion" + ioe.toString());
   	      } 
	}
	private void stopServer(){
		try{
			ss.close();
			
		}catch(IOException ioe){
			
		}
	}
	private void updateCoordenadas(){
		Log.d("Server", "updateCoordenadas");
	while (true){	
		try{ 
	    	   // Esperamos a que alguien se conecte a nuestro Socket 
	    	 
	    	   Socket sckt = ss.accept(); 
	    	   Log.d("Server", "conexaceptada");
	    		 //  tost("conexi—n establecida ");
	    	   // Extraemos los Streams de entrada y de salida 
	    	   DataInputStream dis = new DataInputStream(sckt.getInputStream()); 
	    	   DataOutputStream dos = new DataOutputStream(sckt.getOutputStream()); 
	    	   
	    	
	    	 
	    	   // Podemos extraer informacion del socket 
	    	   // Numero de puerto remoto 
	    	   int puerto = sckt.getPort(); 
	    	 
	    	   // Direccion de Internet remota 
	    	   InetAddress direcc = sckt.getInetAddress(); 
	    	   
	    	   // Leemos datos de la peticion \
	    	 
	    	   la = dis.readInt(); 
	    	   lo = dis.readInt(); 
	    	  //   nombre = dis.readLine();
	    	  //   tost("recibida la petici—n: " + entrada);
	    	   // Calculamos resultado 
	    	 ack = 1;
	    	 //  salida = (long)entrada*(long)entrada; 
	    	   //transferimos el fichero
	    	 //  sendfile("prueba",dos);
	    	   
	    	   // Escribimos el resultado 
	    	 
	    	   dos.writeLong(ack); 
	    	   // Cerramos los streams 
	    	   dis.close(); 
	    	   dos.close(); 
	    	 
	    	   sckt.close(); 
	    	   handler.sendEmptyMessage(0);
 
	    	   }//end try
	    		 catch(Exception e)
	    		 { 
	    			//tost("Se ha producido la excepcion : " +e); 
	    		 } 
	  }//while 
	}
}
