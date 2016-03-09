package org.contacts.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.soap.Detail;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

public class ContactBookProxy implements ContactBook {

    private static final String END_POINT = "http://localhost:8080/contactbook/soap";
    private static final String NAMESPACE = "http://example.org/contactbook";

    @Override
    public Contact findContact(long id) throws NotFoundException {
        Contact contact = new Contact();
        try {
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage request = messageFactory.createMessage();
            request.getSOAPHeader().detachNode();
            SOAPBody body = request.getSOAPBody();

            SOAPElement bodyElement = body.addChildElement("findContact", "tns", NAMESPACE);
            SOAPElement idElement = bodyElement.addChildElement("id");
            String stringID = new Long(id).toString();
            idElement.addTextNode(stringID);

            SOAPConnectionFactory connectionFactory
                    = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = connectionFactory.createConnection();
            SOAPMessage response = connection.call(request, END_POINT);
            connection.close();

            body = response.getSOAPBody();
            if (body.hasFault()) {
                SOAPFault fault = body.getFault();
                String faultCode = fault.getFaultCode();
                String faultString = fault.getFaultString();
                String faultActor = fault.getFaultActor();
                Detail detail = fault.getDetail();
                System.out.println(faultString);
                return contact;
            }
            bodyElement = (SOAPElement) body.getFirstChild();
            response.writeTo(System.out);
            SOAPElement contactElement = (SOAPElement) bodyElement.getFirstChild();
            Iterator<SOAPElement> iter = contactElement.getChildElements();
            contact.setName(iter.next().getTextContent());

            contact.setPhone(iter.next().getTextContent());
            contact.setEmail(iter.next().getTextContent());
            contact.setId(id);
            System.out.println(contact);

        } catch (SOAPException | IOException ex) {
            Logger.getLogger(ContactBookProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contact;
    }

    @Override
    public List<Contact> searchContacts(String name) {
        List<Contact> list = new ArrayList<>();
        try {
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage request = messageFactory.createMessage();
            request.getSOAPHeader().detachNode();
            SOAPBody body = request.getSOAPBody();

            SOAPElement bodyElement
                    = body.addChildElement("searchContacts", "tns", NAMESPACE);
            SOAPElement idElement = bodyElement.addChildElement("name");
            idElement.addTextNode(name);

            SOAPConnectionFactory connectionFactory
                    = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = connectionFactory.createConnection();
            SOAPMessage response = connection.call(request, END_POINT);
            connection.close();

            body = response.getSOAPBody();
            if (body.hasFault()) {
                SOAPFault fault = body.getFault();
                String faultCode = fault.getFaultCode();
                String faultString = fault.getFaultString();
                String faultActor = fault.getFaultActor();
                Detail detail = fault.getDetail();
                System.out.println(faultString);
            }
            bodyElement = (SOAPElement) body.getFirstChild();
            Iterator<SOAPElement> iter = bodyElement.getChildElements();
            while (iter.hasNext()) {
                Contact contact = extract(iter.next());
                list.add(contact);
            }

        } catch (SOAPException ex) {
            Logger.getLogger(ContactBookProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    private Contact extract(SOAPElement contactElement) {
        Contact contact = new Contact();
        Iterator<SOAPElement> iter = contactElement.getChildElements();
        String id = contactElement.getAttribute("id");
        contact.setId(Long.parseLong(id));
        contact.setName(iter.next().getTextContent());
        contact.setPhone(iter.next().getTextContent());
        contact.setEmail(iter.next().getTextContent());
        System.out.println(contact);
        return contact;
    }

    @Override
    public long createContact(String name, String phone, String email) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateContact(Contact contact) {
        try {
            /*<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://example.org/contactbook">
            <soapenv:Header/>
            <soapenv:Body>
            <con:updateContact>
            <contact id="20">
            <name>nnnn</name>
            <phone>pppp</phone>
            <email>eeee</email>
            </contact>
            </con:updateContact>
            </soapenv:Body>
            </soapenv:Envelope>*/
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage request = messageFactory.createMessage();
            request.getSOAPHeader().detachNode();
            SOAPBody body = request.getSOAPBody();
            
            SOAPElement bodyElement
                    = body.addChildElement("updateContact", "tns", NAMESPACE);
            SOAPElement contactElement = bodyElement.addChildElement("contact");
            contactElement.setAttribute("id", new Long(contact.getId()).toString());
            
            SOAPElement nameElement = contactElement.addChildElement("name");
            nameElement.addTextNode(contact.getName());
            
            SOAPElement phoneElement = contactElement.addChildElement("phone");
            phoneElement.addTextNode(contact.getPhone());
            
            SOAPElement emailElement = contactElement.addChildElement("email");
            emailElement.addTextNode(contact.getEmail());           
            
            try {
                request.writeTo(System.out);
            } catch (IOException ex) {
                Logger.getLogger(ContactBookProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
            SOAPConnectionFactory connectionFactory
                    = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = connectionFactory.createConnection();
            SOAPMessage response = connection.call(request, END_POINT);
            connection.close();
            System.out.println();
            body = response.getSOAPBody();
            if (body.hasFault()) {
                SOAPFault fault = body.getFault();
                String faultCode = fault.getFaultCode();
                String faultString = fault.getFaultString();
                String faultActor = fault.getFaultActor();
                Detail detail = fault.getDetail();
                System.out.println(faultString);
            }
            try {
                response.writeTo(System.out);
                System.out.println();
            } catch (IOException ex) {
                Logger.getLogger(ContactBookProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (SOAPException ex) {
            Logger.getLogger(ContactBookProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteContact(long id) {
        throw new UnsupportedOperationException();
    }

    
}
