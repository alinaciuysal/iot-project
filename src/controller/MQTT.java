package controller;

import java.nio.charset.Charset;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTT {
	
	private MqttClient client;
	
	public MQTT() {
		try {
			client = new MqttClient( 
				    "tcp://176.9.41.239:1883", //URI 
				    MqttClient.generateClientId(), //ClientId 
				    new MemoryPersistence());
		} catch (MqttException e) {
			e.printStackTrace();
			client = null;
		} 
	}
	
	public boolean isConnected() {
		return client.isConnected();
	}
	
	public void connect() {
		try {
			client.connect();
		} catch (MqttSecurityException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	public void publishId(long itemId, int machineId) {
		String payload = "" + itemId;
		
		if(client.isConnected()) {
			try {
				client.publish("onlineSale", payload.getBytes(Charset.forName("UTF-8")), 2, false); // retained? 
			} catch (MqttPersistenceException e) {
				e.printStackTrace();
			} catch (MqttException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public MqttClient getClient() {
		return client;
	}
	
}
