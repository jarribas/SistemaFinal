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
	int port = 8080;
	long ack;
	String[] coordenadas;
	String inMsg;
	int la = 43269612,lo = -2496943;
	
	
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
		
			try{ 
				
				if(sckt==null){
					
		    	  	sckt = ss.accept(); 
		    	  	 pos.setLat(la);
					 pos.setLon(lo);
		    	 
				}else{

		    	 
		    	   
				 BufferedReader input = new BufferedReader(new InputStreamReader(sckt.getInputStream()));
                 //PrintWriter output = new PrintWriter(new OutputStreamWriter(sckt.getOutputStream()));

                inMsg = input.readLine();
                coordenadas=inMsg.split(",");               
                
                if (coordenadas!=null){
                la = Integer.parseInt(coordenadas[0]);
                lo = Integer.parseInt(coordenadas[1]);  
               
                }

                pos.setLat(la);
			    pos.setLon(lo);
					}
					
				
		    	   }
		    		 catch(Exception e)
		    		 { 
		    			 
		    			 pos.setLat(la);
						 pos.setLon(lo);
						 sckt=null;
						
		    		 }
	    	  return pos;
		  	
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
