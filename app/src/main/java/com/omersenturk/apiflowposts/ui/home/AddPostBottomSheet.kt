package com.omersenturk.apiflowposts.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.omersenturk.apiflowposts.R
import com.omersenturk.apiflowposts.databinding.FragmentAddPostBottomSheetBinding

class AddPostBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentAddPostBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.imageViewSheetButtonImage.load(it) {
                crossfade(true)
                error(R.color.black)
                scale(coil.size.Scale.FILL)
            }
            binding.buttonChoosePhoto.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPostBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonClose.setOnClickListener { dismiss() }

        binding.buttonChoosePhoto.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.buttonSave.setOnClickListener {
            val title = binding.editTextSheetButtonPostTitle.text.toString().trim()
            val body = binding.editTextSheetButtonPostBody.text.toString().trim()
            Toast.makeText(requireContext(),"$title and $body", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = AddPostBottomSheet()
    }
}
