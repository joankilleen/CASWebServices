package org.contacts;

import java.util.List;
import java.util.Scanner;
import javax.xml.ws.BindingProvider;
import org.contacts.soap.Contact;
import org.contacts.soap.ContactBook;
import org.contacts.soap.ContactBookService;
import org.contacts.soap.NotFoundFault;

public class ContactBookClient {

    private static ContactBookService service = new ContactBookService();
    private static ContactBook contactBook = service.getContactBookPort();
    private static Scanner scanner;

    public static void main(String[] args) {
        
        ((BindingProvider) contactBook).getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                "http://localhost:8080/contactbook/soap");
        
        scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1 Find contact");
            System.out.println("2 Search contacts");
            System.out.println("3 Create contact");
            System.out.println("4 Update contact");
            System.out.println("5 Delete contact");
            System.out.println("6 Exit");
            System.out.print("> ");
            try {
                int action = Integer.parseInt(scanner.nextLine());
                System.out.println();
                switch (action) {
                    case 1:
                        findContact();
                        break;
                    case 2:
                        searchContacts();
                        break;
                    case 3:
                        createContact();
                        break;
                    case 4:
                        updateContact();
                        break;
                    case 5:
                        deleteContact();
                        break;
                    case 6:
                        System.exit(0);
                }
            } catch (NumberFormatException | NotFoundFault ex) {
                System.out.println("Invalid input");
            } catch (RuntimeException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            System.out.println();
        }
    }

    private static void findContact() throws NotFoundFault {
        System.out.print("Id: ");
        long id = Long.parseLong(scanner.nextLine());
        Contact contact = contactBook.findContact(id);
        print(contact);
    }

    private static void searchContacts() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        List<Contact> contacts = contactBook.searchContacts(name);
        if (contacts.isEmpty()) {
            System.out.println("No contacts found");
            return;
        }
        for (Contact contact : contacts) {
            print(contact);
        }
    }

    private static void createContact() {
        System.out.print("Name:  ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println("No contact created");
            return;
        }
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        if (phone.isEmpty()) {
            phone = null;
        }
        System.out.print("Email: ");
        String email = scanner.nextLine();
        if (email.isEmpty()) {
            email = null;
        }
        long id = contactBook.createContact(name, phone, email);
        System.out.println("Contact created (id=" + id + ")");
    }

    private static void updateContact() throws NotFoundFault {
        System.out.print("Id: ");
        long id = Long.parseLong(scanner.nextLine());
        Contact contact = contactBook.findContact(id);
        print(contact);
        System.out.print("Name:  ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            contact.setName(name);
        }
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) {
            contact.setPhone(phone);
        }
        System.out.print("Email: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            contact.setEmail(email);
        }
        contactBook.updateContact(contact);
        System.out.println("Contact updated");
    }

    private static void deleteContact() {
        System.out.print("Id: ");
        long id = Long.parseLong(scanner.nextLine());
        contactBook.deleteContact(id);
        System.out.println("Contact deleted");
    }

    private static void print(Contact contact) {
        System.out.print(contact.getId() + ": " + contact.getName());
        if (contact.getPhone() != null) {
            System.out.print(", " + contact.getPhone());
        }
        if (contact.getEmail() != null) {
            System.out.print(", " + contact.getEmail());
        }
        System.out.println();
    }
}
