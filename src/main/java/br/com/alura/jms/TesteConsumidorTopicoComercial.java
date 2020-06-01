package br.com.alura.jms;

import java.io.Serializable;
import java.util.Scanner;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class TesteConsumidorTopicoComercial {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory)context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection();
		connection.setClientID("comercial");
		
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Topic topico = (Topic) context.lookup("loja");
		
		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura");
		
		consumer.setMessageListener(new MessageListener() {

			public void onMessage(Message message) {
				ObjectMessage objectMessage  = (ObjectMessage)message;
				
				try {
			    Serializable object = objectMessage.getObject();
		        System.out.println(objectMessage);
				} catch(JMSException e) {
					e.printStackTrace();
				}
				
			}
			
		});
	
		new Scanner(System.in).nextLine();
			
		session.close();
		connection.close();
		context.close();
	}

}
