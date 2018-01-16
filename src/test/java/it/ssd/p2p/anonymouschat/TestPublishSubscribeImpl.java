package it.ssd.p2p.anonymouschat;

import java.io.IOException;

import it.ssd.p2p.anonymouschat.AnonymousChat;
import it.ssd.p2p.anonymouschat.AnonymousChatImpl;
import it.ssd.p2p.anonymouschat.MessageListenerImpl;

public class TestPublishSubscribeImpl {

	public static void main(String[] args) {
		
		try {
			AnonymousChat peer0 = new AnonymousChatImpl(0, "127.0.0.1", new MessageListenerImpl(0));
			
			AnonymousChat peer1 = new AnonymousChatImpl(1, "127.0.0.1", new MessageListenerImpl(1));
			
			
			peer0.createRoom("calcio");
			peer0.joinRoom("calcio");
			peer1.joinRoom("calcio");
			System.out.println("creata");
			peer1.sendMessage("calcio", "ciao ragazzzi come va?");
			System.out.println("send");
		
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
