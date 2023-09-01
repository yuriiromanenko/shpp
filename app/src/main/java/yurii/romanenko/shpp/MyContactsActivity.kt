package yurii.romanenko.shpp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import yurii.romanenko.shpp.databinding.MyContactsRecyclerviewBinding
import yurii.romanenko.shpp.model.User
import yurii.romanenko.shpp.model.UserActionListener
import yurii.romanenko.shpp.model.UsersAdapter
import yurii.romanenko.shpp.model.UsersListener
import yurii.romanenko.shpp.model.UsersService

class MyContactsActivity : AppCompatActivity() {

    private lateinit var binding: MyContactsRecyclerviewBinding
    private lateinit var adapter: UsersAdapter

    private val usersService : UsersService
        get() = (applicationContext as App).usersService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyContactsRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UsersAdapter(
            object : UserActionListener {
                override fun onUserDelete(user: User) {
                   usersService.deleteUser(user)
                    Toast.makeText(this@MyContactsActivity, "contact ${user.name} has been removed", Toast.LENGTH_SHORT).show()
                }

                override fun onUserProfile(user: User) {
                    Toast.makeText(this@MyContactsActivity, "User: ${user.name}", Toast.LENGTH_SHORT).show()
                }

            }
        )

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        usersService.addListener(usersListener)
    }


    override fun onDestroy() {
        super.onDestroy()
        usersService.removeListener(usersListener)
    }

    private val usersListener : UsersListener = {
        adapter.users = it
    }


}