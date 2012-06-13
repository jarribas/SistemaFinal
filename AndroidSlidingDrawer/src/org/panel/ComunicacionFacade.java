package org.panel;




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
