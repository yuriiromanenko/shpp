package ua.shpp.yurom.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ua.shpp.yurom.ext.loadImage
import ua.shpp.yurrom.R
import ua.shpp.yurrom.databinding.DetailViewLayoutBinding

class DetailFragment : Fragment(R.layout.detail_view_layout) {

    private lateinit var binding: DetailViewLayoutBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DetailViewLayoutBinding.bind(view)

        with(binding) {
            btnBack.setOnClickListener { findNavController().navigateUp() }
            textName.text = args.nameContact
            address.text = args.addressContact
            position.text= args.positionContact
            photoProfile.loadImage(args.photoContact)

        }
    }
}
