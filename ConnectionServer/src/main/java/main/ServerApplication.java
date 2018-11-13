package main;

import javax.jms.JMSException;

import publisher.DefaultPublisher;
import server.properties.ServerProperties;
import server.utils.ServerLogger;

public class ServerApplication {
	static ServerLogger log = ServerLogger.getInstance();
	
	public static void main(String[] args) {
		
		DefaultPublisher publisher = new DefaultPublisher();
		
		try {
			publisher.init();
			publisher.publishToTopic(ServerProperties.DEFAULT_TOPIC);
		} catch (JMSException e) {
			
			log.error("main", "ServerApplication", e.getMessage());
			e.printStackTrace();
		}
		
		
		try {
			while(true) {
				publisher.sendMessage("BANANE");
				System.out.println("send");
				Thread.sleep(2000);
			}
			
		} catch (JMSException e) {
			
			log.error("main", "ServerApplication", e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
