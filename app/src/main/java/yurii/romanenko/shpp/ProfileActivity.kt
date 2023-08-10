package yurii.romanenko.shpp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import yurii.romanenko.shpp.databinding.ProfileLayoutBinding
import yurii.romanenko.shpp.ext.firstCharToUpper
import kotlin.properties.Delegates

class ProfileActivity : AppCompatActivity() {


    private val binding: ProfileLayoutBinding by lazy {
        ProfileLayoutBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setProfileView()
        setListeners()
    }

    private fun setListeners() {
        onTestButtonClick()
    }

    private fun setProfileView() {
        binding.textName.text = parseEmailToName(intent.getStringExtra("text_name").toString()) // TODO: to Constants
    }

    private fun onTestButtonClick() {
        binding.buttonEdit.setOnClickListener {
            val authActivityIntent = Intent(this, AuthActivity::class.java)
            startActivity(authActivityIntent)
        }
    }
    private fun parseEmailToName(email: String): String { // TODO: parser to object class
        val secondName: String = email.substringBefore('@')
        val firstName: String = secondName.substringBefore('.')
            .firstCharToUpper()
        return "$firstName " + secondName.substringAfter('.')
            .firstCharToUpper()
    }

}