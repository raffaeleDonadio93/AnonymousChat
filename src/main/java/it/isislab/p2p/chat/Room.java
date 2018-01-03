package it.isislab.p2p.chat;

import java.io.Serializable;
import java.util.HashSet;

import net.tomp2p.peers.PeerAddress;

public class Room implements Serializable {

	private static final long serialVersionUID = -8377043372564052802L;
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
		for(PeerAddress t: users)
			if(t.equals(p))
			{
				users.remove(p);
				return true;
			}
		return false;
	}
}
