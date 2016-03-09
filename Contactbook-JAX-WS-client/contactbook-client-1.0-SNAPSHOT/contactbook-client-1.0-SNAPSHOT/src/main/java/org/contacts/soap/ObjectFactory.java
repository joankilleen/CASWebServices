
package org.contacts.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.contacts.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CreateContactResponse_QNAME = new QName("http://example.org/contactbook", "createContactResponse");
    private final static QName _DeleteContact_QNAME = new QName("http://example.org/contactbook", "deleteContact");
    private final static QName _SearchContactsResponse_QNAME = new QName("http://example.org/contactbook", "searchContactsResponse");
    private final static QName _FindContactResponse_QNAME = new QName("http://example.org/contactbook", "findContactResponse");
    private final static QName _SearchContacts_QNAME = new QName("http://example.org/contactbook", "searchContacts");
    private final static QName _UpdateContactResponse_QNAME = new QName("http://example.org/contactbook", "updateContactResponse");
    private final static QName _FindContact_QNAME = new QName("http://example.org/contactbook", "findContact");
    private final static QName _NotFoundFault_QNAME = new QName("http://example.org/contactbook", "NotFoundFault");
    private final static QName _UpdateContact_QNAME = new QName("http://example.org/contactbook", "updateContact");
    private final static QName _CreateContact_QNAME = new QName("http://example.org/contactbook", "createContact");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.contacts.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SearchContactsResponse }
     * 
     */
    public SearchContactsResponse createSearchContactsResponse() {
        return new SearchContactsResponse();
    }

    /**
     * Create an instance of {@link CreateContactResponse }
     * 
     */
    public CreateContactResponse createCreateContactResponse() {
        return new CreateContactResponse();
    }

    /**
     * Create an instance of {@link DeleteContact }
     * 
     */
    public DeleteContact createDeleteContact() {
        return new DeleteContact();
    }

    /**
     * Create an instance of {@link CreateContact }
     * 
     */
    public CreateContact createCreateContact() {
        return new CreateContact();
    }

    /**
     * Create an instance of {@link UpdateContact }
     * 
     */
    public UpdateContact createUpdateContact() {
        return new UpdateContact();
    }

    /**
     * Create an instance of {@link FindContact }
     * 
     */
    public FindContact createFindContact() {
        return new FindContact();
    }

    /**
     * Create an instance of {@link FindContactResponse }
     * 
     */
    public FindContactResponse createFindContactResponse() {
        return new FindContactResponse();
    }

    /**
     * Create an instance of {@link SearchContacts }
     * 
     */
    public SearchContacts createSearchContacts() {
        return new SearchContacts();
    }

    /**
     * Create an instance of {@link UpdateContactResponse }
     * 
     */
    public UpdateContactResponse createUpdateContactResponse() {
        return new UpdateContactResponse();
    }

    /**
     * Create an instance of {@link Contact }
     * 
     */
    public Contact createContact() {
        return new Contact();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateContactResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example.org/contactbook", name = "createContactResponse")
    public JAXBElement<CreateContactResponse> createCreateContactResponse(CreateContactResponse value) {
        return new JAXBElement<CreateContactResponse>(_CreateContactResponse_QNAME, CreateContactResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteContact }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example.org/contactbook", name = "deleteContact")
    public JAXBElement<DeleteContact> createDeleteContact(DeleteContact value) {
        return new JAXBElement<DeleteContact>(_DeleteContact_QNAME, DeleteContact.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchContactsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example.org/contactbook", name = "searchContactsResponse")
    public JAXBElement<SearchContactsResponse> createSearchContactsResponse(SearchContactsResponse value) {
        return new JAXBElement<SearchContactsResponse>(_SearchContactsResponse_QNAME, SearchContactsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindContactResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example.org/contactbook", name = "findContactResponse")
    public JAXBElement<FindContactResponse> createFindContactResponse(FindContactResponse value) {
        return new JAXBElement<FindContactResponse>(_FindContactResponse_QNAME, FindContactResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchContacts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example.org/contactbook", name = "searchContacts")
    public JAXBElement<SearchContacts> createSearchContacts(SearchContacts value) {
        return new JAXBElement<SearchContacts>(_SearchContacts_QNAME, SearchContacts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateContactResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example.org/contactbook", name = "updateContactResponse")
    public JAXBElement<UpdateContactResponse> createUpdateContactResponse(UpdateContactResponse value) {
        return new JAXBElement<UpdateContactResponse>(_UpdateContactResponse_QNAME, UpdateContactResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindContact }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example.org/contactbook", name = "findContact")
    public JAXBElement<FindContact> createFindContact(FindContact value) {
        return new JAXBElement<FindContact>(_FindContact_QNAME, FindContact.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example.org/contactbook", name = "NotFoundFault")
    public JAXBElement<String> createNotFoundFault(String value) {
        return new JAXBElement<String>(_NotFoundFault_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateContact }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example.org/contactbook", name = "updateContact")
    public JAXBElement<UpdateContact> createUpdateContact(UpdateContact value) {
        return new JAXBElement<UpdateContact>(_UpdateContact_QNAME, UpdateContact.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateContact }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example.org/contactbook", name = "createContact")
    public JAXBElement<CreateContact> createCreateContact(CreateContact value) {
        return new JAXBElement<CreateContact>(_CreateContact_QNAME, CreateContact.class, null, value);
    }

}
