package com.omersenturk.apiflowposts.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.omersenturk.apiflowposts.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            //Post title-body-image
            textViewDetailTitle.text = args.title.capitalizeFirst()
            textViewDetailBody.text = args.body
            imageViewDetailImage.load(args.image) {
                transformations(CircleCropTransformation())
            }

            //icon values
            textViewComment.text = args.title.length.toString()
            textViewLike.text = args.body.length.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun String.capitalizeFirst(): String {
        val text = this.trim()
        return if (text.isNotEmpty()) {
            text.replaceFirstChar { it.uppercase() }
        } else {
            text
        }
    }

}