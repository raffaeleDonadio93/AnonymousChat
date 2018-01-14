package it.isislab.p2p.chat;

import java.io.IOException;

import junit.framework.TestCase;

public class Test extends TestCase {

	public void testJoin() throws IOException {
		AnonymousChat peer0 = new AnonymousChatImpl(0, "127.0.0.1", new MessageListenerImpl(0));
		AnonymousChat peer1 = new AnonymousChatImpl(1, "127.0.0.1", new MessageListenerImpl(1));
		AnonymousChat peer2 = new AnonymousChatImpl(2, "127.0.0.1", new MessageListenerImpl(2));
		AnonymousChat peer3 = new AnonymousChatImpl(3, "127.0.0.1", new MessageListenerImpl(3));
		AnonymousChat peer4 = new AnonymousChatImpl(4, "127.0.0.1", new MessageListenerImpl(4));
		AnonymousChat peer5 = new AnonymousChatImpl(5, "127.0.0.1", new MessageListenerImpl(5));
		peer0.createRoom("calcio");
		assertEquals(true, peer0.joinRoom("calcio")); 
		assertEquals(true, peer1.joinRoom("calcio"));
		assertEquals(true, peer2.joinRoom("calcio"));
		assertEquals(true, peer3.joinRoom("calcio"));
		assertEquals(true, peer4.joinRoom("calcio"));
		assertEquals(true, peer5.joinRoom("calcio"));
		assertEquals(true, peer5.sendMessage("calcio","hello"));
		assertEquals(true, peer3.leaveRoom("calcio"));
		assertEquals(true, peer4.leaveRoom("calcio"));
		assertEquals(true, peer5.leaveRoom("calcio"));
		assertEquals(true, peer3.sendMessage("calcio","hello"));

		
	}
	
}
