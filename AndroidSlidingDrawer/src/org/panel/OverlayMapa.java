package org.panel;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class OverlayMapa extends Overlay {
	
	

	    private GeoPoint punto;
private int radio;


		public OverlayMapa(){

	    }   

	    

		public OverlayMapa(GeoPoint punto, int radio) {
			// TODO Auto-generated constructor stub
			Log.d("Entrando al constructor", "");
			
			this.punto = punto;
			this.radio = radio;
			
			Log.d("POINTS on click-->",""+punto.getLatitudeE6()+", "+punto.getLongitudeE6());
		}






		public void draw(Canvas canvas, MapView mapv, boolean shadow){
	        super.draw(canvas, mapv, shadow);

	        Projection projection = mapv.getProjection();
			/*GeoPoint geoPoint = 
				new GeoPoint(latitud.intValue(), longitud.intValue());*/
			
			if (shadow == false) 
			{
				Point centro = new Point();
				projection.toPixels(punto, centro);

				 //Log.d("POINTS on click-->",""+punto.getLatitudeE6()+", "+punto.getLongitudeE6());
	                //Log.d("##","Moved and Clicked");
				//Definimos el pincel de dibujo
				Paint p = new Paint();
				p.setColor(Color.BLUE);
				//p.setStyle(Paint.Style.STROKE);
				p.setAlpha(40);
				double lat=(punto.getLatitudeE6()/1E6);
				//Marca Ejemplo 1: Círculo y Texto
				//canvas.drawCircle(centro.x, centro.y, projection.metersToEquatorPixels(radio), p);
				canvas.drawCircle(centro.x, centro.y, (float)(mapv.getProjection().metersToEquatorPixels(radio)*(1/Math.cos(Math.toRadians(lat)))), p);
				
			
	           }
}
}