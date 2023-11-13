package yurii.romanenko.shpp.model

import com.github.javafaker.Faker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

typealias ContactsListener = (users: List<Contact>) -> Unit

interface ContactsRepositoryInterface {

    fun getContacts(): StateFlow<List<Contact>>
    fun deleteContact(contactPosition: Int)
    fun restoreLastDeletedContact()
    fun addContact(contact: Contact, index: Int)
}

class ContactsRepository : ContactsRepositoryInterface {

    private val _contactsFlow = MutableStateFlow(contacts)
    private val contactsFlow = _contactsFlow.asStateFlow()

    private val listeners = mutableListOf<ContactsListener>()
    private var lastDeletedContact: LastDeletedContact? = null

    init {
        val contactsSize = 10
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
        _contactsFlow.value = _contactsFlow.value.toMutableList().apply {
            add(index, contact)
        }
    }

    override fun deleteContact(contactPosition: Int) { // TODO: I have 3 methods of Delete 1
        _contactsFlow.value = _contactsFlow.value.toMutableList().apply {
            lastDeletedContact = LastDeletedContact(contactPosition, get(contactPosition))
        }
    }

    fun deleteContact(user: Contact) {  // TODO: I have 3 methods of Delete 2
        val indexToDelete: Int = contacts.indexOfFirst { it.id == user.id }
        if (indexToDelete != -1) {
            contacts.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun deleteContactByIndex(contactPosition: Int) {  // TODO: I have 3 methods of Delete 3
        contacts.removeAt(contactPosition)
        notifyChanges()
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