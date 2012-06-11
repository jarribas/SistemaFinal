package org.panel;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;


import org.panel.ItemizedOverlay;
import org.panel.MyService;
import org.panel.ServiceUpdateUIListener;



//import android.app.Activity;
import android.content.Intent;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Test extends MapActivity implements ServiceUpdateUIListener{
		/** Called when the activity is first created. */
	LinearLayout linearLayout;
	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	ItemizedOverlay itemizedOverlay;
	
	
	MapController mc;
    GeoPoint p;
    int zoomLevel = 12;
    TextView text;
    Vibrator v;
    private NotificationManager notificationMgr;
    
    Context contexto;
	
		public void onCreate(Bundle savedInstanceState)
			{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.main);
				
				//Lanzamos el servicio
		        
			       text = (TextView) findViewById(R.id.cont);
			    // Get instance of Vibrator from current Context
				   v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					 
					
			        
			       mapView = (MapView) findViewById(R.id.mapview);
			        mapView.setBuiltInZoomControls(true);
			        
			        mc = mapView.getController();
			         
			        GeoPoint point = new GeoPoint(43270612,-2496943);
			        mc.animateTo(point);
			        mc.setZoom(zoomLevel);
			        
			        mapOverlays = mapView.getOverlays();
			        //mapOverlays.clear();
			        drawable = this.getResources().getDrawable(R.drawable.androidmarker);
			        itemizedOverlay = new ItemizedOverlay(drawable);
			
			        OverlayItem overlayitem = new OverlayItem(point, "", "");
					itemizedOverlay.addOverlay(overlayitem);
			        mapOverlays.clear();
			        mapOverlays.add(itemizedOverlay);
			        dibujarRadio();
			        contexto = mapView.getContext(); //sin uso	        
			        MyService.setUpdateListener(this);
			        startService();
			        
				final Button btnAlcance = (Button)findViewById(R.id.btnAlcance);
					btnAlcance.setOnClickListener(new OnClickListener() {
			            public void onClick(View arg0) {
			                Intent intent = new Intent(Test.this, Primero.class);		         
			                startActivity(intent);
			            }
			        });	
					
				final Button btnMedico = (Button)findViewById(R.id.btnMedico);
				btnMedico.setOnClickListener(new OnClickListener() {
			            public void onClick(View arg0) {
			                Intent intent = new Intent(Test.this, Historial.class);		         
			                startActivity(intent);
			            }
			        });	
				
				final Button btnRuta = (Button)findViewById(R.id.btnRuta);
				btnRuta.setOnClickListener(new OnClickListener() {
			            public void onClick(View arg0) {
			                Intent intent = new Intent(Test.this, RoutePath.class);		         
			                startActivity(intent);
			            }
			        });	
				 
				final Button btnUltimo = (Button)findViewById(R.id.btnUltimo);
				btnUltimo.setOnClickListener(new OnClickListener() {
			            public void onClick(View arg0) {
			                Intent intent = new Intent(Test.this, Ultima.class);
			                System.out.println("Start activity");
			                startActivity(intent);
			            }
			        });	
				
				final Button btnUrgencia = (Button)findViewById(R.id.btnUrgencia);
				btnUrgencia.setOnClickListener(new OnClickListener() {
			            public void onClick(View arg0) {
			                Intent intent = new Intent(Test.this, Phonecall.class);
			                System.out.println("Start call activity");
			                startActivity(intent);
			            	
			            }
			        });	
			}
		
		
		protected boolean isRouteDisplayed() {
	        return false;
	    }

		@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.menu, menu);
	        return true;
	    }
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle item selection
	        switch (item.getItemId()) {
	        case R.id.stopService:
	        	stopApplication();
	            return true;
	        case R.id.help:
	            alertRegister();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }
		
		public void update(int la, int lo) {
			
		GeoPoint pp = new GeoPoint( la, lo );//43269692,-24969431
			
			OverlayItem overlayitem1 = new OverlayItem(pp, "", "");
			itemizedOverlay = new ItemizedOverlay(drawable);
			itemizedOverlay.addOverlay(overlayitem1);
			
	        mapOverlays.clear();
	        mapOverlays.add(itemizedOverlay);
	        mc.animateTo(pp);
	        try
			{
			    OutputStreamWriter fout=
			        new OutputStreamWriter(
			        		contexto.openFileOutput("last_pos.txt", contexto.MODE_PRIVATE));
			
			    fout.write(pp.getLatitudeE6()+" "+pp.getLongitudeE6()+" ");
			    fout.close();
			}
			catch (Exception ex)
			{
			    Log.e("Ficheros", "Error al escribir fichero a memoria interna");
			}
			
			String msg = pp.toString();
			text.setText(msg);
			
			dibujarRadio();
			calculateDistance(la, lo);
			
			
			
	 /*
	        Toast toast = Toast.makeText(contexto, msg, Toast.LENGTH_SHORT);
	        toast.show();
	        */
		}
		
		private void startService() {
			Log.d("", "11antes de arrancar el servicio ");
	    	Intent svc = new Intent(this, MyService.class);
	    	Log.d("", "22antes de arrancar el servicio");
	        startService(svc);
	    }
	    
	    private void stopService() {
	    	Intent svc = new Intent(this, MyService.class);
			stopService(svc);
	    }
	    
	    public void dibujarRadio(){
	    	 String []archivos=fileList();
		        String palabra;
		        int i = 0;
		        int lat_centro = 0;
		        int lon_centro = 0;
		        int rad = 0;

		        if (existe(archivos,"radio.txt")) 
		            try {
		                InputStreamReader archivo=new InputStreamReader(openFileInput("radio.txt"));
		                BufferedReader br=new BufferedReader(archivo);
		                String linea=br.readLine();
		                System.out.println("String:"+linea);
		                
		                StringTokenizer tokenizer= new StringTokenizer(linea, " ");
		                while (tokenizer.hasMoreTokens()){
		                	//palabra = tokenizer.nextToken();
		                	//System.out.println("palabra:"+palabra);
		                	if(i==0){  
		                		palabra = tokenizer.nextToken();
		                		System.out.println("palabra i 0:"+palabra);
		                	lat_centro =  Integer.parseInt(palabra);
		                	i++;
		                	}
		                	if(i==1){
		                		palabra = tokenizer.nextToken();
		                		System.out.println("palabra i 1:"+palabra);
		                	lon_centro =  Integer.parseInt(palabra);
		                	i++;
		                	}
		                	if(i==2){
		                		palabra = tokenizer.nextToken();
		                		System.out.println("palabra i 2:"+palabra);
		                	rad = Integer.parseInt(palabra);	
		                	}
		                }
		               
		                br.close();
		                archivo.close();
		                
		            } catch (IOException e)
		            {
		            }
		        
		        GeoPoint centro = new GeoPoint( lat_centro,lon_centro );
		        mapOverlays.add(new OverlayMapa(centro,rad));
		        System.out.println("Estoy aqui");
		        System.out.println("Lat_cen: " + lat_centro + " - " +
		    	        "Lon_cen: " + lon_centro + " - " +"Radio: " + rad);
		       
		        
		        
	    }
	    
	    private boolean existe(String[] archivos,String archbusca)
	    {
	        for(int f=0;f<archivos.length;f++)
	            if (archbusca.equals(archivos[f]))
	                return true;
	        return false;
	    }

public void calculateDistance(int la, int lo){  
	    	
	    	
	    	String []archivos=fileList();
	        String palabra;
	        int i = 0;
	        int lat_centro = 0;
	        int lon_centro = 0;
	        int rad = 0;

	        if (existe(archivos,"radio.txt")) 
	            try {
	                InputStreamReader archivo=new InputStreamReader(openFileInput("radio.txt"));
	                BufferedReader br=new BufferedReader(archivo);
	                String linea=br.readLine();
	                System.out.println("String:"+linea);
	                
	                StringTokenizer tokenizer= new StringTokenizer(linea, " ");
	                while (tokenizer.hasMoreTokens()){
	                	//palabra = tokenizer.nextToken();
	                	//System.out.println("palabra:"+palabra);
	                	if(i==0){  
	                		palabra = tokenizer.nextToken();
	                		System.out.println("palabra i 0:"+palabra);
	                	lat_centro =  Integer.parseInt(palabra);
	                	i++;
	                	}
	                	if(i==1){
	                		palabra = tokenizer.nextToken();
	                		System.out.println("palabra i 1:"+palabra);
	                	lon_centro =  Integer.parseInt(palabra);
	                	i++;
	                	}
	                	if(i==2){
	                		palabra = tokenizer.nextToken();
	                		System.out.println("palabra i 2:"+palabra);
	                	rad = Integer.parseInt(palabra);	
	                	}
	                }
	               
	                br.close();
	                archivo.close();
	                
	            } catch (IOException e)
	            {
	            }
	    	
	    	double dist = 0.0;
	    	double latVal2 = la/(double)1E6;
	    	double latVal1 = lat_centro/(double)1E6;
	    	//double latVal1 = 43270612/(double)1E6;
			double deltaLat = Math.toRadians(latVal2 - latVal1);
			//double lonVal1 = -2496943/(double)1E6;
			double lonVal1 = lon_centro/(double)1E6;
			double lonVal2 = lo/(double)1E6;
			double deltaLon = Math.toRadians(lonVal2 - lonVal1);
	         latVal1 = Math.toRadians(latVal1);
	         latVal2 = Math.toRadians(latVal2);
	         lonVal1 = Math.toRadians(lonVal1);
	         lonVal2 = Math.toRadians(lonVal2);
	         double earthRadius = 6371;
	         double a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) +
	Math.cos(latVal1) * Math.cos(latVal2) * Math.sin(deltaLon/2) * Math.sin
	(deltaLon/2);
	         double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	         dist = earthRadius * c;
	         
	         /*String msg = "R"+lat_centro" - " "lon_centro"+lon_centro" - " "radio"+rad";
			 Log.d(" ", msg);*/
	         
	    	String msg = "Distancia: " + dist;
			 Log.d("Monitor: ", msg);
			 Log.d("Monitor: ", String.valueOf(rad));
			 
			 if (dist>(rad/1000)){
				 
				 
				 Toast toast = Toast.makeText(mapView.getContext(), "Ha abandonado el radio de seguridad!!!", Toast.LENGTH_SHORT);
				 toast.show();
				// Vibrate for 300 milliseconds
				 v.vibrate(3000);
				 alertRegister();
			 }
			 
			    /*Toast toast = Toast.makeText(mapView.getContext(), msg, Toast.LENGTH_SHORT);
			    toast.show();*/

	      }  
		private void alertRegister(){
			notificationMgr =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
			displayNotificationMessage("Mensaje del Localizador");
		}
		private void displayNotificationMessage(String message)
		{
			Notification notification = new Notification(R.drawable.note, message, System.currentTimeMillis());
	
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new  Intent(this, Test.class), 0);
	
			notification.setLatestEventInfo(this, "Vigilado esta fuera del area de alcance", message, contentIntent);
	
			notificationMgr.notify(R.string.hello, notification);
		}
		
		private void stopApplication(){
			stopService();
			finish();
		}

	}