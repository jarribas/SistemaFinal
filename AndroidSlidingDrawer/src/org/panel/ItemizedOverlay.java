package org.panel; 

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem; 

//mensaje de alerta
//import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/** 
* Clase que se ocupa de mostrar el marcador y realizar la accion correspondiente al clickar en la pantalla.  
* @author Jon Arribas
* @author Javier Martin
* @version 1.0, 13/06/2012
*/
public class ItemizedOverlay extends com.google.android.maps.ItemizedOverlay {
	
	 
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	GeoPoint punto;
	List<Overlay> mapOverlays;
	Drawable drawable;
	ItemizedOverlay itemizedOverlay;
	 boolean OK = false;
	 
	public ItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
	}

	@Override
	protected OverlayItem createItem(int i) {
	  return mOverlays.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();
	}
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	/**
	 *     Metodo que realiza el click en la pantalla
	 *     @param point La coordenada del click.
	 *     @param mapView El mapa en el que se ha realizado el click. 
	 */
	public boolean onTap(GeoPoint point, MapView mapView)
	{
		
		
				
	    Context contexto = mapView.getContext(); 
	    
	   
	    
	    showAddDialog(contexto, point, mapView);
        
	    return true;
	}
	
	private void showAddDialog(final Context contexto, final GeoPoint point, final MapView mapView) {

		final String TAG = "test";
		final Dialog loginDialog = new Dialog(contexto);
		loginDialog.getWindow().setFlags(
		WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
		WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		loginDialog.setTitle("Centro del alcance");

		LayoutInflater li = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View dialogView = li.inflate(R.layout.add_dailog, null);
		loginDialog.setContentView(dialogView);

		loginDialog.show();

		Button addButton = (Button) dialogView.findViewById(R.id.add_button);
		Button cancelButton = (Button) dialogView
		.findViewById(R.id.cancel_button);

		addButton.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {

		
			loginDialog.dismiss();
			 String msg = "Lat: " + point.getLatitudeE6()/1E6 + " - " +
				        "Lon: " + point.getLongitudeE6()/1E6;
				 
				    Toast toast = Toast.makeText(contexto, msg, Toast.LENGTH_SHORT);
				    toast.show();
				    
				    mapOverlays = mapView.getOverlays();
				    mapOverlays.clear();
				    drawable = contexto.getResources().getDrawable(R.drawable.androidmarker);
			        itemizedOverlay = new ItemizedOverlay(drawable);
			        punto = new GeoPoint(point.getLatitudeE6(),point.getLongitudeE6());
			        OverlayItem overlayitem = new OverlayItem(punto, "", "");
			        			        
			        itemizedOverlay.addOverlay(overlayitem);
			        mapOverlays.add(itemizedOverlay);
				    mapView.invalidate();
				    
				   
				    showRadiusDialog(contexto, punto, mapView);
				  
		}
		});

		cancelButton.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			loginDialog.dismiss();
			
		
		}
		});

		}
	
	
	private void showRadiusDialog(final Context contexto, final GeoPoint point, final MapView mapView) {

		final String TAG = "test";
		final Dialog radiusDialog = new Dialog(contexto);
		radiusDialog.getWindow().setFlags(
		WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
		WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		radiusDialog.setTitle("Configuracion del Radio del alcance");

		LayoutInflater li = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View dialogView = li.inflate(R.layout.rad_dialog, null);
		radiusDialog.setContentView(dialogView);

		radiusDialog.show();

		Button addButton = (Button) dialogView.findViewById(R.id.add_button_radio);
		Button cancelButton = (Button) dialogView.findViewById(R.id.cancel_button_radio);

		addButton.setOnClickListener(new OnClickListener() {
		// @Override
		public void onClick(View v) {
			EditText radio;
			radio=(EditText) dialogView.findViewById(R.id.uname_id);
	
			radiusDialog.dismiss();
			
			try
			{
			    OutputStreamWriter fout=
			        new OutputStreamWriter(
			            contexto.openFileOutput("radio.txt", contexto.MODE_PRIVATE));
			 
			    fout.write(punto.getLatitudeE6()+" "+punto.getLongitudeE6()+" "+Integer.parseInt(radio.getText().toString()));
			    fout.close();
			}
			catch (Exception ex)
			{
			    Log.e("Ficheros", "Error al escribir fichero a memoria interna");
			}
			
				    
					mapOverlays.add(new OverlayMapa(punto,Integer.parseInt(radio.getText().toString())));
				    mapView.invalidate();
				    
		}
		});

		cancelButton.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			radiusDialog.dismiss();
		}
		});

		}
	
}
