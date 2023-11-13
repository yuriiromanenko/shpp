package yurii.romanenko.shpp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import yurii.romanenko.shpp.databinding.ContactsBinding
import yurii.romanenko.shpp.model.AddContactDialog
import yurii.romanenko.shpp.model.Contact
import yurii.romanenko.shpp.model.ContactActionListener
import yurii.romanenko.shpp.model.ContactsAdapter
import yurii.romanenko.shpp.model.ContactsListener
import yurii.romanenko.shpp.model.ContactsRepository
import yurii.romanenko.shpp.model.ContactsRepositoryInterface

class ContactsActivity : AppCompatActivity() {

    private lateinit var binding: ContactsBinding
    private lateinit var adapter: ContactsAdapter

    // private lateinit var viewModel: ContactViewModel

    private val contactsRepository: ContactsRepository
        get() = (applicationContext as App).contactsRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //   viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        binding.tvAddContact.setOnClickListener {
            showDialog();
        }

        adapter = ContactsAdapter(
            object : ContactActionListener {
                override fun onUserDelete(contact: Contact) {
                    contactsRepository.deleteContact(contact)
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

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        contactsRepository.addListener(contactsListener)

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
                contactsRepository.deleteContactByIndex(position)
                undoDelete()
            }
        }).attachToRecyclerView(binding.recyclerView)
    }

    private fun showDialog() {
        val fragmentManager = supportFragmentManager
        val dialog = AddContactDialog()
            // dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogTheme)
        dialog.show(fragmentManager, ADD_CONTACT_DIALOG)

    }

    private fun undoDelete() {
        Snackbar.make(binding.root, R.string.contact_deleted, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.undo)) {
                 // restoreContact()
                // TODO: restore contact NOT WORk
            }
            .show()
    }

    private fun restoreContact() {
      contactsRepository.restoreLastDeletedContact()
    }

    override fun onDestroy() {
        super.onDestroy()
        contactsRepository.removeListener(contactsListener)
    }

    private val contactsListener: ContactsListener = {
        adapter.contacts = it
    }

    companion object {
        const val ADD_CONTACT_DIALOG = "AddContactDialog"
    }
}