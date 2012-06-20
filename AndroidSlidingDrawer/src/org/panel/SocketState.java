package org.panel;
/** 
* Clase para guardar valores del socket utiles para los testeos JUnit. 
* @author Jon Arribas
* @author Javier Martin
* @version 1.0, 13/06/2012
*/  
public class SocketState {
	int createOK;
	boolean destroyedOK;
	
	public boolean isDestroyedOK() {
		return destroyedOK;
	}
	public void setDestroyedOK(boolean destroyedOK) {
		this.destroyedOK = destroyedOK;
	}
	public int getCreateOK() {
		return createOK;
	}
	public void setCreateOK(int createOK) {
		this.createOK = createOK;
	}
	
	
	
	
	
	
}
