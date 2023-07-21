package yurii.romanenko.shpp

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import yurii.romanenko.shpp.databinding.AuthLayoutBinding
import kotlin.properties.Delegates.notNull

class AuthActivity : AppCompatActivity() {

    private val SHARED_PREFS_EMAIL = "put_email"
    private val SHARED_PREFS_PASSWORD = "put_password"
    private lateinit var binding: AuthLayoutBinding
    private var email by notNull<String>()
    private var password by notNull<String>()
    private var emailValid: Boolean = false
    private var passwordValid: Boolean = false


       val sharedPrefs by lazy {
           getSharedPreferences(
               "shared_preference",
               Context.MODE_PRIVATE
           )
       }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AuthLayoutBinding.inflate(layoutInflater).also { setContentView(it.root) }

        email = sharedPrefs.getString(SHARED_PREFS_EMAIL, "yuri.romanenko").toString()
        password = sharedPrefs.getString(SHARED_PREFS_PASSWORD, "KotlinMyLov").toString()

        binding.textEditEmail.setText(email)
        binding.textEditPass.setText(password)

        emailCheck()
        passwordCheck()

        binding.buttonRegister.setOnClickListener() { clickButtonRegister() }
    }


    private fun passwordCheck() {
        binding.textEditPass.doOnTextChanged { text, start, before, count ->
            passwordValid = text!!.contains("Love")
            if (passwordValid) {
                binding.textInputPasswordLayout.error = null
            } else {
                binding.textInputPasswordLayout.error = "Password must contain word 'Love'"
            }
        }
    }

    private fun emailCheck() {
        binding.textEditEmail.doOnTextChanged { text, start, before, count ->
            emailValid = (text!!.contains('@')) && (text.contains('.'))
            if (emailValid) {
                binding.textEditEmail.error = null
            } else {
                binding.textEditEmail.error = "email NOT valid"
            }
        }
    }

    private fun clickButtonRegister() {
        val intentProfileActivity = Intent(this, ProfileActivity::class.java)
        val optionTransitionAnimation = ActivityOptions.makeSceneTransitionAnimation(this)
        var remembeMeIsChecked : Boolean = binding.checkBoxRememberMe.isChecked
        email = binding.textEditEmail.text.toString()
        password = binding.textEditPass.text.toString()
        Log.d("TESTLOG", "Click REGISTER: $email")
        if (!emailValid) {
            Toast.makeText(this, "Email '$email' is not valid", Toast.LENGTH_SHORT).show()
        } else if (!passwordValid) {
            Toast.makeText(this, "Password '$password' is not valid", Toast.LENGTH_SHORT).show()
        } else {

            if (remembeMeIsChecked){
            sharedPrefs.edit().putString(SHARED_PREFS_EMAIL, email).apply()
            sharedPrefs.edit().putString(SHARED_PREFS_PASSWORD, password).apply()
         }

            intentProfileActivity.putExtra("text_name", parseEmailToName(email))
            startActivity(intentProfileActivity, optionTransitionAnimation.toBundle())

        }
    }


    private fun parseEmailToName(email: String): String {
        var secondName: String = email.substringBefore('@')
        var firstName: String = secondName.substringBefore('.').capitalize()
        return firstName + " " + secondName.substringAfter('.').capitalize()
    }

}




