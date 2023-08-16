package yurii.romanenko.shpp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import yurii.romanenko.shpp.databinding.AuthLayoutBinding
import kotlin.properties.Delegates.notNull

class AuthActivity : AppCompatActivity() {

    private val binding: AuthLayoutBinding by lazy {
        AuthLayoutBinding.inflate(layoutInflater)
    }

    private var email by notNull<String>()
    private var password by notNull<String>()


    private val userPreferences: UserPreferences by lazy {
        UserPreferences(applicationContext.dataStore)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadUser()
        validatesInput()
        setListeners()
    }

    private fun setListeners() {
        binding.buttonRegister.setOnClickListener { clickButtonRegister() }
    }

    private fun validatesInput() {
        emailCheck()
        passwordCheck()
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

            binding.textInputPasswordLayout.error =
                if (Validation.isValidPassword(text.toString())) {
                    null
                } else {
                    getString(R.string.password_must_contain)
                }
        }
    }

    private fun emailCheck() {

        binding.textEditEmail.doOnTextChanged { text, _, _, _ ->
            binding.textEditEmail.error = if (Validation.isValidEmail(text.toString())) {
                null
            } else {
                getString(R.string.email_not_valid)
            }
        }
    }

    private fun clickButtonRegister() {
        val intent = Intent(this, ProfileActivity::class.java)
//        val rememberMeIsChecked: Boolean = binding.checkBoxRememberMe.isChecked
//
//        email = binding.textEditEmail.text.toString()
//        password = binding.textEditPass.text.toString()
//        Log.d("TEST_LOG", "Click REGISTER: $email")

//        if (rememberMeIsChecked) {
//
//            if (email.isNotEmpty() && password.isNotEmpty()) {
//                lifecycleScope.launch {
//                    userPreferences.saveEmail(email)
//                    userPreferences.savePass(password)
//                }
//            }
//        }

//        lifecycleScope.launch {
//            userPreferences.saveName(email)
//        }

        startActivity(intent)

    }




}




