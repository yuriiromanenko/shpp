package ua.shpp.yurom.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ua.shpp.yurom.datastore.UserPreferences
import ua.shpp.yurom.datastore.dataStore
import ua.shpp.yurrom.databinding.ProfileLayoutBinding

class ProfileActivity : AppCompatActivity() {

    private val userPreferences: UserPreferences by lazy {
        UserPreferences(applicationContext.dataStore)
    }

    private val binding: ProfileLayoutBinding by lazy {
        ProfileLayoutBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
        loadUser()
    }
    private fun loadUser() {
        lifecycleScope.launch {
            userPreferences.nameFlow.collect { savedData ->
                savedData?.let {
                    binding.textName.text = it
                }
            }
        }
    }

    private fun setListeners() {
        binding.buttonEdit.setOnClickListener {   }
        binding.btnViewMyContacts?.setOnClickListener {  goMyContact() }
        binding.buttonLogout?.setOnClickListener { goAutuhActivity()   }
    }

    private fun goMyContact() {
        val intent = Intent(this, ContactsActivity::class.java)
        startActivity(intent)
    }

    private fun goAutuhActivity() {
        notRememberMe()
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
    }
    private fun notRememberMe() {
        lifecycleScope.launch {
            userPreferences.saveCheckBox(false)
        }
    }


}