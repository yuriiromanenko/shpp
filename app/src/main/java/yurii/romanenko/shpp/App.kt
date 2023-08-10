package yurii.romanenko.shpp

import android.app.Application
import yurii.romanenko.shpp.model.UsersService

class App:Application() {
    val usersService = UsersService()
}