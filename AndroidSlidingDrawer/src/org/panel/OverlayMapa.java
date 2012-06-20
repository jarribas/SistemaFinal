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
/** 
* Clase que se ocupa mediante el metodo draw de pintar el radio. 
* @author Jon Arribas
* @author Javier Martin
* @version 1.0, 13/06/2012
*/
 
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


		/**
		 * Funcion para el dibujo.
		 * @param  canvas El objeto que contiene el metodo de dibujo.
		 * @param shadow El sombreado.
		 * @param mapv El mapa.
		 */
		public void draw(Canvas canvas, MapView mapv, boolean shadow){
	        super.draw(canvas, mapv, shadow);

	        Projection projection = mapv.getProjection();

			
			if (shadow == false) 
			{
				Point centro = new Point();
				projection.toPixels(punto, centro);

				//Definimos el pincel de dibujo
				Paint p = new Paint();
				p.setColor(Color.BLUE);
				
				p.setAlpha(40);
				double lat=(punto.getLatitudeE6()/1E6);
				
				canvas.drawCircle(centro.x, centro.y, (float)(mapv.getProjection().metersToEquatorPixels(radio)*(1/Math.cos(Math.toRadians(lat)))), p);
				
			
	           }
}
}