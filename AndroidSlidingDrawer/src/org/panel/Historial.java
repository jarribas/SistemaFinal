package org.panel;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import android.widget.EditText;


public class Historial extends Activity {	
    /** Called when the activity is first created. */
	private EditText etBody;
	private EditText etNombre;
	private EditText etSexo;
	private EditText etDireccion;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial); 
        Button btnSend = (Button) findViewById(R.id.btnSend);
        Button btnRuta = (Button) findViewById(R.id.btnRuta);
        etBody=(EditText)findViewById(R.id.etBody);
        etNombre=(EditText)findViewById(R.id.etNombre);
        etSexo=(EditText)findViewById(R.id.etSexo);
        etDireccion=(EditText)findViewById(R.id.etDireccion);
        String []archivos=fileList();
        int kont=0;
        if (existe(archivos,"Historial.txt")) 
            try {
                InputStreamReader archivo=new InputStreamReader(openFileInput("Historial.txt"));
                BufferedReader br=new BufferedReader(archivo);
               // while ((br.readLine())!=null) {
                	if(kont==0){
                		etNombre.setText(br.readLine());
                		kont++;
                	}
                	if(kont==1){
                		etSexo.setText(br.readLine());
                		kont++;
                	}
                	if(kont==2){
                		etDireccion.setText(br.readLine());
                		kont++;
                	}
                	if(kont==3){
                		etBody.setText(br.readLine());
                		kont++;
                	}
                	
                	//}
               // String linea=br.readLine();
                
                //etBody2.setText(etBody.getText());
                //etBody2.setText(linea);
                
            } catch (IOException e)
            {
            }
        
		//String("Nombre: "+etNombre.getText().toString()+"\r\nSexo: "+etSexo.getText().toString()+"Direccion: \r\n"+etDireccion.getText().toString()+"Historial Medico: \r\n"+etBody.getText().toString());


        btnSend.setOnClickListener(new OnClickListener() {                     
						
							public void onClick(View v) {
								String texto =new String("Nombre: "+etNombre.getText().toString()+"\r\nSexo: "+etSexo.getText().toString()+"\r\nDireccion: "+etDireccion.getText().toString()+"\r\nHistorial Medico: "+etBody.getText().toString());

								Intent intent = new Intent(Historial.this, Main.class);
								intent.putExtra("NOMBRE", texto);
				                startActivity(intent);
							}
                });
       /* btnRuta.setOnClickListener(new OnClickListener() {                     
			
			public void onClick(View v) {
				
 
				Intent intent = new Intent(Historial.this, RoutePath.class);
				
                startActivity(intent);
			}
});*/

    }
    public void grabar(View v) {
        try {
        	String texto =new String(etNombre.getText().toString()+"\r\n"+etSexo.getText().toString()+"\r\n"+etDireccion.getText().toString()+"\r\n"+etBody.getText().toString());
        	OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("Historial.txt",Activity.MODE_PRIVATE));
            archivo.write(texto);
            archivo.flush();
            archivo.close();            
        }catch (IOException e)
        {
        }
        Toast t=Toast.makeText(this,"Los datos fueron grabados", Toast.LENGTH_SHORT);
        t.show();
    }
    private boolean existe(String[] archivos,String archbusca)
    {
        for(int f=0;f<archivos.length;f++)
            if (archbusca.equals(archivos[f]))
                return true;
        return false;
    }
}