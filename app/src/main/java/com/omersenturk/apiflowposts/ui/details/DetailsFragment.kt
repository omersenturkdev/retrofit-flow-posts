package com.omersenturk.apiflowposts.ui.details

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.chip.Chip
import com.omersenturk.apiflowposts.R
import com.omersenturk.apiflowposts.databinding.FragmentDetailsBinding
import com.omersenturk.apiflowposts.extension.capitalizeFirst


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
            textViewDetailBody.text = args.body.capitalizeFirst()
            imageViewDetailImage.load(args.image) {
                transformations(CircleCropTransformation())
            }

            //icon values
            textViewPostComment.text = args.title.length.toString()
            textViewPostLike.text = args.body.length.toString()

            setRandomChipsFromBody(args.body)

            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun setRandomChipsFromBody(body: String) {
        val words = body.replace("\n", " ").split(" ").filter { it.isNotBlank() }
        binding.chipGroupPostDetail.removeAllViews()
        words.forEach {
            val chip = Chip(
                ContextThemeWrapper(requireContext(), R.style.CustomChipStyle),
                null,
                0
            ).apply {
                text = "#${it.capitalizeFirst()}"
            }
            chip.chipBackgroundColor = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.light_gray)
                    )
            binding.chipGroupPostDetail.addView(chip)
        }
    }


}