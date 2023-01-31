
package de.herolflo.socketCommunication.udp;

/**
 * Wird von der Komponente implementiert, die auf ein Datenpaket eines Serversockets wartet.
 * 
 * @author Florian Herold
 *
 */
public interface SocketServerObserver {
	
	void recieveMessage(byte[] message, int port);
}
