package yurii.romanenko.shpp

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import yurii.romanenko.shpp.databinding.AuthLayoutBinding
import java.util.Locale
import kotlin.properties.Delegates.notNull

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: AuthLayoutBinding
    private var email by notNull<String>()  // = "yuri.romanenko@mail.com"//
    private var password by notNull<String>()   //  = "KotlinMyLov"  //
    private var emailValid: Boolean = false
    private var passwordValid: Boolean = false

    private val userPreferences: UserPreferences by lazy {
        UserPreferences(applicationContext.dataStore)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AuthLayoutBinding.inflate(layoutInflater).also { setContentView(it.root) }

        loadUser()

        emailCheck()
        passwordCheck()

        binding.buttonRegister.setOnClickListener { clickButtonRegister() }
    }

    private fun loadUser() {
        lifecycleScope.launch {
            userPreferences.emailFlow.collect { savedEmail ->
                savedEmail?.let {
                    email = it
                    binding.textEditEmail.setText(email)
                }
            }
        }

        lifecycleScope.launch {
            userPreferences.passFlow.collect { savedPass ->
                savedPass?.let {
                    password = it
                    binding.textEditPass.setText(password)
                }
            }
        }
    }


    private fun passwordCheck() {
        binding.textEditPass.doOnTextChanged { text, _, _, _ ->
            passwordValid = text!!.contains("Love")
            if (passwordValid) {
                binding.textInputPasswordLayout.error = null
            } else {
                binding.textInputPasswordLayout.error = "Password must contain word 'Love'"
            }
        }
    }

    private fun emailCheck() {
        binding.textEditEmail.doOnTextChanged { text, _, _, _ ->
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
        val rememberMeIsChecked: Boolean = binding.checkBoxRememberMe.isChecked
        email = binding.textEditEmail.text.toString()
        password = binding.textEditPass.text.toString()
        Log.d("TEST_LOG", "Click REGISTER: $email")
        if (!emailValid) {
            Toast.makeText(this, "Email '$email' is not valid", Toast.LENGTH_SHORT).show()
        } else if (!passwordValid) {
            Toast.makeText(this, "Password '$password' is not valid", Toast.LENGTH_SHORT).show()
        } else {
            if (rememberMeIsChecked) {

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    lifecycleScope.launch {
                        userPreferences.saveEmail(email)
                    }
                    lifecycleScope.launch {
                        userPreferences.savePass(password)
                    }
                }
            }

            intentProfileActivity.putExtra("text_name", parseEmailToName(email))
            startActivity(intentProfileActivity, optionTransitionAnimation.toBundle())

        }
    }


    private fun parseEmailToName(email: String): String {
        val secondName: String = email.substringBefore('@')
        val firstName: String = secondName.substringBefore('.')
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        return "$firstName " + secondName.substringAfter('.')
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

}




