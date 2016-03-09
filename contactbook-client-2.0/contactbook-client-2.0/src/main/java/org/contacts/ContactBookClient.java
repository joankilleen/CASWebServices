package org.contacts;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Response;
import javax.xml.ws.soap.SOAPFaultException;
import org.contacts.soap.Contact;
import org.contacts.soap.ContactBook;
import org.contacts.soap.ContactBookService;
import org.contacts.soap.CreateContactResponse;
import org.contacts.soap.NotFoundFault;
import org.contacts.soap.SearchContactsResponse;

public class ContactBookClient {

    private static final String ENDPOINT_ADDRESS = "http://localhost:8080/contactbook/soap";

    private static ContactBook contactBook;
    private static Scanner scanner;

    public static void main(String[] args) throws InterruptedException {
        ContactBookService service = new ContactBookService();
        contactBook = service.getContactBookPort();
        ((BindingProvider) contactBook).getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ENDPOINT_ADDRESS);
        scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1 Find contact");
            System.out.println("2 Search contacts");
            System.out.println("3 Create contact");
            System.out.println("4 Update contact");
            System.out.println("5 Delete contact");
            System.out.println("6 create contact aysnchronously");
            System.out.println("7 create contact aysnchronously using handler");
            System.out.println("8 Exit");
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
                    case 6: {
                        try {
                            createContactAysnc();
                        } catch (ExecutionException ex) {
                            Logger.getLogger(ContactBookClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    case 7: {
                        try {
                            createContactAysncHandler();
                        } catch (ExecutionException ex) {
                            Logger.getLogger(ContactBookClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    case 8:
                        System.exit(0);
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input");
            } catch (SOAPFaultException ex) {
                System.out.println("Error: " + ex.getFault().getFaultString());
            } catch (NotFoundFault | RuntimeException ex) {
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
        Response<SearchContactsResponse> response = contactBook.searchContactsAsync(name);
        System.out.print("Please wait...");
        try {
            while (!response.isDone()) {
                Thread.sleep(1000);
                System.out.print(".");
            }
            System.out.println();
            List<Contact> contacts = response.get().getContact();
            if (contacts.isEmpty()) {
                System.out.println("No contacts found");
                return;
            }
            for (Contact contact : contacts) {
                print(contact);
            }
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
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

    private static void createContactAysnc() throws InterruptedException, ExecutionException {
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
        Response<CreateContactResponse> response = contactBook.createContactAsync(name, phone, email);
        while (!response.isDone()) {
            System.out.println("waiting for response");
        }
        response.get().getId();
        System.out.println("Contact created (id=" + response.get().getId() + ")");
    }
    
    private static void createContactAysncHandler() throws InterruptedException, ExecutionException {
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
        contactBook.createContactAsync(name, phone, email, new CreateContactHandler());
        
        System.out.println("Contact being created....");
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
