package ua.shpp.yurom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import ua.shpp.yurom.ext.loadImage
import ua.shpp.yurom.model.Contact
import ua.shpp.yurrom.databinding.AddContactDialogBinding

interface AddContactInterface {
    fun addContact(contact: Contact)
}

class AddContactDialog(
) : DialogFragment() {

    private val binding: AddContactDialogBinding by lazy {
        AddContactDialogBinding.inflate(layoutInflater)
    }

    private lateinit var addContactInterface: AddContactInterface
    private var photoLink = ""
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the photo picker.
            if (uri != null) {
                photoLink = uri.toString()
                binding.photo.loadImage(photoLink)
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        addContactInterface = requireActivity() as AddContactInterface

        with(binding) {

            btnAddPhoto.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            btnSave.setOnClickListener {
                val contact: Contact = getContact()
                addContactInterface.addContact(contact)
                dismiss()
            }

            backButton.setOnClickListener {
                dismiss()
            }
        }

        return binding.root
    }

    private fun getContact(): Contact {
        var id: Long = 1111
        val contact = Contact(
            id = id,
            photo = photoLink,
            name = binding.usernameEdit.text.toString(),
            company = binding.careerEdit.text.toString()
        )
        id++
        return contact
    }


}

