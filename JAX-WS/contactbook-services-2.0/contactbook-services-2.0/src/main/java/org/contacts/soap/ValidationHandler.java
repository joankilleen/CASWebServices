package org.contacts.soap;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.xml.XMLConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.SOAPFaultException;
import org.xml.sax.SAXException;

public class ValidationHandler implements LogicalHandler<LogicalMessageContext> {

	private static final String SCHEMA_LOCATION = "/WEB-INF/wsdl/ContactBook.xsd";

	@Override
	public boolean handleMessage(LogicalMessageContext context) {
		boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (!outbound) {
			try {
				getValidator(context).validate(context.getMessage().getPayload());
			} catch (SAXException | IOException ex) {
				System.out.println(ex.getMessage());
				throw new SOAPFaultException(createFault(ex.getMessage()));
			}
		}
		return true;
	}

	@Override
	public boolean handleFault(LogicalMessageContext context) {
		return true;
	}

	@Override
	public void close(MessageContext context) {
	}

	private Validator getValidator(MessageContext context) {
		try {
			ServletContext servletContext = (ServletContext) context.get(MessageContext.SERVLET_CONTEXT);
			InputStream stream = servletContext.getResourceAsStream(SCHEMA_LOCATION);
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new StreamSource(stream));
			return schema.newValidator();
		} catch (SAXException ex) {
			throw new RuntimeException(ex);
		}
	}

	private SOAPFault createFault(String message) {
		try {
			SOAPFault fault = SOAPFactory.newInstance().createFault();
			fault.setFaultCode("Validation");
			fault.setFaultString("Invalid data");
			fault.addDetail().addChildElement("message").addTextNode(message);
			return fault;
		} catch (SOAPException ex) {
			throw new RuntimeException(ex);
		}
	}
}
