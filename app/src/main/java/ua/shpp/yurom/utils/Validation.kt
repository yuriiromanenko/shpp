package ua.shpp.yurom.utils

import android.util.Patterns

object Validation {


    fun isValidEmail (email : String) : Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword (password : String) : Boolean =
        password.isLongEnough() && password.hasEnoughDigits() && password.isMixedCase()

    fun String.isLongEnough() = length >= 6
    fun String.hasEnoughDigits() = count(Char::isDigit) > 0
    fun String.isMixedCase() = any(Char::isLowerCase) && any(Char::isUpperCase)


}