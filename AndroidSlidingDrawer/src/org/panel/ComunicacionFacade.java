package org.panel;

/** 
* Clase Facade en la que se define la comunicacion deseada. 
* @author Jon Arribas
* @author Javier Martin
* @version 1.0, 13/06/2012
*/
public class ComunicacionFacade {
	
	InterfaceCommPulsera com;

	public ComunicacionFacade(int tipoComunicacion) {
		
		switch(tipoComunicacion){
		case 1: com= new ComTCP(); 
		break;
		case 2: com = new ComSMS();
		break;
		default:
		break;
		}

	}

	public void startServer() {

		com.startServer();
	}

	
	public Posicion updateCoordenadas() {

		return com.updateCoordenadas();		
	}

	public void stopServer() {

		com.stopServer();
	}


}
