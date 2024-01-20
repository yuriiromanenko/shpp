package ua.shpp.yurom.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ua.shpp.yurom.datastore.UserPreferences
import ua.shpp.yurom.datastore.dataStore
import ua.shpp.yurom.ext.firstCharToUpper
import ua.shpp.yurom.utils.Validation
import ua.shpp.yurrom.R
import ua.shpp.yurrom.databinding.AuthLayoutBinding

class AuthActivity : AppCompatActivity() {

    private val binding: AuthLayoutBinding by lazy {
        AuthLayoutBinding.inflate(layoutInflater)
    }

    private val userPreferences: UserPreferences by lazy {
        UserPreferences(applicationContext.dataStore)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
        autoLogin()
        validatesInput()
    }

    private fun autoLogin() {
        lifecycleScope.launch {
            userPreferences.checkFlow.collect { savedData ->
                savedData?.let {
                    if (it) {
                        startProfileActivity()
                    }
                }
            }
        }
    }

    private fun startProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun setListeners() {
        binding.buttonRegister.setOnClickListener { clickButtonRegister() }
    }

    private fun validatesInput() {
        Log.d("YRLOG", "validation")
        emailCheck()
        passwordCheck()
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
        if (Validation.isValidEmail(getEmail())
            && Validation.isValidPassword(getPassword())
        ) {
            lifecycleScope.launch {
                userPreferences.saveEmail(getEmail())
                userPreferences.saveName(parseEmailToName(getEmail()))
                userPreferences.savePass(getPassword())
                userPreferences.saveCheckBox(binding.checkBoxRememberMe.isChecked)
            }
            startProfileActivity()
        }
    }
    private fun getPassword() = binding.textEditPass.text.toString()
    private fun getEmail() = binding.textEditEmail.text.toString()
    private fun parseEmailToName(email: String): String {
        val secondName: String = email.substringBefore('@')
        val firstName: String = secondName.substringBefore('.')
            .firstCharToUpper()
        return "$firstName " + secondName.substringAfter('.')
            .firstCharToUpper()
    }
}




