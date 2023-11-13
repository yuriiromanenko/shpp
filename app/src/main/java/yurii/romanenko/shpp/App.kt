package yurii.romanenko.shpp

import android.app.Application
import yurii.romanenko.shpp.model.ContactsRepository

class App:Application() {
    val contactsRepository = ContactsRepository()
}