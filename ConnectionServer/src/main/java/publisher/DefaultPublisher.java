package publisher;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;


import server.properties.ServerProperties;
import server.utils.ServerLogger;

public class DefaultPublisher {
	private Connection connection;
	private Session session; 
	private MessageProducer messagePublisher;
	private ServerLogger log = ServerLogger.getInstance();

	public void init() throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ServerProperties.BROKER_URL);
		connection = factory.createConnection();
		session = connection.createSession(false,  Session.AUTO_ACKNOWLEDGE);
	}
	
	public void publishToTopic(String topicname) throws JMSException {	
		Topic topic = session.createTopic(topicname);
		messagePublisher = session.createProducer(topic);
		messagePublisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	}
	
	public void sendMessage(Object o) throws JMSException {
		TextMessage message = session.createTextMessage(" " + o );
		message.setStringProperty("default Message", "Example : " + new Date());
	}
	
	
	public void close() {
		try {
			session.close();
			connection.close();
		} catch (JMSException e) {
			log.error("close", "DefaultPublisher", e.getMessage());
			e.printStackTrace();
		}
		
	}

}
