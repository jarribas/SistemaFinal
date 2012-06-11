package org.panel;
import java.util.List;

import com.google.android.maps.GeoPoint;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

//import android.app.Activity;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.view.View;

import android.widget.LinearLayout;
import android.widget.ZoomControls;



public class Primero extends MapActivity {
    // Called when the activity is first created. 
	LinearLayout linearLayout;
	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	ItemizedOverlay itemizedOverlay;
	MapController mc;
	
    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primero);
        mapView = (MapView) findViewById(R.id.mapview_2);
        
        //mapView.setBuiltInZoomControls(true);
        
       
        mc = mapView.getController();
        
        ZoomControls zoomControls = (ZoomControls) findViewById(R.id.zoomControls1);
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

        
        GeoPoint point = new GeoPoint(43270612,-2496943);
        mc.animateTo(point);
        
        
        mapOverlays = mapView.getOverlays();
        //mapOverlays.clear();
        drawable = this.getResources().getDrawable(R.drawable.androidmarker);
        itemizedOverlay = new ItemizedOverlay(drawable);

        OverlayItem overlayitem = new OverlayItem(point, "", "");
		itemizedOverlay.addOverlay(overlayitem);
        mapOverlays.clear();
        mapOverlays.add(itemizedOverlay);
        
    }
    
    
    protected boolean isRouteDisplayed() {
        return false;
    }
        
}
