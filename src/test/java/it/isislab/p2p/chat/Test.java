package it.isislab.p2p.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.tomp2p.utils.Pair;

public class Test extends TestCase {

	private List<Pair<AnonymousChat,MessageListenerImpl>> lista;
	private int lastIdPeer;
	
	public void test() throws IOException, InterruptedException {
		String roomName = "calcio";
		createPoolOfPeer(10);
		lista.get(0).element0().createRoom(roomName); // create Room
		joinPeersToRoom(roomName);
		String messaggio = "hello everyone!!!";
		int sender = 0; // peer 0 is the sender of the message
		lista.get(sender).element0().sendMessage("calcio", messaggio);
		checkArrivalsMessages(sender, messaggio); // check the incoming message on all peer of the room
		
		
		messaggio = "End of the world!!!";
		lista.get(0).element0().leaveRoom(roomName);
		lista.remove(0);
		lista.get(sender).element0().sendMessage("calcio", messaggio);
		checkArrivalsMessages(sender, messaggio); 
		
		
		
		messaggio = "No crimes!!!";
		lista.get(0).element0().leaveRoom(roomName);
		lista.remove(0);
		lista.get(sender).element0().sendMessage("calcio", messaggio);
		checkArrivalsMessages(sender, messaggio); 
		
		
		
		messaggio = "The big bang Theory!!!";
		lista.get(0).element0().leaveRoom(roomName);
		lista.remove(0);
		lista.get(sender).element0().sendMessage("calcio", messaggio);
		checkArrivalsMessages(sender, messaggio); 
		
		
		addPeersToRoom(roomName, 5);
		messaggio = "Alcatraz!!!";
		lista.get(sender).element0().sendMessage("calcio", messaggio);
		checkArrivalsMessages(sender, messaggio); 
		
		
	}
	
	
	private void createPoolOfPeer(int capacity) throws IOException{
		lista = new ArrayList<Pair<AnonymousChat,MessageListenerImpl>>();
		MessageListenerImpl listener;
		AnonymousChat peer;
		int i = 0;
		for (i=0 ; i<capacity;i++) {
			listener = new MessageListenerImpl(i);
			peer = new AnonymousChatImpl(i, "127.0.0.1", listener);
			lista.add(new Pair<AnonymousChat, MessageListenerImpl>(peer, listener));
		}
		this.lastIdPeer = i;
	}
	
	private void joinPeersToRoom(String room) {
		for(int i=0 ; i<lista.size(); i++) {
			lista.get(i).element0().joinRoom(room);
		}
	}
	
	private void checkArrivalsMessages(int sender, String messaggio) {
		for(int i = 0; i<lista.size(); i++) {
			if(i!=sender) {
				while(!lista.get(i).element1().isLastMessage());
				assertEquals(messaggio, lista.get(i).element1().getMessage().getMessage());
				lista.get(i).element1().setLastMessage(false);
			}
		}
	}
	
	private void addPeersToRoom(String roomName, int number) throws IOException {
		MessageListenerImpl listener;
		AnonymousChat peer;
		for (int i=0;i <number; i++) {
			listener = new MessageListenerImpl(lastIdPeer + i);
			peer = new AnonymousChatImpl(lastIdPeer + i, "127.0.0.1", listener);
			peer.joinRoom(roomName);
			lista.add(new Pair<AnonymousChat, MessageListenerImpl>(peer, listener ));
		}
	}
	
}
