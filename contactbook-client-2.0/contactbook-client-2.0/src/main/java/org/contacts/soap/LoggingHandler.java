package org.contacts.soap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		log(context);
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		log(context);
		return true;
	}

	@Override
	public void close(MessageContext context) {
	}

	private void log(SOAPMessageContext context) {
		try {
			boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			context.getMessage().writeTo(stream);
			System.out.println("Message " + (outbound ? "sent" : "received") + ": " + stream.toString());
		} catch (SOAPException | IOException ex) {
		}
	}
}
