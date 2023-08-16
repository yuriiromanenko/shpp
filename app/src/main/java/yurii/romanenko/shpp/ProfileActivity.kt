package yurii.romanenko.shpp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import yurii.romanenko.shpp.databinding.ProfileLayoutBinding
import yurii.romanenko.shpp.ext.firstCharToUpper

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

        lifecycleScope.launch {
            userPreferences.emailFlow.collect { savedEmail ->
                savedEmail?.let {
                   var email = it
                    binding.textName.text = parseEmailToName(email)
                }
            }
        }

    }

    private fun setListeners() {
        binding.buttonEdit.setOnClickListener {  onTestButtonClick()      }
    }

    fun onTestButtonClick() {
        val intent = Intent(this, MyContactsActivity::class.java)
        startActivity(intent)
    }

    private fun parseEmailToName(email: String): String {
        val secondName: String = email.substringBefore('@')
        val firstName: String = secondName.substringBefore('.')
            .firstCharToUpper()
        return "$firstName " + secondName.substringAfter('.')
            .firstCharToUpper()
    }
}