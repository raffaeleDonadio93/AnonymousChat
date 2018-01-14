package it.isislab.p2p.chat;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import net.tomp2p.dht.FutureGet;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.futures.BaseFutureAdapter;
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
	final private int nMax=100;
	final private int DEFAULT_MASTER_PORT=4000;
	private  Logger logger = Logger.getLogger("AnonymousChatImpl");

	public AnonymousChatImpl( int _id, String _master_peer, final MessageListener _listener) throws IOException
	{
	
		 peer= new PeerBuilder(Number160.createHash(_id)).ports(DEFAULT_MASTER_PORT+_id).start();
		 _dht = new PeerBuilderDHT(peer).start();	
		FutureBootstrap fb = peer.bootstrap().inetAddress(InetAddress.getByName(_master_peer)).ports(DEFAULT_MASTER_PORT).start();
		fb.awaitUninterruptibly();
		if(fb.isSuccess()) {
			peer.discover().peerAddress(fb.bootstrapTo().iterator().next()).start().awaitUninterruptibly();
		}
		
		logger.addHandler(new FileHandler(System.getProperty("user.dir")+"\\log\\logger_"+_id+".log",true));
		logger.setUseParentHandlers(false);
		
		peer.objectDataReply(new ObjectDataReply() {
		
		public Object reply(PeerAddress sender, Object request) throws Exception {
			Message mex = (Message) request;
			if(!equalsPeerAndress(mex.getDestination(),peer.peerAddress())){
		        logger.info("Dovrei inoltrare a:"+mex.getDestination().peerId());  
		        boolean forward=new Random().nextInt(2)==0 ? true :false;
				if(!forward){
					 sendToPeer(mex,mex.getDestination());
				     logger.info("forward=false: invio diretto a destinazione:"+mex.getDestination().peerId());
				}else{
				     forwardMessage(mex, sender);
				}	  
		       return null;
		    }
			else{
				return _listener.parseMessage(mex);
			}
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
				_dht.put(Number160.createHash(_room_name)).data(new Data(room)).start().awaitUninterruptibly();
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean sendMessage(String _room_name,String _text_message) {
		// TODO Auto-generated method stub
		try {
			FutureGet futureGet = _dht.get(Number160.createHash(_room_name)).start();
			futureGet.awaitUninterruptibly();
			Message mex= new Message(_room_name,_text_message);
			if (futureGet.isSuccess()) {
				Room room = (Room) futureGet.dataMap().values().iterator().next().object();
				for(PeerAddress p: room.getUsers())
				{
			     if(!equalsPeerAndress(p,peer.peerAddress())){
			   	    mex.setDestination(p);
			   	    boolean forward=new Random().nextInt(2)==0 ? true :false;
			        PeerAddress dest;
			   	    if(!forward){
					 dest=p;
					 logger.info("forward=false: invio diretto a destinazione:"+p.peerId());
					}else{
				     PeerAddress p1 = room.getRandomPeer(peer.peerAddress(),p);
				     dest=p1;
					 logger.info("forward=true: inoltro a:"+p1.peerId());
					}
					sendToPeer(mex,dest);
			     }
				}
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void sendToPeer(Message mex,PeerAddress destPeer){
		FutureDirect futureDirect = _dht.peer().sendDirect(destPeer).object(mex).start();
		futureDirect.addListener(new BaseFutureAdapter<FutureDirect>() {
          	@Override
			public void operationComplete(FutureDirect future) throws Exception {
				if (future.isSuccess()) {
                   logger.info("Ho inviato a "+mex.getDestination());
				}
			}
         });
	}
	
   public boolean equalsPeerAndress(PeerAddress p1,PeerAddress p2){
	   return  p1.peerId().equals(p2.peerId());
   }
	
   public void forwardMessage(Message mex,PeerAddress sender){
	   try{
		     FutureGet futureGet = _dht.get(Number160.createHash(mex.getNameRoom())).start();
			 futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
	         public void operationComplete(FutureGet future) throws Exception {
				 if(future.isSuccess()) { 
				   Room room = (Room) futureGet.dataMap().values().iterator().next().object();
                   PeerAddress peerDest = room.getRandomPeer(peer.peerAddress(),sender);
				   sendToPeer(mex,peerDest);
				   logger.info("forward=true: inoltro a:"+peerDest.peerId());
				  }
			 }});
		  }catch(Exception e){
			e.printStackTrace();
		}
   }
}