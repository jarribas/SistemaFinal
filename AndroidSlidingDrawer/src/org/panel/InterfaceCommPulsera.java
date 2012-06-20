package org.panel;
/** 
* Clase perteneciente al Facade, la interface del facade.
* @author Jon Arribas
* @author Javier Martin
* @version 1.0, 13/06/2012
*/ 
public interface InterfaceCommPulsera {
		/**
		 * Inicio del servicio TCP o SMS.
		 */
		public void startServer();
		/**
		 * Parar el servicio TCP o SMS.
		 */
		public Posicion updateCoordenadas();
		/**
		 * Obtener la posicion.
		 */
		public void stopServer();
	
}
