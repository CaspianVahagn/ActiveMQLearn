package topicListener;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import server.properties.ServerProperties;
import server.utils.ServerLogger;

public class DefaultListener implements MessageListener {
	
	
	
	private Connection connection;
	private Session session;
	private MessageConsumer messageConsumer;
	
	final static boolean USE_EMBEDED_BROOKER = true;
	
	BrokerService embededBroker = null;
	private ServerLogger log = ServerLogger.getInstance();
	
	@Override
	public void onMessage(Message message) {
		System.out.println("Got a Message:");
		
		try {
			
			if(message instanceof TextMessage) {
				TextMessage tm = (TextMessage) message;
				System.out.println(tm.getText());
			}
			
		} catch (JMSException e) {
			// TODO: handle exception
		}
		
	}
	
	public DefaultListener() {
		
		if(USE_EMBEDED_BROOKER) {
			embededBroker = new BrokerService();
			try {
				embededBroker.addConnector(ServerProperties.ALL_MASKS_8080);
				embededBroker.start();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("Constructor", "DefaultListener", "Connection error: " + e.getMessage());
				e.printStackTrace();
				System.exit(1);
			}
		}
		
	}
	
	public void init() throws JMSException{
		System.err.println("INIT!");
		if(USE_EMBEDED_BROOKER) {
			System.out.println("Embedded Broker");
		}else {
			System.out.println("Extern Broker");
		}
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ServerProperties.LOCALVM);
		connection = factory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	}
	
	public void listenToTopic(String topicname) throws JMSException {	
		Topic topic = session.createTopic(topicname);
		messageConsumer = session.createConsumer(topic);
		messageConsumer.setMessageListener(this);
		connection.start();
		System.err.println("Start: " +  ServerProperties.LOCALVM + " Topic: " + topicname);
	}
	
	public static void main(String[] args) {
		DefaultListener listener = new DefaultListener();
		try {
			listener.init();
			listener.listenToTopic(ServerProperties.DEFAULT_TOPIC);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


}
