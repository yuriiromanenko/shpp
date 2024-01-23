package ua.shpp.yurom.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ua.shpp.yurrom.R
import ua.shpp.yurrom.databinding.DetailViewFragmentBinding

class DetailFragment: Fragment(R.layout.detail_view_fragment) {

    private lateinit var binding: DetailViewFragmentBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DetailViewFragmentBinding.bind(view)

        binding.textView.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_testFrag)
        }
    }
}