package it.ssd.p2p.anonymouschat;

import java.io.Serializable;
import java.time.LocalDateTime;
import net.tomp2p.peers.PeerAddress;

public class Messaggio implements Serializable{

	private static final long serialVersionUID = -6785859773397922306L;
	private String nameRoom;
	private String message;
	private LocalDateTime dateOrigin;
	private PeerAddress destination;
	private boolean flag;
	
	public Messaggio(String message, String nameRoom) {
		this.message = message;
		this.nameRoom = nameRoom;
		this.destination = null;
		this.dateOrigin = LocalDateTime.now();
		this.flag = true;
	}
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getNameRoom() {
		return nameRoom;
	}

	public void setNameRoom(String nameRoom) {
		this.nameRoom = nameRoom;
	}

	public LocalDateTime getDateOrigin() {
		return dateOrigin;
	}

	public void setDateOrigin(LocalDateTime dateOrigin) {
		this.dateOrigin = dateOrigin;
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
		return getClass().getName() + "[message=" + message + "--- destination=" + destination + " --- time="+ dateOrigin+"]";
	}
	
	
}
