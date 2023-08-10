package yurii.romanenko.shpp

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import yurii.romanenko.shpp.databinding.AuthLayoutBinding
import yurii.romanenko.shpp.ext.firstCharToUpper
import java.util.Locale
import kotlin.properties.Delegates.notNull

class AuthActivity : AppCompatActivity() {

    private val binding: AuthLayoutBinding by lazy {
        AuthLayoutBinding.inflate(layoutInflater)
    }
    private var email: String = ""  // = "yuri.romanenko@mail.com"// // TODO: why not Null?
    private var password by notNull<String>()   //  = "KotlinMyLov"  //

    private val userPreferences: UserPreferences by lazy {
        UserPreferences(applicationContext.dataStore)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        loadUser()
        validateInputs()
        setListeners()

    }

    private fun setListeners() {
        binding.buttonRegister.setOnClickListener { clickButtonRegister() }
    }

    private fun validateInputs() {
        emailCheck()
        passwordCheck()
    }

    private fun loadUser() {
        lifecycleScope.launch {
            userPreferences.emailFlow.collect { savedEmail ->
                savedEmail?.let {
                    email = it
                    binding.textEditEmail.setText(it)
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
        binding.textEditEmail.doOnTextChanged { text, _, _, _ ->
            if (text.toString().contains("Love")) {  // TODO: Love?
                binding.textInputPasswordLayout.error = null
            } else {
                binding.textInputPasswordLayout.error = "Password must contain word 'Love'" // TODO: to Constants
            }
        }
    }

    private fun emailCheck() {
        binding.textEditEmail.doOnTextChanged { text, _, _, _ ->
            binding.textEditEmail.error = if (Validation.isValidEmail(text.toString())) {
                 null
            } else {
                "email NOT valid" // TODO: to res
            }
        }
    }

    private fun clickButtonRegister() {

        val intent = Intent(this, ProfileActivity::class.java)
        val optionTransitionAnimation = ActivityOptions.makeSceneTransitionAnimation(this)

        if (!Validation.isValidEmail(binding.textEditEmail.text.toString())) {
            Toast.makeText(this, "Email '$email' is not valid", Toast.LENGTH_SHORT).show()
        } else if (!Validation.isValidPassword(binding.textEditPass.text.toString())) {
            Toast.makeText(this, "Password '$password' is not valid", Toast.LENGTH_SHORT).show()
        } else {
            if (binding.checkBoxRememberMe.isChecked) {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    lifecycleScope.launch {
                         userPreferences.saveEmail(email)
                         userPreferences.savePass(password)
                    }
                }
            }
            intent.putExtra("text_name", binding.textEditEmail.text.toString()) // TODO: to constants
            startActivity(intent, optionTransitionAnimation.toBundle())
        }
    }




}




