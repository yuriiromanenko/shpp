package ua.shpp.yurom.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.shpp.yurom.model.Contact
import ua.shpp.yurom.model.ContactsListener
import ua.shpp.yurom.model.ContactsRepositoryInterface

class ContactViewModel(
    private val contactsRepository: ContactsRepositoryInterface
) : ViewModel() {

    private val _contactsLive = MutableLiveData<List<Contact>>()
    val contactsLive: LiveData<List<Contact>> = _contactsLive

    private val listener: ContactsListener = {
        _contactsLive.value = it
    }

    init {
        loadContacts()
    }

    override fun onCleared() {
        super.onCleared()
        contactsRepository.removeListener(listener)
    }

    private fun loadContacts() {
        contactsRepository.addListener(listener)
    }

    fun addContact(contact: Contact) {
        contactsRepository.addContact(contact, contactsRepository.getSizeContacts())
    }

    fun restoreLastDeletedContact() {
        contactsRepository.restoreLastDeletedContact()
    }

    fun deleteContact(contactPosition: Int) {
    contactsRepository.deleteContact(contactPosition)
    }

    fun deleteContact(contact: Contact) {
    contactsRepository.deleteContact(contact)
    }


}

/**
 *   Factory
 */
class ContactViewModelFactory(private val contactsRepositoryInterface: ContactsRepositoryInterface) :
    ViewModelProvider.Factory {
// todo  https://www.youtube.com/watch?v=bCH12ycXPeo  22:40
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(contactsRepositoryInterface) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}

