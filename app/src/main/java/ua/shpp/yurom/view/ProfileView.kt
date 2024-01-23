package ua.shpp.yurom.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ua.shpp.yurom.datastore.UserPreferences
import ua.shpp.yurrom.R
import ua.shpp.yurrom.databinding.ProfileLayoutBinding

class ProfileView : Fragment(R.layout.profile_layout) {

  //  private lateinit var userPreferences: UserPreferences

    private lateinit var binding: ProfileLayoutBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfileLayoutBinding.bind(view)
        setListeners()
      //  loadUser()
    }

    private fun loadUser() {
//        lifecycleScope.launch {
//            userPreferences.nameFlow.collect { savedData ->
//                savedData?.let {
//                    binding.textName.text = it
//                }
//            }
//        }
    }

    private fun setListeners() {
        binding.buttonEdit.setOnClickListener { }
        binding.btnViewMyContacts?.setOnClickListener { goMyContact() }
        binding.buttonLogout?.setOnClickListener { goAutuhActivity() }
    }

    private fun goMyContact() {
        // TODO: impl
    }

    private fun goAutuhActivity() {
       // notRememberMe()
        // TODO: impl
    }

    private fun notRememberMe() {
        lifecycleScope.launch {
            //userPreferences.saveCheckBox(false)
        }
    }


}