package it.isislab.p2p.chat;

import java.io.Serializable;

import net.tomp2p.peers.PeerAddress;

public class Pacchetto implements Serializable{

	public String message;
	public PeerAddress destination;
	@Override
	public String toString() {
		return "Pacchetto [message=" + message + ", destination=" + destination + "]";
	}
	
	
}
