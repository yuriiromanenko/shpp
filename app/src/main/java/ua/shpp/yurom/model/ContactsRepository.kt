package ua.shpp.yurom.model

import com.github.javafaker.Faker

typealias ContactsListener = (contact: List<Contact>) -> Unit

interface ContactsRepositoryInterface {

    fun restoreLastDeletedContact()
    fun addContact(contact: Contact, index: Int)
    fun deleteContact(contact: Contact)
    fun deleteContact(contactPosition: Int)
    fun getSizeContacts(): Int
    fun addListener(listener: ContactsListener)
    fun removeListener(listener: ContactsListener)
}

public class ContactsRepository : ContactsRepositoryInterface {


    private val listeners = mutableListOf<ContactsListener>()
    private var lastDeletedContact: LastDeletedContact? = null

    init {
        val contactsSize = 8
        // create user-pic
        for (i in 1..contactsSize) {
            IMAGES.add("https://picsum.photos/200?random=$i")
        }
        // create name and company
        val faker = Faker.instance()
        IMAGES.shuffle()
        if (contacts.isEmpty()) {
            contacts = (1..contactsSize).map {
                Contact(
                    id = it.toLong(),
                    name = faker.name().name(),
                    position = faker.job().position(),
                    photo = IMAGES[it % IMAGES.size],
                    address = faker.address().cityName()
                )
            }.toMutableList()
        }
    }

    override fun addContact(contact: Contact, index: Int) {
        contacts = ArrayList(contacts)
        contacts.add(index, contact)
        // notifyChanges()
    }

    override fun deleteContact(contact: Contact) {
        val indexToDelete: Int = contacts.indexOfFirst { it.id == contact.id }
        lastDeletedContact = LastDeletedContact(indexToDelete, contact)
        if (indexToDelete != -1) {
            contacts = ArrayList(contacts)
            contacts.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    override fun deleteContact(contactPosition: Int) {
        lastDeletedContact = LastDeletedContact(contactPosition, contacts[contactPosition])
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


    override fun addListener(listener: ContactsListener) {
        listeners.add(listener)
        listener.invoke(contacts)
    }

    override fun removeListener(listener: ContactsListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(contacts) }
    }


    companion object {
        public fun getNewID(): Long {
            return contacts.size.toLong() + 1
        }

        private var contacts = mutableListOf<Contact>()
        private val IMAGES = mutableListOf<String>()
    }

}


class LastDeletedContact(
    var position: Int,
    var contact: Contact,
)