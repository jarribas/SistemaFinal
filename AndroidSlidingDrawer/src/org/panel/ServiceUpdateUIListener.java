package org.panel;

import com.google.android.maps.GeoPoint;
/** 
* Clase del listener que actualiza los valores de la posicion. 
* @author Jon Arribas
* @author Javier Martin
* @version 1.0, 13/06/2012
*/
public interface ServiceUpdateUIListener {
	public void update(int la, int lo); //GeoPoint g_point
}
