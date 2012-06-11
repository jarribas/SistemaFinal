package org.panel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/*import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;*/

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

//import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;



public class Ultima extends MapActivity {
	 // Called when the activity is first created. 
		LinearLayout linearLayout;
		MapView mapView;
		List<Overlay> mapOverlays;
		Drawable drawable;
	    ItemizedOverlay itemizedOverlay;
		//private MapItemizedOverlay itemizedOverlay;
		TextView text;

		MapController mc;
		String add = "";
		
	    @Override 
	    public void onCreate(Bundle savedInstanceState) {
	    	
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.last_pos);
	        mapView = (MapView) findViewById(R.id.mapview_3);
	       
	        text = (TextView) findViewById(R.id.last_dir);
	        //mapView.setBuiltInZoomControls(true);
	        
	        
	        mc = mapView.getController();
	        
	        
	        ZoomControls zoomControls = (ZoomControls) findViewById(R.id.zoomControls3);
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
	        
	        
	        GeoPoint point = new GeoPoint(ul_lat,ul_lon);
	        mc.animateTo(point);
	        
	        mapOverlays = mapView.getOverlays();
	        //mapOverlays.clear();
	        drawable = this.getResources().getDrawable(R.drawable.androidmarker);
	        //itemizedOverlay = new MapItemizedOverlay(drawable, this, 30);
	       itemizedOverlay = new ItemizedOverlay(drawable);

	        
	        
	        
	        
      
	        Geocoder geoCoder = new Geocoder(
                    getBaseContext(), Locale.getDefault());
	        
	        try {
                List<Address> addresses = geoCoder.getFromLocation(
                		point.getLatitudeE6()  / 1E6, 
                		point.getLongitudeE6() / 1E6, 1);

                 
                if (addresses.size() > 0) 
                {
                    for (int j=0; j<addresses.get(0).getMaxAddressLineIndex(); 
                         j++)
                       add += addresses.get(0).getAddressLine(j) + "\n";
                }
                
                
               /* mapView.getManager().addMapLocation(new MapLocation(mMapView,
                		"Prueba para poner un texto en un mapa",
                		point, MapLocation.TYPE_BUBBLE));*/
                
                OverlayItem overlayitem = new OverlayItem(point, "" , "");
    			itemizedOverlay.addOverlay(overlayitem);
    	        mapOverlays.clear();
    	        mapOverlays.add(itemizedOverlay);
                
    	        text.setText(add);
    	        
    	        
                
    	       /* final Toast tag = Toast.makeText(getBaseContext(), add, Toast.LENGTH_LONG);

    	        tag.show();

    	        
    	        new CountDownTimer(9000, 1000)
    	        {

    	            public void onTick(long millisUntilFinished) {tag.show();}
    	            public void onFinish() {tag.show();}

    	        }.start();*/

                
                //Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
               /* AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseContext()); 
                dialog.setTitle("");
                dialog.setMessage("");
                dialog.show();*/
                //mapView.invalidate();
                
            }
            catch (IOException e) {                
                e.printStackTrace();
            }   
	        
	        
	        /*OverlayItem overlayitem = new OverlayItem(point, "", "");
			itemizedOverlay.addOverlay(overlayitem);
	        mapOverlays.clear();
	        mapOverlays.add(itemizedOverlay);*/
	        
	        
	       /* String address = getAddress(ul_lat,ul_lon);  
            Toast.makeText(this.getBaseContext(), address, Toast.LENGTH_SHORT).show();*/  
            //.getMapView().invalidate();  
            /*Toast toast = Toast.makeText(contexto, msg, Toast.LENGTH_SHORT);
    	    toast.show();*/
	        
	    }
	    
	   /* public static String getAddress(double lat, double lng) {  
	        JSONObject jsonObject = getLocationInfo(lat, lng);  
	        String address = "Lugar desconocido";  
	        try {  
	            address = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getString("formatted_address");  
	        } catch (JSONException e) {  
	            e.printStackTrace();  
	        }  
	        return address;  
	    }  
	    
	    private static JSONObject getLocationInfo(double lat, double lng) {  
	        HttpGet httpGet = new HttpGet("http://maps.google.com"  
	                + "/maps/api/geocode/json?latlng=" + lat + "," + lng  
	                + "&sensor=false");  
	        return getLocation(httpGet);  
	    }  
	    
	    private static JSONObject getLocation(HttpGet httpGet) {  
	        HttpClient client = new DefaultHttpClient();  
	        HttpResponse response;  
	        StringBuilder stringBuilder = new StringBuilder();  
	        try {  
	            response = client.execute(httpGet);  
	            HttpEntity entity = response.getEntity();  
	            InputStream stream = entity.getContent();  
	            int b;  
	            while ((b = stream.read()) != -1) {  
	                stringBuilder.append((char) b);  
	            }  
	        } catch (ClientProtocolException e) {  
	        } catch (IOException e) {  
	        }  
	        JSONObject jsonObject = new JSONObject();  
	        try {  
	            jsonObject = new JSONObject(stringBuilder.toString());  
	        } catch (JSONException e) {  
	            e.printStackTrace();  
	        }  
	        return jsonObject;  
	    }  
	    protected boolean isRouteDisplayed() {
	        return false;
	    }*/
	    
	    private boolean existe(String[] archivos,String archbusca)
	    {
	        for(int f=0;f<archivos.length;f++)
	            if (archbusca.equals(archivos[f]))
	                return true;
	        return false;
	    }

	
	protected boolean isRouteDisplayed() {
		
		return false;
	}

	        
	}
