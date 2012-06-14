package org.panel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;





public class ComTCP implements InterfaceCommPulsera {
	

	DataInputStream dis = null; 
	DataOutputStream dos = null;

	ServerSocket ss = null; 
	Socket sckt = null;
	String IP = "";
	int port = 5554;
	long ack;
	int la = 43269612,lo = -2496943;
	
	public ComTCP() {
		
	}

	public void startServer() {
		
	   	try
	   		{
	   			ss = new ServerSocket(port);
	   					
	   					 
	   	    } catch (IOException ioe)
	   		{ 
	   	    	System.err.println("Error al abrir el socket de servidor : " + ioe); 

	   	      }
		
	}



	public Posicion updateCoordenadas() {
		Posicion pos = new Posicion();
		Log.d("Server", "updateCoordenadas");
		//while (true){	
			try{ 
				Log.d("Server", "bloqueado");
		    	   // Esperamos a que alguien se conecte a nuestro Socket	
				if(sckt==null){
		    	  	//sckt = ss.accept(); 
		    	   Log.d("Server", "conexaceptada");
				}
		    	   // Extraemos los Streams de entrada y de salida 
		    	   //DataInputStream dis = new DataInputStream(sckt.getInputStream()); 
		    	   //DataOutputStream dos = new DataOutputStream(sckt.getOutputStream()); 
		    	 
		    	   
		    	   // Leemos datos de la peticion 
				//la=dis.readInt();
				//lo=dis.readInt();
		    	  la = la - 5000;
					lo = lo + 5000;	 
				 pos.setLat(la);
			     pos.setLon(lo);
		    	   // Calculamos resultado 
		    	   ack = 1;
		    	    	   
		    	   // Escribimos el resultado 
		    	 
		    	  // dos.writeLong(ack); 
		    	   // Cerramos los streams 
		    	  dis.close(); 
		    	  // dos.close(); 
		    	 
		    	   //sckt.close(); 

		    	   }//end try
		    		 catch(Exception e)
		    		 { 
		    			//tost("Se ha producido la excepcion : " +e); 
		    		 }
	    	  return pos;
		  //}		
	}

	public void stopServer() {

		try{
			ss.close();
			
		}catch(IOException ioe){
			
		}
	
	}
	
}
