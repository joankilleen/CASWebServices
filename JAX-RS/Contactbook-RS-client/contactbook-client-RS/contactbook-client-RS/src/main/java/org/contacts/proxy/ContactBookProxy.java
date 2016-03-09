package org.contacts.proxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ContactBookProxy implements ContactBook {

    String resourceURI = "http://localhost:8080/contactbook/rest/contacts";
    Client client = ClientBuilder.newClient();
    WebTarget webTarget = client.target(resourceURI);

    /*private static final String ENDPOINT_ADDRESS = "http://localhost:8080/contactbook/soap";
     private static final String TARGET_NAMESPACE = "http://example.org/contactbook";*/

    /*private final SOAPFactory soapFactory;
     private final MessageFactory messageFactory;
     private final SOAPConnection connection;*/
    public ContactBookProxy() {
        /*try {
         soapFactory = SOAPFactory.newInstance();
         messageFactory = MessageFactory.newInstance();
         connection = SOAPConnectionFactory.newInstance().createConnection();
         } catch (SOAPException ex) {
         throw new RuntimeException(ex);
         }*/
    }

    @Override
    public Contact findContact(long id) throws NotFoundException {
        Contact contact = null;
        String idString = String.valueOf(id);

        Response response = webTarget.path(idString).request().get();
        if (response.getStatus() == Status.OK.getStatusCode()) {
            contact = response.readEntity(Contact.class);
            System.out.println("contact found: " + contact.getEmail() + " " + contact.getName() + " " + contact.getPhone());
        }
        return contact;

    }
    /*
     @Override
     public List<Contact> searchContacts(String name) {
     try {
     SOAPMessage request = messageFactory.createMessage();
     SOAPElement bodyElement = request.getSOAPBody().addChildElement("searchContacts", "tns", TARGET_NAMESPACE);
     bodyElement.addChildElement("name").addTextNode(name);
     SOAPBody responseBody = connection.call(request, ENDPOINT_ADDRESS).getSOAPBody();
     if (responseBody.hasFault()) {
     throw new RuntimeException(resonseBody.getFault().getFaultString());
     }
     bodyElement = (SOAPElement) responseBody.getFirstChild();
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
     SOAPMessage request = messageFactory.createMessage();
     SOAPElement bodyElement = request.getSOAPBody().addChildElement("createContact", "tns", TARGET_NAMESPACE);
     bodyElement.addChildElement("name").addTextNode(name);
     if (phone != null) {
     bodyElement.addChildElement("phone").addTextNode(phone);
     }
     if (email != null) {
     bodyElement.addChildElement("email").addTextNode(email);
     }
     SOAPBody responseBody = connection.call(request, ENDPOINT_ADDRESS).getSOAPBody();
     if (responseBody.hasFault()) {
     throw new RuntimeException(responseBody.getFault().getFaultString());
     }
     bodyElement = (SOAPElement) responseBody.getFirstChild();
     SOAPElement idElement = (SOAPElement) bodyElement.getFirstChild();
     return Long.parseLong(idElement.getValue());
     } catch (SOAPException ex) {
     throw new RuntimeException(ex);
     }
     }

     @Override
     public void updateContact(Contact contact) throws NotFoundException {
     try {
     SOAPMessage request = messageFactory.createMessage();
     SOAPElement bodyElement = request.getSOAPBody().addChildElement("updateContact", "tns", TARGET_NAMESPACE);
     SOAPElement contactElement = bodyElement.addChildElement("contact");
     contactElement.setAttribute("id", String.valueOf(contact.getId()));
     contactElement.addChildElement("name").addTextNode(contact.getName());
     if (contact.getPhone() != null) {
     contactElement.addChildElement("phone").addTextNode(contact.getPhone());
     }
     if (contact.getEmail() != null) {
     contactElement.addChildElement("email").addTextNode(contact.getEmail());
     }
     SOAPBody responseBody = connection.call(request, ENDPOINT_ADDRESS).getSOAPBody();
     if (responseBody.hasFault()) {
     SOAPFault fault = responseBody.getFault();
     if (fault.getDetail().getFirstChild().getNodeName().equals("NotFoundFault")) {
     throw new NotFoundException(fault.getFaultString());
     } else {
     throw new RuntimeException(fault.getFaultString());
     }
     }
     } catch (SOAPException ex) {
     throw new RuntimeException(ex);
     }
     }

     @Override
     public void deleteContact(long id) {
     try {
     SOAPMessage request = messageFactory.createMessage();
     SOAPElement bodyElement = request.getSOAPBody().addChildElement("deleteContact", "tns", TARGET_NAMESPACE);
     bodyElement.addChildElement("id").addTextNode(String.valueOf(id));
     connection.call(request, ENDPOINT_ADDRESS).getSOAPBody();
     } catch (SOAPException ex) {
     throw new RuntimeException(ex);
     }
     }

     private Contact convert(SOAPElement element) {
     Contact contact = new Contact();
     contact.setId(Long.parseLong(element.getAttribute("id")));
     Iterator<SOAPElement> iter = element.getChildElements();
     while (iter.hasNext()) {
     SOAPElement childElement = iter.next();
     switch (childElement.getTagName()) {
     case "name":
     contact.setName(childElement.getValue());
     break;
     case "phone":
     contact.setPhone(childElement.getValue());
     break;
     case "email":
     contact.setEmail(childElement.getValue());
     break;
     }
     }
     return contact;
     }*/

    @Override
    public List<Contact> searchContacts(String name) {
        List<Contact> contacts = new ArrayList<Contact>();
        Response response = webTarget.queryParam("name", name).request(APPLICATION_XML).get();
            if (response.getStatus() == Status.OK.getStatusCode()) {
                contacts = response.readEntity(new GenericType<List<Contact>>(){ });
            }
            
        return contacts;

    }

    @Override
    public long createContact(String name, String phone, String email) {
        Contact contact = new Contact();
        contact.setEmail(email);
        contact.setPhone(phone);
        contact.setName(name);
        Response response = webTarget.request(TEXT_PLAIN).post(Entity.xml(contact));
        String id = response.readEntity(String.class);
        return Long.parseLong(id);
        
    }

    @Override
    public void updateContact(Contact contact) throws NotFoundException {
        String id = String.valueOf(contact.getId());
        Response response = webTarget.path(id).request().put(Entity.xml(contact));
        
        System.out.println(response.getStatus());
    }

    @Override
    public void deleteContact(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
