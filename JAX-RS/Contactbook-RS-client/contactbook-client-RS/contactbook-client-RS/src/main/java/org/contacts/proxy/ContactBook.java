package org.contacts.proxy;

import java.util.List;

public interface ContactBook {

	public Contact findContact(long id) throws NotFoundException;

	public List<Contact> searchContacts(String name);

	public long createContact(String name, String phone, String email);

	public void updateContact(Contact contact) throws NotFoundException;

	public void deleteContact(long id);
}
