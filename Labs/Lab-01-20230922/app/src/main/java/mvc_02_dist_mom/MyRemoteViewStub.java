package mvc_02_dist_mom;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import mvc_01_basic.*;

//si mette in osservazione del modello e poi pubblica la modifica del model su un topic di rabbitmq

//- la comunicazione è asincrona (ogni modifica del model è un evento)
class MyRemoteViewStub implements ModelObserver {

	private final static String EXCHANGE_NAME = "mvc";
	private Channel channel;
	private Connection connection;
	private ConnectionFactory factory;

	private ModelObserverSource model;
	
	public MyRemoteViewStub(ModelObserverSource model) {		
		this.model = model;		
	    model.addObserver(this);	    
	    try {
			factory = new ConnectionFactory();
		    factory.setHost("localhost");
		    connection = factory.newConnection();		    
		    channel = connection.createChannel();
		    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		    System.out.println("Remote View Stub installed.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void notifyModelUpdated() {
		System.out.println(channel);
		try {		    
		    String message = "" + model.getState();
			//qualora il modello cambi si esegue una pubblicsh
	        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
		
	
}
