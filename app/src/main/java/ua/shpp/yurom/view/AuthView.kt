package ua.shpp.yurom.view

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ua.shpp.yurom.datastore.UserPreferences
import ua.shpp.yurom.ext.firstCharToUpper
import ua.shpp.yurom.utils.Validation
import ua.shpp.yurrom.R
import ua.shpp.yurrom.databinding.AuthLayoutBinding

class AuthView : Fragment(R.layout.auth_layout) {

    private lateinit var binding: AuthLayoutBinding

    private lateinit var userPreferences: UserPreferences


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AuthLayoutBinding.bind(view)
        //userPreferences = UserPreferences(applicationContext.dataStore) todo
        setListeners()
        // autoLogin()
        validatesInput()
    }

    private fun autoLogin() {
        lifecycleScope.launch {
            userPreferences.checkFlow.collect { savedData ->
                savedData?.let {
                    if (it) {
                        startProfileView()
                    }
                }
            }
        }
    }

    private fun startProfileView() {
        findNavController().navigate(R.id.action_authView_to_profileView)
    }

    private fun setListeners() {
        binding.buttonRegister.setOnClickListener { clickButtonRegister() }
    }

    private fun validatesInput() {
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
            startProfileView()
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




