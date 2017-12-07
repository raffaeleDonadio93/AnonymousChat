package it.isislab.p2p.chat;

import java.io.IOException;

public class TestPublishSubscribeImpl {

	public static void main(String[] args) {
		class MessageListenerImpl implements MessageListener{
			int peerid;
			public MessageListenerImpl(int peerid)
			{
				this.peerid=peerid;
			}
			public Object parseMessage(Object obj) {
				System.out.println(peerid+"] (Direct Message Received) "+obj);
				return "success";
			}
			
		}
		
		try {
			AnonymousChat peer0 = new AnonymousChatImpl(0, "127.0.0.1", new MessageListenerImpl(0));
			
			AnonymousChat peer1 = new AnonymousChatImpl(1, "127.0.0.1", new MessageListenerImpl(1));
			
			AnonymousChat peer2 = new AnonymousChatImpl(2, "127.0.0.1", new MessageListenerImpl(2));
			
			AnonymousChat peer3 = new AnonymousChatImpl(3, "127.0.0.1", new MessageListenerImpl(3));
			
			peer1.createRoom("calcio");
			
			peer1.joinRoom("calcio");
			System.out.println("creata");
			peer2.joinRoom("calcio");
			System.out.println("join");
			peer3.joinRoom("calcio");
			System.out.println("join");
			peer1.sendMessage("calcio", "ciao ragazzzi come va?");
			System.out.println("send");
		
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
