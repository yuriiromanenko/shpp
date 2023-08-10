package yurii.romanenko.shpp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import yurii.romanenko.shpp.databinding.MyContactsLayoutBinding
import yurii.romanenko.shpp.model.UsersListener
import yurii.romanenko.shpp.model.UsersService

class MyContactsActivity : AppCompatActivity() {

    private lateinit var binding: MyContactsLayoutBinding
    private lateinit var adapter: UserAdapter

    private val usersService : UsersService
        get() = (applicationContext as App).usersService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyContactsLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()

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