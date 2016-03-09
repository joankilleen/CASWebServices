package org.contacts.impl;

public class ContactEntity {

	private long id;
	private String name;
	private String phone;
	private String email;
	private String twitter;

	public ContactEntity(long id, String name, String phone, String email) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	public ContactEntity(long id, String name, String phone, String email, String twitter) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.twitter = twitter;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
}
