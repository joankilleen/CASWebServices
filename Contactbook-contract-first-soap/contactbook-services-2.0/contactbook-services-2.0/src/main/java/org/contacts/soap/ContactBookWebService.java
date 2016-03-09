package org.contacts.soap;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.jws.WebService;
import org.contacts.impl.ContactBookImpl;
import org.contacts.impl.ContactEntity;
import org.contacts.impl.NotFoundException;

@WebService(endpointInterface = "org.contacts.soap.ContactBook", wsdlLocation = "ContactBook.wsdl",
		portName = "ContactBookPort", serviceName = "ContactBookService", targetNamespace = "http://example.org/contactbook")
public class ContactBookWebService implements ContactBook {

	private ContactBookImpl contactBook;

	@PostConstruct
	protected void init() {
		contactBook = ContactBookImpl.getInstance();
	}

	@Override
	public Contact findContact(long id) throws NotFoundFault {
		try {
			return convert(contactBook.find(id));
		} catch (NotFoundException ex) {
			throw new NotFoundFault(ex.getMessage(), null);
		}
	}

	@Override
	public List<Contact> searchContacts(String name) {
		List<Contact> contacts = new ArrayList<>();
		for (ContactEntity entity : contactBook.search(name)) {
			contacts.add(convert(entity));
		}
		return contacts;
	}

	@Override
	public long createContact(String name, String phone, String email) {
		return contactBook.create(name, phone, email);
	}

	@Override
	public void updateContact(Contact contact) throws NotFoundFault {
		try {
			contactBook.update(convert(contact));
		} catch (NotFoundException ex) {
			throw new NotFoundFault(ex.getMessage(), null);
		}
	}

	@Override
	public void deleteContact(long id) {
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
