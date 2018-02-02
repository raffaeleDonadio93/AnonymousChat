package it.ssd.p2p.anonymouschat;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;


import net.tomp2p.peers.PeerAddress;

public class Room implements Serializable {
	
	private static final long serialVersionUID = 818810810051412014L;
	private String name;
	private HashSet<PeerAddress> users;
	
	public Room(String name) {
		this.name=name;
		users=new HashSet<PeerAddress>();
	}

	public HashSet<PeerAddress> getUsers() {
		return users;
	}
	
	public void setUsers(HashSet<PeerAddress> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addPeer(PeerAddress p) {
		this.users.add(p);
	}
	
	public boolean removePeer(PeerAddress p) {
		return users.remove(p);
	}

	public PeerAddress getRandomPeer(PeerAddress p1,PeerAddress p2){
	    int value =(new Random()).nextInt(users.size()-2);
	    return (PeerAddress) users.stream().filter(x->!x.peerId().equals(p1.peerId())&& !x.peerId().equals(p2.peerId())).toArray()[value];	
	}
}
