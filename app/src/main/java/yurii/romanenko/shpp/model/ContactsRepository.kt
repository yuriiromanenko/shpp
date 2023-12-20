package yurii.romanenko.shpp.model

import com.github.javafaker.Faker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

typealias ContactsListener = (users: List<Contact>) -> Unit

interface ContactsRepositoryInterface {

    fun getContacts(): StateFlow<List<Contact>>
    fun deleteContactByFlow(contactPosition: Int)
    fun restoreLastDeletedContact()
    fun addContact(contact: Contact, index: Int)

    fun deleteContactByIndex(contactPosition: Int)

    fun getSizeContacts(): Int
}

class ContactsRepository : ContactsRepositoryInterface {

    private val _contactsFlow = MutableStateFlow(contacts)
    private val contactsFlow = _contactsFlow.asStateFlow()

    private val listeners = mutableListOf<ContactsListener>()
    private var lastDeletedContact: LastDeletedContact? = null

    init {
        val contactsSize = 4
        // create user-pic
        for (i in 1..contactsSize) {
            IMAGES.add("https://picsum.photos/200?random=$i")
        }
        // create name and company
        val faker = Faker.instance()
        IMAGES.shuffle()
        contacts = (1..contactsSize).map {
            Contact(
                id = it.toLong(),
                name = faker.name().name(),
                company = faker.company().name(),
                photo = IMAGES[it % IMAGES.size]
            )
        }.toMutableList()
    }

    override fun getContacts() = contactsFlow

    override fun addContact(contact: Contact, index: Int) {
        contacts = ArrayList(contacts)
        contacts.add(index, contact)
        addListener { }
       notifyChanges()

    }

    override fun deleteContactByFlow(contactPosition: Int) { // TODO: deleteContactByFlow NOT WORK
        lastDeletedContact = LastDeletedContact(contactPosition, contacts.get(contactPosition))
        _contactsFlow.value = _contactsFlow.value.apply {
            removeAt(contactPosition)
        }
    }

    fun deleteContact(contact: Contact) {
        val indexToDelete: Int = contacts.indexOfFirst { it.id == contact.id }
        lastDeletedContact = LastDeletedContact(indexToDelete, contact)
        if (indexToDelete != -1) {
            contacts = ArrayList(contacts)
            contacts.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    override fun deleteContactByIndex(contactPosition: Int) {
        lastDeletedContact = LastDeletedContact(contactPosition, contacts.get(contactPosition))
        contacts = ArrayList(contacts)
        contacts.removeAt(contactPosition)
        notifyChanges()
    }

    override fun getSizeContacts(): Int {
        return contacts.size
    }

    override fun restoreLastDeletedContact() {
        addContact(lastDeletedContact!!.contact, lastDeletedContact!!.position)
        notifyChanges()
    }


    fun addListener(listener: ContactsListener) {
        listeners.add(listener)
        listener.invoke(contacts)
    }

    fun removeListener(listener: ContactsListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(contacts) }
    }


    companion object {
        private var contacts = mutableListOf<Contact>()
        private val IMAGES = mutableListOf<String>()
    }

}

class LastDeletedContact(
    var position: Int,
    var contact: Contact,
)