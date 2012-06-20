package org.panel;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
/** 
* Clase que se ocupa de la llamada de emergencias. 
* @author Jon Arribas
* @author Javier Martin
* @version 1.0, 13/06/2012
*/
public class Phonecall extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        call();
    }
/** 
* Funcion de la llamada.
*/ 
private void call() {
    try {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:112"));
        startActivity(callIntent);
    } catch (ActivityNotFoundException activityException) {
        Log.e("dialing-example", "Call failed", activityException);
    }
}

}