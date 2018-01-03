package it.isislab.p2p.chat;

import java.io.Serializable;

import net.tomp2p.peers.PeerAddress;

public class Pacchetto implements Serializable{

	private static final long serialVersionUID = -6785859773397922306L;
	private String message;
	private PeerAddress destination;
	
	public Pacchetto() {
		message = null;
		destination = null; 
	}
	
	public Pacchetto(String message, PeerAddress destination) {
		this.message = message;
		this.destination = destination;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PeerAddress getDestination() {
		return destination;
	}

	public void setDestination(PeerAddress destination) {
		this.destination = destination;
	}

	public String toString() {
		return getClass().getName() + "[message=" + message + ", destination=" + destination + "]";
	}
	
	
}
