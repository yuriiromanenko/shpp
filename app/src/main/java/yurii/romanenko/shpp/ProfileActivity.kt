package yurii.romanenko.shpp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlin.properties.Delegates

class ProfileActivity : AppCompatActivity() {

    private var name by Delegates.notNull<String>()
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_layout)

        textView = findViewById(R.id.text_name)

        name = intent.getStringExtra("text_name").toString()

        textView.text = name

    }

    fun onTestButtonClick() {
        val authActivityIntent = Intent(this, AuthActivity::class.java)
        startActivity(authActivityIntent)
    }


}