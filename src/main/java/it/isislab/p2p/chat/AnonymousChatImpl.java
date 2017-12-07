package it.isislab.p2p.chat;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;

import net.tomp2p.dht.FutureGet;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.futures.FutureBootstrap;
import net.tomp2p.futures.FutureDirect;
import net.tomp2p.p2p.Peer;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;
import net.tomp2p.rpc.ObjectDataReply;
import net.tomp2p.storage.Data;

public class AnonymousChatImpl implements AnonymousChat{
	final private Peer peer;
	final private PeerDHT _dht;
	final private int nMax=10;
	final private int DEFAULT_MASTER_PORT=4000;
	
	final private ArrayList<String> s_topics=new ArrayList<String>();

	public AnonymousChatImpl( int _id, String _master_peer, final MessageListener _listener) throws IOException
	{
		 peer= new PeerBuilder(Number160.createHash(_id)).ports(DEFAULT_MASTER_PORT+_id).start();
		 _dht = new PeerBuilderDHT(peer).start();	
		FutureBootstrap fb = peer.bootstrap().inetAddress(InetAddress.getByName(_master_peer)).ports(DEFAULT_MASTER_PORT).start();
		fb.awaitUninterruptibly();
		if(fb.isSuccess()) {
			peer.discover().peerAddress(fb.bootstrapTo().iterator().next()).start().awaitUninterruptibly();
		}
		peer.objectDataReply(new ObjectDataReply() {
		
		public Object reply(PeerAddress sender, Object request) throws Exception {
			System.out.println("ho ricevuto un messaggio da:"+sender.peerId()+"messaggio:"+request.toString());
			return request;
		}
	});
		
	
	}

	public boolean createRoom(String _room_name) {
		// TODO Auto-generated method stub
		try {
			Room r=new Room(_room_name,nMax);
			FutureGet futureGet = _dht.get(Number160.createHash(_room_name)).start();
			futureGet.awaitUninterruptibly();
			if (futureGet.isSuccess() && futureGet.isEmpty()) 
				_dht.put(Number160.createHash(_room_name)).data(new Data(r)).start().awaitUninterruptibly();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean joinRoom(String _room_name) {
		// TODO Auto-generated method stub
		try {
			FutureGet futureGet = _dht.get(Number160.createHash(_room_name)).start();
			futureGet.awaitUninterruptibly();
			if (futureGet.isSuccess()) {
				Room room;
				room = (Room) futureGet.dataMap().values().iterator().next().object();
				room.addPeer(peer.peerAddress());
				_dht.put(Number160.createHash(_room_name)).data(new Data(room)).start().awaitUninterruptibly();
				
				return true;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean leaveRoom(String _room_name) {
		// TODO Auto-generated method stub
		try {
			FutureGet futureGet = _dht.get(Number160.createHash(_room_name)).start();
			futureGet.awaitUninterruptibly();
			if (futureGet.isSuccess()) {
				Room room;
				room = (Room) futureGet.dataMap().values().iterator().next().object();
				room.removePeer(peer.peerAddress());
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean sendMessage(String _room_name, String _text_message) {
		// TODO Auto-generated method stub
		try {
			FutureGet futureGet = _dht.get(Number160.createHash(_room_name)).start();
			futureGet.awaitUninterruptibly();
			if (futureGet.isSuccess()) {
				Room room;
				room = (Room) futureGet.dataMap().values().iterator().next().object();
				for(PeerAddress p: room.getUsers())
				{
					FutureDirect futureDirect = _dht.peer().sendDirect(p).object(_text_message).start();
					System.out.println("sto mandando un messaggio a tutti gli altri");
					futureDirect.awaitUninterruptibly();
				}
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}