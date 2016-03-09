package org.contacts.soap;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
import org.contacts.impl.ContactBookImpl;
import org.contacts.impl.ContactEntity;
import org.contacts.impl.NotFoundException;

@WebService(name = "ContactBook", portName = "ContactBookPort", serviceName = "ContactBookService",
		targetNamespace = "http://example.org/contactbook")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
@HandlerChain(file = "handler-chain.xml")
public class ContactBookWebService {

	private ContactBookImpl contactBook;

	@PostConstruct
	protected void init() {
		contactBook = ContactBookImpl.getInstance();
	}

	@WebMethod(operationName = "findContact")
	@WebResult(name = "contact")
	public Contact findContact(@WebParam(name = "id") long id) throws NotFoundFault {
		try {
			return convert(contactBook.find(id));
		} catch (NotFoundException ex) {
			throw new NotFoundFault(ex.getMessage());
		}
	}

	@WebMethod(operationName = "searchContacts")
	@WebResult(name = "contact")
	public List<Contact> searchContacts(@WebParam(name = "name") String name) {
		List<Contact> contacts = new ArrayList<>();
		for (ContactEntity entity : contactBook.search(name)) {
			contacts.add(convert(entity));
		}
		return contacts;
	}

	@WebMethod(operationName = "createContact")
	@WebResult(name = "id")
	public long createContact(@WebParam(name = "name") String name,
			@WebParam(name = "phone") String phone, @WebParam(name = "email") String email) {
		return contactBook.create(name, phone, email);
	}

	@WebMethod(operationName = "updateContact")
	public void updateContact(@WebParam(name = "contact") Contact contact) throws NotFoundFault {
		try {
			contactBook.update(convert(contact));
		} catch (NotFoundException ex) {
			throw new NotFoundFault(ex.getMessage());
		}
	}

	@Oneway
	@WebMethod(operationName = "deleteContact")
	public void deleteContact(@WebParam(name = "id") long id) {
		contactBook.delete(id);
	}

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
}
