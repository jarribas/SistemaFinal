package org.panel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/** 
* Clase que se ocupa del envio de mails.   
* @author Jon Arribas
* @author Javier Martin
* @version 1.0, 13/06/2012
*/
public class Main extends Activity {	
    /** Called when the activity is first created. */
	private EditText etBody2;
	private EditText etBody;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segundo); 
        Button btnSend = (Button) findViewById(R.id.btnSend);
        etBody2=(EditText)findViewById(R.id.etBody2);
        
        Bundle bundle = getIntent().getExtras();
        etBody2.setText(bundle.getString("NOMBRE"));
        
       
        btnSend.setOnClickListener(new OnClickListener() {                      
						public void onClick(View v) {
							/* obtenemos los datos para el envio del correo */
					        EditText etEmail = (EditText) findViewById(R.id.etEmail);
					        EditText etSubject = (EditText) findViewById(R.id.etSubject);
					        EditText etBody2 = (EditText) findViewById(R.id.etBody2);					        		        
					        /* es necesario un intent que levante la actividad deseada */
                            Intent itSend = new Intent(android.content.Intent.ACTION_SEND);                            
                            /* vamos a enviar texto plano a menos que el checkbox esté marcado */
                            itSend.setType("plain/text");                            
                            /* colocamos los datos para el envio */
                            itSend.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ etEmail.getText().toString()});                            
                            itSend.putExtra(android.content.Intent.EXTRA_SUBJECT, etSubject.getText().toString());
                            itSend.putExtra(android.content.Intent.EXTRA_TEXT, etBody2.getText());                          
                            /* iniciamos la actividad */
                            startActivity(itSend);

						}
                });
    }
 
}