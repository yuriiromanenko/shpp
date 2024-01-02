package yurii.romanenko.shpp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import yurii.romanenko.shpp.R
import yurii.romanenko.shpp.databinding.ContactsBinding
import yurii.romanenko.shpp.model.Contact
import yurii.romanenko.shpp.model.ContactActionListener
import yurii.romanenko.shpp.model.ContactsAdapter
import yurii.romanenko.shpp.model.ContactsRepository

class ContactsActivity : AppCompatActivity(), AddContactInterface {

    private lateinit var binding: ContactsBinding
    private lateinit var adapter: ContactsAdapter
    val viewModel: ContactViewModel by viewModels {
        ContactViewModelFactory(ContactsRepository()) //todo  ContactsRepository() ???
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initLiveData()
        initListeners()
        initRecyclerView()
        initTouchHelper()
    }

    private fun initLiveData() {
        viewModel.contactsLive.observe(this) {
            adapter.contacts = it
        }
    }

    private fun initTouchHelper() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                viewModel.deleteContact(position)
               // contactsRepository.deleteContactByIndex(position)
                undoDelete()
            }
        }).attachToRecyclerView(binding.recyclerView)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun initListeners() {

       // contactsRepository.addListener(contactsListener)

        binding.tvAddContact.setOnClickListener {
            showDialog()
        }

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun initAdapter() {
        adapter = ContactsAdapter(
            object : ContactActionListener {
                override fun onUserDelete(contact: Contact) {
                    viewModel.deleteContact(contact)
                    // contactsRepository.deleteContact(contact)
                    Toast.makeText(
                        this@ContactsActivity,
                        "contact ${contact.name} has been removed",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onUserProfile(contact: Contact) {
                    Toast.makeText(
                        this@ContactsActivity,
                        "Contact: ${contact.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    private fun showDialog() {
        val fragmentManager = supportFragmentManager
        val dialog = AddContactDialog()
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogTheme)
        dialog.show(fragmentManager, ADD_CONTACT_DIALOG)
    }

    private fun undoDelete() {
        Snackbar.make(binding.root, R.string.contact_deleted, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.undo)) {
                restoreContact()
            }
            .show()
    }

    private fun restoreContact() {
       viewModel.restoreLastDeletedContact()
    }


    companion object {
        const val ADD_CONTACT_DIALOG = "AddContactDialog"
    }

    override fun addContact(contact: Contact) {
        viewModel.addContact(contact)
       // contactsRepository.addContact(contact, contactsRepository.getSizeContacts())
    }
}