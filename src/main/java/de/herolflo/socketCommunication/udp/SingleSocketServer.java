package de.herolflo.socketCommunication.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Kapselt einen einzelnen Serversocket. Das Protokoll ist UDP.
 * 
 * @author Florian Herold
 *
 */
public class SingleSocketServer extends Thread {

	private final int MESSAGE_SIZE = 1024;
	//public SocketServerObserver socketObserver;
	
	private Vector<SocketServerObserver> socketObservers;
	DatagramSocket serverSocket;

	//private final AtomicBoolean running = new AtomicBoolean(false);	//wird als Flag genutzt, um Thread.stop zu umgehen
	
	public SingleSocketServer(int port) throws SocketException
	{
		serverSocket = new DatagramSocket(port);
		socketObservers = new Vector<SocketServerObserver>();
		
		this.start();
	}
	
	/**
	 * In einem eigenen Thread wird auf eine Antwort auf dem Serversocket gewartet. 
	 * Eintreffende Daten werden als byte[] an die übergeordnete Komponente weitergeleitet.
	 */
	public void run() {
		//running.set(true);
        //while(running.get())
		while(true)
        {
        	byte[] recieveMessage = new byte[MESSAGE_SIZE];
        	DatagramPacket receivePacket = new DatagramPacket(recieveMessage, recieveMessage.length);
        	try {
				serverSocket.receive(receivePacket);
				byte[] message = receivePacket.getData();
				
				for(SocketServerObserver observer : socketObservers)
					observer.recieveMessage(message, serverSocket.getLocalPort());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
    }
	
	/**
	 * Eine Komponente, die die Daten braucht, die an den Serversocket gesendet werden, kann sich hier registrieren.
	 * @param observer
	 */
	public void register(SocketServerObserver observer)
	{
		if(!socketObservers.contains(observer))
			socketObservers.add(observer);
	}
	
	public void remove(SocketServerObserver observer)
	{
		if(socketObservers.contains(observer))
			socketObservers.remove(observer);
	}
	
	public int getPort()
	{
		return serverSocket.getLocalPort();
	}
	
	public void close()
	{
		//this.stop(); -> deprecated
		this.stop();
		//running.set(false);

		serverSocket.close();
	}
}
