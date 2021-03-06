/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contacts.rest;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import org.contacts.impl.ContactBookImpl;
import org.contacts.impl.ContactEntity;
import org.contacts.impl.NotFoundException;

/**
 *
 * @author Joan
 */
@Path("contacts")
public class ContactBookResource {

    private ContactBookImpl contactBook = ContactBookImpl.getInstance();

    @GET
    @Path("{id}")
    public Contact findContact(@PathParam("id") long id) {
        System.out.println("find contcat : " + id);
        try {
            return convert(contactBook.find(id));
        } catch (NotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(),
                    Response.status(NOT_FOUND).entity(ex.getMessage()).build());
        }
    }
    /*@GET
public List<Contact> searchContacts(@QueryParam("name") String name){
    List<ContactEntity> list =  contactBook.search(name);
    List<Contact> listContact
    for (ContactEntity entity: list){
        
    }
}*/

    private Contact convert(ContactEntity entity) {
        Contact contact = new Contact();
        contact.setId(entity.getId());
        contact.setName(entity.getName());
        contact.setPhone(entity.getPhone());
        contact.setEmail(entity.getEmail());
        return contact;
    }

    private ContactEntity convert(Contact contact) {
        return new ContactEntity(contact.getId(), contact.getName(), contact.getPhone(), contact.getEmail());
    }

    @GET
	//@Compressed
	@Produces({APPLICATION_XML, APPLICATION_JSON, "text/csv"})
	public List<Contact> searchContacts(@QueryParam("name") String name) {
		List<Contact> contacts = new ArrayList<>();
		for (ContactEntity entity : contactBook.search(name)) {
			contacts.add(convert(entity));
		}
		return contacts;
	}

	@POST
	@Consumes({APPLICATION_XML, APPLICATION_JSON})
	@Produces({TEXT_PLAIN})
	public long addContact(Contact contact) {
		return contactBook.add(convert(contact));
	}

	@PUT
	@Path("{id}")
	@Consumes({APPLICATION_XML, APPLICATION_JSON})
	public void updateContact(@PathParam("id") long id, Contact contact) {
		try {
			contact.setId(id);
			contactBook.update(convert(contact));
		} catch (NotFoundException ex) {
			Response response = Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
			throw new WebApplicationException(ex.getMessage(), response);
		}
	}

	@DELETE
	@Path("{id}")
	public void deleteContact(@PathParam("id") long id) {
		contactBook.delete(id);
	}

	
        


}
