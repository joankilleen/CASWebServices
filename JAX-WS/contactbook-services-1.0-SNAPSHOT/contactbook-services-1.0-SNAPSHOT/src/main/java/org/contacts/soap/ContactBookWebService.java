/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contacts.soap;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.HandlerChain;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import org.contacts.impl.Contact;
import org.contacts.impl.ContactBookImpl;
import org.contacts.impl.NotFoundException;

/**
 *
 * @author Joan
 */
@WebService(name="ContactBook",
portName="ContactBookPort", serviceName="ContactBookService",
targetNamespace="http://example.org/contactbook")
@SOAPBinding(style=Style.DOCUMENT, use=Use.LITERAL,
parameterStyle=ParameterStyle.WRAPPED)
@HandlerChain(file="handler-chain.xml")
public class ContactBookWebService {
    
    private ContactBookImpl impl = ContactBookImpl.getInstance();
    @WebMethod(operationName="findContact")
    @WebResult(name="contact")
    public Contact findContact(@WebParam(name="id")long id) throws NotFoundFault{
        try {
            return impl.find(id);
        } catch (NotFoundException ex) {
            throw new NotFoundFault(ex.getMessage());
        }
    }
    @WebMethod(operationName="searchContacts")
    @WebResult(name="contact")
    public List<Contact> searchContacts(@WebParam(name="name")String name){
            return impl.search(name);
    }
    @WebMethod(operationName="createContact")
    @WebResult(name="id")
    public long createContact(@WebParam(name="name")String name, @WebParam(name="phone")String phone, @WebParam(name="email")String email) {
        return impl.create(name, phone, email);
    }
    @WebMethod(operationName="updateContact")   
    public void updateContact(@WebParam(name="contact")Contact contact) throws NotFoundFault{
        try {
            impl.update(contact);
        } catch (NotFoundException ex) {
           throw new NotFoundFault(ex.getMessage());
        }
        
    }
    @Oneway @WebMethod(operationName="deleteContact")
    public void deleteContact(@WebParam(name="id")long id){
        impl.delete(id);
    }
}
