package de.herolflo.socketCommunication.json;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONFileReader {
	
	private JSONReaderClient jsonReaderClient;
	private JSONObject jsonObject;
	
	
	public JSONFileReader() {}
	
	public void register(JSONReaderClient client)
	{
		jsonReaderClient = client;
	}
	
	public void load(String fileName) throws URISyntaxException, MalformedURLException, IOException
	{
		String fullPath = "file:" + fileName;
		URI uri = new URI(fullPath);
		JSONTokener tokener = new JSONTokener(uri.toURL().openStream());
		jsonObject = new JSONObject(tokener);
		
		if(jsonReaderClient!=null)
			jsonReaderClient.loadFinish(jsonObject);
	}
	
	public JSONObject getJsonObject() {
		return jsonObject;
	}
}
