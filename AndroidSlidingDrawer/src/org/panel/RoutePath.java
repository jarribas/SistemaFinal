/*
 * Read more: http://csie-tw.blogspot.com/2009/06/android-driving-direction-route-path.html
 *
 */

package org.panel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;


public class RoutePath extends MapActivity implements Runnable {
	/** Called when the activity is first created. */

	MapView mapView;
	MapController mc;
	 private ProgressDialog pd;
	    
		LocationManager mLocationManager;
		Location mLocation;
		MyLocationListener mLocationListener;
		
		Location currentLocation = null;
		
		TextView outlat;
		TextView outlong;
		GeoPoint srcGeoPoint;
		GeoPoint destGeoPoint;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ruta);

		writeSignalGPS();

	}
    private void setCurrentLocation(Location loc) {
    	currentLocation = loc;
    }
    
    

	private Handler handler = new Handler() {
		
		public void handleMessage(Message msg) {
			pd.dismiss();
			mLocationManager.removeUpdates(mLocationListener);
	    	if (currentLocation!=null) {
	    	
	    		MapView mapView = (MapView) findViewById(R.id.myMapView1);
	    		int lat = (int)(currentLocation.getLatitude()*1E6);
	    		int longi = (int)(currentLocation.getLongitude()*1E6);
	    	
	    		mc = mapView.getController();
	            
	            ZoomControls zoomControls = (ZoomControls) findViewById(R.id.zoomControls2);
	            zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
	                    public void onClick(View v) {
	                            mc.zoomIn();
	                    }
	            });
	            zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
	                    public void onClick(View v) {
	                            mc.zoomOut();
	                    }
	            });
	            
	            
	        	
	        	 String []archivos=fileList();
	 	        String palabra;
	 	        int i = 0;
	 	        int ul_lat = 0;
	 	        int ul_lon = 0;
	 	        

	 	        if (existe(archivos,"last_pos.txt")) 
	 	            try {
	 	                InputStreamReader archivo=new InputStreamReader(openFileInput("last_pos.txt"));
	 	                BufferedReader br=new BufferedReader(archivo);
	 	                String linea=br.readLine();
	 	                System.out.println("String:"+linea);
	 	                
	 	                StringTokenizer tokenizer= new StringTokenizer(linea, " ");
	 	                while (tokenizer.hasMoreTokens()){
	 	                	
	 	                	if(i==0){  
	 	                		palabra = tokenizer.nextToken();
	 	                		System.out.println("palabra i 0:"+palabra);
	 	                		ul_lat =  Integer.parseInt(palabra);
	 	                	i++;
	 	                	}
	 	                	if(i==1){
	 	                		palabra = tokenizer.nextToken();
	 	                		System.out.println("palabra i 1:"+palabra);
	 	                		ul_lon =  Integer.parseInt(palabra);
	 	                	
	 	                	}
	 	                	
	 	                }
	 	               
	 	                br.close();
	 	                archivo.close();
	 	                
	 	            } catch (IOException e)
	 	            {
	 	            }
	 	        
	 	       
	 	               	
	 	       destGeoPoint = new GeoPoint(ul_lat,ul_lon);
	        	srcGeoPoint = new GeoPoint(lat,
	        			longi);
	        	if((srcGeoPoint!=null) && (destGeoPoint !=null)){
	        		 DrawPath(srcGeoPoint, destGeoPoint, Color.GREEN, mapView);

	 	    		mc.animateTo(srcGeoPoint);
	 	    		mc.setZoom(15);	        		
	        	}	           
	    	}
		}
	};
	
	  private boolean existe(String[] archivos,String archbusca)
	    {
	        for(int f=0;f<archivos.length;f++)
	            if (archbusca.equals(archivos[f]))
	                return true;
	        return false;
	    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
    private void writeSignalGPS() {
    	
    	DialogInterface.OnCancelListener dialogCancel = new DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getBaseContext(), 
                        getResources().getString(R.string.gps_signal_not_found), 
                        Toast.LENGTH_LONG).show();
                handler.sendEmptyMessage(0);
            }
          
        };
    	
		pd = ProgressDialog.show(this, this.getResources().getString(R.string.search), 
				this.getResources().getString(R.string.search_signal_gps), true, true, dialogCancel);
		
		Thread thread = new Thread(this);
		thread.start();

    }
	public void run() {
    	
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			
			Looper.prepare();
			
			mLocationListener = new MyLocationListener();
			
			mLocationManager.requestLocationUpdates(
	                LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
			Looper.loop(); 
			Looper.myLooper().quit(); 
			
		} else {
			
            Toast.makeText(getBaseContext(), 
                    getResources().getString(R.string.gps_signal_not_found), 
                    Toast.LENGTH_LONG).show();
            
		}
	}
	 private class MyLocationListener implements LocationListener 
	    {
	        
	        public void onLocationChanged(Location loc) {
	            if (loc != null) {
	                Toast.makeText(getBaseContext(), 
	                    getResources().getString(R.string.gps_signal_found), 
	                    Toast.LENGTH_LONG).show();
	                setCurrentLocation(loc);
	                handler.sendEmptyMessage(0);
	            }
	        }

	      
	        public void onProviderDisabled(String provider) {
	            // TODO Auto-generated method stub
	        }

	        public void onProviderEnabled(String provider) {
	            // TODO Auto-generated method stub
	        }

	       
	        public void onStatusChanged(String provider, int status, 
	            Bundle extras) {
	            // TODO Auto-generated method stub
	        }
	    } 
	private void DrawPath(GeoPoint src, GeoPoint dest, int color,
			MapView mMapView01) {

		// connect to map web service
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.google.com/maps?f=d&hl=en");
		urlString.append("&saddr=");// from
		urlString.append(Double.toString((double) src.getLatitudeE6() / 1.0E6));
		urlString.append(",");
		urlString
				.append(Double.toString((double) src.getLongitudeE6() / 1.0E6));
		urlString.append("&daddr=");// to
		urlString
				.append(Double.toString((double) dest.getLatitudeE6() / 1.0E6));
		urlString.append(",");
		urlString.append(Double
				.toString((double) dest.getLongitudeE6() / 1.0E6));
		urlString.append("&ie=UTF8&0&om=0&output=kml");

		Log.d("xxx", "URL=" + urlString.toString());

		// get the kml (XML) doc. And parse it to get the coordinates(direction
		// route).
		Document doc = null;
		HttpURLConnection urlConnection = null;
		URL url = null;
		try {
			url = new URL(urlString.toString());
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.connect();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(urlConnection.getInputStream());

			if (doc.getElementsByTagName("GeometryCollection").getLength() > 0) {

				String path = doc.getElementsByTagName("GeometryCollection")
						.item(0).getFirstChild().getFirstChild()
						.getFirstChild().getNodeValue();

				Log.d("xxx", "path=" + path);

				String[] pairs = path.split(" ");
				String[] lngLat = pairs[0].split(","); // lngLat[0]=longitude
														// lngLat[1]=latitude
														// lngLat[2]=height

				// src
				GeoPoint startGP = new GeoPoint((int) (Double
						.parseDouble(lngLat[1]) * 1E6), (int) (Double
						.parseDouble(lngLat[0]) * 1E6));
				mMapView01.getOverlays()
						.add(new MyOverLay(startGP, startGP, 1));

				GeoPoint gp1;
				GeoPoint gp2 = startGP;
				for (int i = 1; i < pairs.length; i++) // the last one would be
														// crash
				{
					lngLat = pairs[i].split(",");
					gp1 = gp2;
					// watch out! For GeoPoint, first:latitude, second:longitude
					gp2 = new GeoPoint(
							(int) (Double.parseDouble(lngLat[1]) * 1E6),
							(int) (Double.parseDouble(lngLat[0]) * 1E6));
					mMapView01.getOverlays().add(
							new MyOverLay(gp1, gp2, 2, color));

					Log.d("xxx", "pair:" + pairs[i]);

				}
				mMapView01.getOverlays().add(new MyOverLay(dest, dest, 3)); // use
																			// the
																			// default
																			// color
			}
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ParserConfigurationException e) {

			e.printStackTrace();

		} catch (SAXException e) {

			e.printStackTrace();
		}

	}

}
