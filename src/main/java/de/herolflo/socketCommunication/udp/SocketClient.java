package de.herolflo.socketCommunication.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Sendet Datenpakete an einen Server-Socket.
 * 
 * @author Florian Herold
 *
 */
public class SocketClient {
	
	DatagramSocket clientSocket;
	InetAddress  ipAdress;
	int ipPort;
	
	/**
	 * @param ip IP-Adresse des Serversockets, an den Datenpakete gesendet werden sollen
	 * @param port Der Port des Serversockets, an den Datenpakete gesendet werden sollen
	 * @throws SocketException
	 * @throws UnknownHostException
	 */
	public SocketClient(String ip, int port) throws SocketException, UnknownHostException
	{
		clientSocket = new DatagramSocket();
		ipAdress = InetAddress.getByName(ip);
		ipPort = port;
	}

	/**
	 * Sendet eine Nachricht an den Serversocket.
	 * 
	 * @param message Nachricht, die versendet wird.
	 * @throws IOException
	 */
	public void sendMessage(byte[] message) throws IOException
	{
		DatagramPacket sendPacket = new DatagramPacket(message, message.length, ipAdress, ipPort);
		clientSocket.send(sendPacket);
	}
}
