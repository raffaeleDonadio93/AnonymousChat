package it.ssd.p2p.anonymouschat;

public class MessageListenerImpl implements MessageListener {
	
	int peerid;
	
	public MessageListenerImpl(int peerid)
	{
		this.peerid=peerid;
	}
	
	
	public Object parseMessage(Object obj) {
	
		System.out.println(peerid+"(Direct Message Received) "+obj);
		return "success";
	}
	
}
