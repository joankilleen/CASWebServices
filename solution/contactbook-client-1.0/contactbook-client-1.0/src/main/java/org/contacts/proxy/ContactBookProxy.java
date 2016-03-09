package org.contacts.proxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;

public class ContactBookProxy implements ContactBook {

	private static final String ENDPOINT_ADDRESS = "http://localhost:8080/contactbook/soap";
	private static final String TARGET_NAMESPACE = "http://example.org/contactbook";

	private final SOAPFactory soapFactory;

	public ContactBookProxy() {
		try {
			this.soapFactory = SOAPFactory.newInstance();
		} catch (SOAPException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Contact findContact(long id) throws NotFoundException {
		try {
			SOAPElement idElement = soapFactory.createElement("id").addTextNode(String.valueOf(id));
			SOAPBody responseBody = sendRequest("findContact", idElement);
			if (responseBody.hasFault()) {
				throw new NotFoundException(responseBody.getFault().getFaultString());
			}
			SOAPElement bodyElement = (SOAPElement) responseBody.getFirstChild();
			SOAPElement contactElement = (SOAPElement) bodyElement.getFirstChild();
			return convert(contactElement);
		} catch (SOAPException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<Contact> searchContacts(String name) {
		try {
			SOAPElement nameElement = soapFactory.createElement("name").addTextNode(name);
			SOAPBody responseBody = sendRequest("searchContacts", nameElement);
			SOAPElement bodyElement = (SOAPElement) responseBody.getFirstChild();
			List<Contact> contacts = new ArrayList<>();
			for (Iterator<SOAPElement> iter = bodyElement.getChildElements(); iter.hasNext();) {
				contacts.add(convert(iter.next()));
			}
			return contacts;
		} catch (SOAPException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public long createContact(String name, String phone, String email) {
		try {
			SOAPElement nameElement = soapFactory.createElement("name").addTextNode(name);
			SOAPElement phoneElement = soapFactory.createElement("phone").addTextNode(phone);
			SOAPElement emailElement = soapFactory.createElement("email").addTextNode(email);
			SOAPBody responseBody = sendRequest("createContact", nameElement, phoneElement, emailElement);
			SOAPElement bodyElement = (SOAPElement) responseBody.getFirstChild();
			SOAPElement idElement = (SOAPElement) bodyElement.getFirstChild();
			return Long.parseLong(idElement.getValue());
		} catch (SOAPException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void updateContact(Contact contact) throws NotFoundException {
		try {
			SOAPElement contactElement = soapFactory.createElement("contact");
			contactElement.addAttribute(soapFactory.createName("id"), String.valueOf(contact.getId()));
			contactElement.addChildElement("name").addTextNode(contact.getName());
			contactElement.addChildElement("phone").addTextNode(contact.getPhone());
			contactElement.addChildElement("email").addTextNode(contact.getEmail());
			SOAPBody responseBody = sendRequest("updateContact", contactElement);
			if (responseBody.hasFault()) {
				throw new NotFoundException(responseBody.getFault().getFaultString());
			}
		} catch (SOAPException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void deleteContact(long id) {
		try {
			SOAPElement idElement = soapFactory.createElement("id").addTextNode(String.valueOf(id));
			sendRequest("deleteContact", idElement);
		} catch (SOAPException ex) {
			throw new RuntimeException(ex);
		}
	}

	private SOAPBody sendRequest(String operation, SOAPElement... elements) {
		try {
			SOAPMessage request = MessageFactory.newInstance().createMessage();
			request.getSOAPHeader().detachNode();
			SOAPBody body = request.getSOAPBody();
			SOAPElement bodyElement = body.addChildElement(operation, "tns", TARGET_NAMESPACE);
			for (SOAPElement element : elements) {
				bodyElement.addChildElement(element);
			}
			SOAPConnection connection = SOAPConnectionFactory.newInstance().createConnection();
			SOAPMessage response = connection.call(request, ENDPOINT_ADDRESS);
			connection.close();
			return response == null ? null : response.getSOAPBody();
		} catch (SOAPException ex) {
			throw new RuntimeException(ex);
		}
	}

	private Contact convert(SOAPElement element) throws SOAPException {
		Contact contact = new Contact();
		contact.setId(Long.parseLong(element.getAttributeValue(soapFactory.createName("id"))));
		Iterator<SOAPElement> iter = element.getChildElements();
		contact.setName(iter.next().getTextContent());
		contact.setPhone(iter.next().getTextContent());
		contact.setEmail(iter.next().getTextContent());
		return contact;
	}
}
