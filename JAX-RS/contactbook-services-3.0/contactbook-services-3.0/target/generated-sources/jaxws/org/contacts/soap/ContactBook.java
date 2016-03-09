
package org.contacts.soap;

import java.util.List;
import javax.jws.HandlerChain;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ContactBook", targetNamespace = "http://example.org/contactbook")
@HandlerChain(file = "ContactBook_handler.xml")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ContactBook {


    /**
     * 
     * @param id
     * @return
     *     returns org.contacts.soap.Contact
     * @throws NotFoundFault
     */
    @WebMethod
    @WebResult(name = "contact", targetNamespace = "")
    @RequestWrapper(localName = "findContact", targetNamespace = "http://example.org/contactbook", className = "org.contacts.soap.FindContact")
    @ResponseWrapper(localName = "findContactResponse", targetNamespace = "http://example.org/contactbook", className = "org.contacts.soap.FindContactResponse")
    public Contact findContact(
        @WebParam(name = "id", targetNamespace = "")
        long id)
        throws NotFoundFault
    ;

    /**
     * 
     * @param name
     * @return
     *     returns java.util.List<org.contacts.soap.Contact>
     */
    @WebMethod
    @WebResult(name = "contact", targetNamespace = "")
    @RequestWrapper(localName = "searchContacts", targetNamespace = "http://example.org/contactbook", className = "org.contacts.soap.SearchContacts")
    @ResponseWrapper(localName = "searchContactsResponse", targetNamespace = "http://example.org/contactbook", className = "org.contacts.soap.SearchContactsResponse")
    public List<Contact> searchContacts(
        @WebParam(name = "name", targetNamespace = "")
        String name);

    /**
     * 
     * @param twitter
     * @param phone
     * @param name
     * @param email
     * @return
     *     returns long
     */
    @WebMethod
    @WebResult(name = "id", targetNamespace = "")
    @RequestWrapper(localName = "createContact", targetNamespace = "http://example.org/contactbook", className = "org.contacts.soap.CreateContact")
    @ResponseWrapper(localName = "createContactResponse", targetNamespace = "http://example.org/contactbook", className = "org.contacts.soap.CreateContactResponse")
    public long createContact(
        @WebParam(name = "name", targetNamespace = "")
        String name,
        @WebParam(name = "phone", targetNamespace = "")
        String phone,
        @WebParam(name = "email", targetNamespace = "")
        String email,
        @WebParam(name = "twitter", targetNamespace = "")
        String twitter);

    /**
     * 
     * @param contact
     * @throws NotFoundFault
     */
    @WebMethod
    @RequestWrapper(localName = "updateContact", targetNamespace = "http://example.org/contactbook", className = "org.contacts.soap.UpdateContact")
    @ResponseWrapper(localName = "updateContactResponse", targetNamespace = "http://example.org/contactbook", className = "org.contacts.soap.UpdateContactResponse")
    public void updateContact(
        @WebParam(name = "contact", targetNamespace = "")
        Contact contact)
        throws NotFoundFault
    ;

    /**
     * 
     * @param id
     */
    @WebMethod
    @Oneway
    @RequestWrapper(localName = "deleteContact", targetNamespace = "http://example.org/contactbook", className = "org.contacts.soap.DeleteContact")
    public void deleteContact(
        @WebParam(name = "id", targetNamespace = "")
        long id);

}
