package ua.shpp.yurom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ua.shpp.yurom.model.Contact
import ua.shpp.yurom.model.ContactActionListener
import ua.shpp.yurom.model.ContactsAdapter
import ua.shpp.yurom.model.ContactsRepository
import ua.shpp.yurrom.R
import ua.shpp.yurrom.databinding.ContactsBinding

class Contacts : Fragment(R.layout.contacts) {

    private lateinit var binding: ContactsBinding
    private lateinit var adapter: ContactsAdapter
    private val viewModel: ContactViewModel by viewModels {
        ContactViewModelFactory(ContactsRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ContactsBinding.inflate(inflater)

        initAdapter()
        initListeners()
        initRecyclerView()
        initTouchHelper()
        initLiveData()

        return binding.root
    }

    private fun initLiveData() {
        viewModel.contactsLive.observe(viewLifecycleOwner) {
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
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                viewModel.deleteContact(position)
                undoDelete()
            }
        }).attachToRecyclerView(binding.recyclerView)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun initListeners() {

        // contactsRepository.addListener(contactsListener)

        binding.tvAddContact.setOnClickListener {
            showDialog()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initAdapter() {
        adapter = ContactsAdapter(
            object : ContactActionListener {
                override fun onUserDelete(contact: Contact) {
                    viewModel.deleteContact(contact)
                    Toast.makeText(
                        requireContext(),
                        "contact ${contact.name} has been removed",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onUserProfile(contact: Contact) {
                    val name = contact.name
                    val photo = contact.photo
                    val company = contact.position
                    val address = contact.address

                    val directions = ContactsDirections.actionContacts2ToDetailFragment(
                        name,
                        photo,
                        address,
                        company
                    )
                    findNavController().navigate(directions)

                }
            }
        )
    }

    private fun showDialog() {
        val dialogFragment = AddContactDialog()
        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogTheme)
        dialogFragment.show(parentFragmentManager, AddContactDialog.ADD_CONTACT_DIALOG)
    }

    private fun undoDelete() {
        Snackbar
            .make(binding.root, R.string.contact_deleted, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.undo)) { viewModel.restoreLastDeletedContact() }
            .setActionTextColor(resources.getColor(R.color.orange))
            .show()
    }

}