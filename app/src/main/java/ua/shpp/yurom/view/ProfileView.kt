package ua.shpp.yurom.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ua.shpp.yurom.datastore.UserPreferences
import ua.shpp.yurom.datastore.dataStore
import ua.shpp.yurrom.R
import ua.shpp.yurrom.databinding.ProfileLayoutBinding

class ProfileView : Fragment(R.layout.profile_layout) {

    private lateinit var userPreferences: UserPreferences

    private lateinit var binding: ProfileLayoutBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfileLayoutBinding.bind(view)
        userPreferences =  UserPreferences(requireContext().dataStore)

        setListeners()
        loadUser()
    }

    private fun loadUser() {
        lifecycleScope.launch {
            userPreferences.nameFlow.collect { savedData ->
                savedData?.let {
                    binding.textName.text = it
                }
            }
        }
    }

    private fun setListeners() {
        binding.btnViewMyContacts?.setOnClickListener { goMyContact() }
        binding.buttonLogout?.setOnClickListener { goAutuhActivity() }
    }

    private fun goMyContact() {
        findNavController().navigate(R.id.action_profileView_to_contacts)
    }

    private fun goAutuhActivity() {
        notRememberMe()
        findNavController().navigateUp()
    }

    private fun notRememberMe() {
        lifecycleScope.launch {
            userPreferences.saveCheckBox(false)
        }
    }


}