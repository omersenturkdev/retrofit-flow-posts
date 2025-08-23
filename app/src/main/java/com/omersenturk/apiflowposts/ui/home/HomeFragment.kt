package com.omersenturk.apiflowposts.ui.home

import PostAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.omersenturk.apiflowposts.databinding.FragmentHomeBinding
import com.omersenturk.apiflowposts.ui.HomeViewModel
import com.omersenturk.apiflowposts.ui.PostState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observePosts()
        setupAddButton()
        searchPostList()

        viewModel.fetchPosts()
    }

    private fun setupRecyclerView() {
        adapter = PostAdapter(emptyList()) { post ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                post.title,
                post.body,
                post.image
            )
            findNavController().navigate(action)
        }
        binding.recycleViewPosts.adapter = adapter
    }

    private fun observePosts() {
        lifecycleScope.launch {
            viewModel.posts.collectLatest { state ->
                when (state) {
                    is PostState.Loading -> {
                        Toast.makeText(requireContext(),"Loading Post", Toast.LENGTH_LONG).show()
                    }
                    is PostState.Success -> {
                        adapter.updatePosts(state.posts)
                    }
                    is PostState.Error -> {
                        Log.e("observePosts", state.message)
                    }
                }
            }
        }
    }

    private fun setupAddButton() {
        binding.buttonAdd.setOnClickListener {
            Toast.makeText(requireContext(), "Add button clicked!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun searchPostList() {
        binding.searchEditText.addTextChangedListener { editable ->
            val query = editable.toString()
            if (query.isNotEmpty()) {
                viewModel.searchPosts(query)
            } else {
                viewModel.fetchPosts()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
