package yurii.romanenko.shpp

import android.util.Patterns

object Validation {
    fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isValidPassword(password: String) : Boolean = true
}