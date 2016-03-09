package org.contacts.soap;

import javax.xml.ws.WebFault;

@WebFault(name = "NotFoundFault")
public class NotFoundFault extends Exception {

	public NotFoundFault(String message) {
		super(message);
	}

	public String getFaultInfo() {
		return getMessage();
	}
}
