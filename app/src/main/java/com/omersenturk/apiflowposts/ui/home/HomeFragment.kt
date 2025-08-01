package com.omersenturk.apiflowposts.ui.home

import PostAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omersenturk.apiflowposts.R
import com.omersenturk.apiflowposts.ui.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAdd: Button
    private lateinit var adapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvPosts)
        btnAdd = view.findViewById(R.id.btnAdd)

        adapter = PostAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        observePosts()
        setupAddButton()

        viewModel.fetchPosts()
    }

    private fun observePosts() {
        lifecycleScope.launch {
            viewModel.posts.collectLatest { result ->
                result?.onSuccess { posts ->
                    adapter = PostAdapter(posts)
                    recyclerView.adapter = adapter
                }?.onFailure { error ->
                    Toast.makeText(requireContext(), "Hata: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupAddButton() {
        btnAdd.setOnClickListener {
            Toast.makeText(requireContext(), "Add button clicked!", Toast.LENGTH_SHORT).show()

        }
    }
}
