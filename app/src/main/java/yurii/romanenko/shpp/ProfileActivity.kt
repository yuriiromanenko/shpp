package yurii.romanenko.shpp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_layout)
    }

    fun onTestButtonClick(view: View) {
        val nextIntent = Intent(this, AuthActivity::class.java)
        startActivity(nextIntent)
    }


}