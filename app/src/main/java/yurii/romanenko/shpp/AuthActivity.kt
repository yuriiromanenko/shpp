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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AuthLayoutBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.textEditEmail.doOnTextChanged { text, start, before, count ->
            if ((!text!!.contains('@')) || (!text.contains('.'))  ) {
                binding.textEditEmail.error = "This is NOT email"
            } else {
                binding.textEditEmail.error = null
            }
        }

        binding.textEditPass.doOnTextChanged { text, start, before, count ->
            if (!text!!.contains("Love")) {
                binding.textInputPasswordLayout.error = "Password must contain word 'Love'"
            } else {
                binding.textInputPasswordLayout.error = null
            }
        }


        binding.buttonRegister.setOnClickListener() { clickButtonRegister() }

//        pass = binding.textInputPasswordLayout.editText?.text.toString()
//        email = binding.textInputEmail.editText?.text.toString()
//        Log.d("TESTLOG", "Pass: $pass")
//        Log.d("TESTLOG", "Email: $email")
    }

    private fun passLogic() {

    }

    private fun emailLogic() {

    }

    private fun clickButtonRegister() {

        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("text_name", "Test Testovich")
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // outState.putString(email, email) // TODO: What write this?
    }

    private fun pass() {
        val pass = binding.textInputPasswordLayout.editText.toString()
        println(pass)

        if (!pass.contains("Love") && pass.isEmpty()) {
            binding.textInputPasswordLayout.error = "Password must contain word 'Love' "
        }
    }

    private fun parseEmail() {
        val pass = binding.textInputPasswordLayout.editText.toString()
        TODO("Not yet implemented")
        Toast.makeText(this, pass, Toast.LENGTH_SHORT).show()
    }


//    fun onTestButtonClick(view: View) {
//        val intent = Intent(this, ProfileActivity::class.java)
//        startActivity(intent)
//    }
}

