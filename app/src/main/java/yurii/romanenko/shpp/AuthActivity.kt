package yurii.romanenko.shpp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import yurii.romanenko.shpp.databinding.AuthLayoutBinding
import kotlin.properties.Delegates.notNull

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: AuthLayoutBinding
    private var email by notNull<String>()
    private var pass by notNull<String>()
    private var emailValid: Boolean = false
    private var passwordValid: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AuthLayoutBinding.inflate(layoutInflater).also { setContentView(it.root) }


        emailCheck()
        passwordCheck()

        binding.buttonRegister.setOnClickListener() { clickButtonRegister() }
     //   renderState()
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
        val intent = Intent(this, ProfileActivity::class.java)
        email = binding.textEditEmail.text.toString()
        pass = binding.textEditPass.text.toString()
        Log.d("TESTLOG", "Click REGISTER: $email")
        if (!emailValid) {
            Toast.makeText(this, "Email '$email' is not valid", Toast.LENGTH_SHORT).show()
        } else if (!passwordValid) {
            Toast.makeText(this, "Password '$pass' is not valid", Toast.LENGTH_SHORT).show()
        } else {
            intent.putExtra("text_name", parseEmailToName(email))
            renderState()
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

      //  email = binding.textEditEmail.text.toString()
        outState.putString(KEY_EMAIL, email)
        outState.putString(KEY_PASS, pass)
        renderState()
    }

    private fun parseEmailToName(email: String): String {
        var secondName: String = email.substringBefore('@')
        var firstName: String = secondName.substringBefore('.').capitalize()
        return firstName + " " + secondName.substringAfter('.').capitalize()
    }

    fun renderState() = with(binding) {
        textEditEmail.setText(email)
        textEditPass.setText(pass)
    }

    companion object {
        @JvmStatic
        private val KEY_EMAIL = "EMAIL"

        @JvmStatic
        private val KEY_PASS = "PASS"
    }
}




