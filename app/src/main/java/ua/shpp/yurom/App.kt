package ua.shpp.yurom

import android.app.Application
import ua.shpp.yurom.model.ContactsRepository

class App:Application() {
    val contactsRepository = ContactsRepository()
}