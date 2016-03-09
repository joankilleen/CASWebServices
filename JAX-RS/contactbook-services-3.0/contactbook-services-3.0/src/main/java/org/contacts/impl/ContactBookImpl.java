package org.contacts.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactBookImpl {

	private static ContactBookImpl instance;
	private final Map<Long, ContactEntity> contacts;
	private long sequence;

	public static ContactBookImpl getInstance() {
		if (instance == null) {
			instance = new ContactBookImpl();
		}
		return instance;
	}

	private ContactBookImpl() {
		contacts = new HashMap<>();
		create("Alice Smith", "+1 123-456-7890", "alice@example.org");
		create("Robert Smith", "+1 123-456-7890", "bob@example.org");
	}

	public ContactEntity find(long id) throws NotFoundException {
		log("find contact with id " + id);
		if (!contacts.containsKey(id)) {
			throw new NotFoundException("Contact not found");
		}
		return contacts.get(id);
	}

	public List<ContactEntity> search(String name) {
		log("search contacts with name " + name);
		List<ContactEntity> results = new ArrayList<>();
		for (ContactEntity contact : contacts.values()) {
			if (contact.getName().toLowerCase().contains(name.toLowerCase())) {
				results.add(contact);
			}
		}
		delay();
		return results;
	}

	public long create(String name, String phone, String email) {
		log("create contact with name " + name);
		ContactEntity contact = new ContactEntity(++sequence, name, phone, email);
		contacts.put(contact.getId(), contact);
		return contact.getId();
	}

	public long create(String name, String phone, String email, String twitter) {
		log("create contact with name " + name);
		ContactEntity contact = new ContactEntity(++sequence, name, phone, email, twitter);
		contacts.put(contact.getId(), contact);
		return contact.getId();
	}

	public long add(ContactEntity contact) {
		log("add contact with name " + contact.getName());
		contact.setId(++sequence);
		contacts.put(contact.getId(), contact);
		return contact.getId();
	}

	public void update(ContactEntity contact) throws NotFoundException {
		log("update contact with id " + contact.getId());
		if (!contacts.containsKey(contact.getId())) {
			throw new NotFoundException("Contact not found");
		}
		contacts.put(contact.getId(), contact);
	}

	public void delete(long id) {
		log("delete contact with id " + id);
		contacts.remove(id);
	}

	private void log(String msg) {
		System.out.println(ContactBookImpl.class.getSimpleName() + ": " + msg);
	}

	private void delay() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
		}
	}
}
