package com.omersenturk.apiflowposts.ui.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.omersenturk.apiflowposts.R
import com.omersenturk.apiflowposts.databinding.FragmentAddPostBottomSheetBinding
import com.omersenturk.apiflowposts.ui.HomeViewModel

class AddPostBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentAddPostBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private var selectedImageUri: Uri? = null

    private val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            selectedImageUri = it
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
            pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.buttonSave.setOnClickListener {
            val title = binding.editTextSheetButtonPostTitle.text.toString().trim()
            val body = binding.editTextSheetButtonPostBody.text.toString().trim()
            val image = selectedImageUri?.toString() ?: "https://picsum.photos/200/300?random=${System.currentTimeMillis()}"

            if (title.isNotEmpty() && body.isNotEmpty()) {
                viewModel.addPost(title, body, image)
                Toast.makeText(requireContext(), "Post added locally", Toast.LENGTH_SHORT).show()
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
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
