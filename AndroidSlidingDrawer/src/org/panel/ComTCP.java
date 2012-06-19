package org.panel;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.util.Log;

/** 
* Clase perteneciente al Facade para la comunicacion por TCP. 
* @author Jon Arribas
* @author Javier Martin
* @version 1.0, 13/06/2012
*/
public class ComTCP implements InterfaceCommPulsera {
	

	DataInputStream dis = null; 
	DataOutputStream dos = null;
	SocketState sstate = null;
 
	ServerSocket ss = null; 
	Socket sckt = null;
	String IP = "";
	//int port = 5554;
	int port = 8080;
	long ack;
	String[] coordenadas;
	String inMsg;
	int la = 43269612,lo = -2496943;
	//int la = 0,lo = 0;
	int dentro=0;
	
	 String latitud;
     String longitud;
	public ComTCP() {
		
	}

	public SocketState getSocketState(){
		return sstate;
	}
	
	public void startServer() {
		
	   	try
	   		{
	   			sstate = new SocketState();
	   			ss = new ServerSocket(port);
	   			if(ss!=null){
	   				sstate.setCreateOK(1);
	   			}else{
	   				sstate.setCreateOK(0);
	   			}
	   					 
	   	    } catch (IOException ioe)
	   		{ 
	   	    	System.err.println("Error al abrir el socket de servidor : " + ioe); 

	   	      }
		
	}



	public Posicion updateCoordenadas() {
		Posicion pos = new Posicion();
		//Log.d("Server", "updateCoordenadas");
		//while (true){	
			try{ 
				//Log.d("Server", "bloqueado");
		    	   // Esperamos a que alguien se conecte a nuestro Socket
				
				
				if(sckt==null){
					
		    	  	sckt = ss.accept(); 
		    	  	 pos.setLat(la);
					 pos.setLon(lo);
		    	  // Log.d("Server", "conexaceptada");
				}else{
					
			  
					
					
					
					
		    	   // Extraemos los Streams de entrada y de salida 
		    	   //DataInputStream dis = new DataInputStream(sckt.getInputStream()); 
		    	   //DataOutputStream dos = new DataOutputStream(sckt.getOutputStream()); 
		    	 
		    	   
				 BufferedReader input = new BufferedReader(new InputStreamReader(sckt.getInputStream()));
                 PrintWriter output = new PrintWriter(new OutputStreamWriter(sckt.getOutputStream()));
				
                
				
		    	   // Leemos datos de la peticion 
                inMsg = input.readLine();
                coordenadas=inMsg.split(",");               
                
                if (coordenadas!=null){
                la = Integer.parseInt(coordenadas[0]);
                lo = Integer.parseInt(coordenadas[1]);  
               
                }
                	 
                
                
                pos.setLat(la);
			    pos.setLon(lo);
				//la=dis.readInt();
				//lo=dis.readInt();
		    	  //la = la - 5000;
				 // lo = lo + 5000;	 
				 
			     
			   //Log.d("lat", String.valueOf(la));
			   //Log.d("lon", String.valueOf(lo));
			   
		    	   // Calculamos resultado 
		    	   ack = 1;
		    	    	   
		    	   // Escribimos el resultado 
		    	 
		    	  // dos.writeLong(ack); 
		    	   // Cerramos los streams 
		    	 //dis.close(); 
		    	  // dos.close(); 
					}
					
				
		    	   }//end try
		    		 catch(Exception e)
		    		 { 
		    			 
		    			 pos.setLat(la);
						 pos.setLon(lo);
						 sckt=null;
						 
						 
		    			//tost("Se ha producido la excepcion : " +e); 
		    		 }
	    	  return pos;
		  //}		
	}

	public void stopServer() {

		try{
			ss.close();
			sckt.close();
			if(ss!=null){
   				sstate.setDestroyedOK(true);
   			}else{
   				sstate.setDestroyedOK(false);
   			}
			
		}catch(IOException ioe){
			
		}
	
	}
	
}
