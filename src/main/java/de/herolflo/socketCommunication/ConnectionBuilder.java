package de.herolflo.socketCommunication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.herolflo.socketCommunication.json.JSONFileReader;
import de.herolflo.socketCommunication.json.JSONReaderClient;
import de.herolflo.socketCommunication.udp.SocketClient;
import de.herolflo.socketCommunication.udp.SingleSocketServer;

public class ConnectionBuilder implements JSONReaderClient {

	private final String DEFAULT_INIT_FILE = "socketInit.json";
	
	private Vector<SingleSocketServer> socketServers;
	private SocketClient socketClient;
	
	private ConnectionBuilderObserver connectionBuilderObserver;
	
	public ConnectionBuilder()
	{
		socketServers = new Vector<SingleSocketServer>();
	}
	
	public void initConntection() throws MalformedURLException, URISyntaxException, IOException
	{
		initConntection(DEFAULT_INIT_FILE);
	}
	
	public void initConntection(String initFile) throws MalformedURLException, URISyntaxException, IOException
	{
		JSONFileReader fileReader = new JSONFileReader();
		fileReader.register(this);
		fileReader.load(initFile);
		
	}

	@Override
	public void loadFinish(JSONObject connectionInformations) {
		
		try {
			initSockets(connectionInformations);
		} catch (SocketException | UnknownHostException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initSockets(JSONObject connectionInformations) throws SocketException, UnknownHostException, JSONException {

		createSocketClient(connectionInformations.getJSONObject("client").getString("ipAdress"),
							connectionInformations.getJSONObject("client").getInt("port"));
		
		createSocketServers(calculatePortArray(connectionInformations.getJSONArray("serverPorts")));
		
		connectionBuilderObserver.buildFinish();
	}
	
	public void register(ConnectionBuilderObserver observer)
	{
		connectionBuilderObserver = observer;
	}
	
	private void createSocketServers(int[] ports) throws SocketException
	{		
		for(int port : ports)
		{
			socketServers.add(new SingleSocketServer(port));
		}
	}
	
	private void createSocketClient(String ipAdress, int port) throws SocketException, UnknownHostException
	{
		socketClient = new SocketClient(ipAdress, port);
	}
	
	private int[] calculatePortArray(JSONArray portJSONArray)
	{
		int[] portArray = new int[portJSONArray.length()];
		
		for(int i=0; i<portJSONArray.length(); i++)
		{
			portArray[i] = portJSONArray.getJSONObject(i).getInt("port");
		}
		
		
		return portArray;
	}
	
	public Vector<SingleSocketServer> getSocketServers()
	{
		return socketServers;
	}
	
	public SocketClient getSocketClient()
	{
		return socketClient;
	}
	
}
