package ua.shpp.yurom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import ua.shpp.yurom.ext.loadImage
import ua.shpp.yurom.model.Contact
import ua.shpp.yurom.model.ContactsRepository
import ua.shpp.yurrom.databinding.AddContactDialogBinding


class AddContactDialog : DialogFragment() {

    private val binding: AddContactDialogBinding by lazy {
        AddContactDialogBinding.inflate(layoutInflater)
    }

    private val viewModel: ContactViewModel by viewModels {
        ContactViewModelFactory(ContactsRepository())
    }

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

        with(binding) {

            btnAddPhoto.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            btnSave.setOnClickListener {
                val contact: Contact = getContact()

                viewModel.addContact(contact)

                dismiss()
            }

            backButton.setOnClickListener {
                dismiss()
            }
        }
        return binding.root
    }

    private fun getContact(): Contact {
        return Contact(
            id = ContactsRepository.getNewID(),
            photo = photoLink,
            name = binding.usernameEdit.text.toString(),
            position = binding.careerEdit.text.toString(),
            address = binding.addressEdit.text.toString(),
        )
    }

    companion object {
        const val ADD_CONTACT_DIALOG = "AddContactDialog"
    }


}

