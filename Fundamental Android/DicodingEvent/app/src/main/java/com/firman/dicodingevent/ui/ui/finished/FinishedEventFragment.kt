package com.firman.dicodingevent.ui.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.firman.dicodingevent.databinding.FragmentFinishedEventBinding
import com.firman.dicodingevent.ui.FinishedEventAdapter

class FinishedEventFragment : Fragment() {

    private var _binding: FragmentFinishedEventBinding? = null
    private val binding get() = _binding!!

    private val finishedEventViewModel by viewModels<FinishedEventViewModel>()
    private lateinit var eventAdapter: FinishedEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchBar()
        setupRecyclerView()
        observeViewModel()

    }

    private fun setupSearchBar() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, actionId, event ->
                val keyword = searchView.text.toString()
                finishedEventViewModel.searchEvents(keyword)
                searchBar.setText(keyword)
                searchView.hide()
                Toast.makeText(requireContext(), "Searching for: $keyword", Toast.LENGTH_SHORT).show()
                false
            }
        }
    }

    private fun setupRecyclerView() {
        eventAdapter = FinishedEventAdapter { event ->
            // Handle event item click
        }
        binding.rvActive.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvActive.adapter = eventAdapter

        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvActive.addItemDecoration(itemDecoration)
    }

    private fun observeViewModel() {
        finishedEventViewModel.finishedEvent.observe(viewLifecycleOwner) { eventList ->
            eventAdapter.submitList(eventList)
            // Tambahkan notifikasi jika tidak ada event yang ditemukan
            if (eventList.isEmpty()) {
                Toast.makeText(requireContext(), "No events found", Toast.LENGTH_SHORT).show()
            }
        }

        finishedEventViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        // Menempatkan ProgressBar di tengah
        if (isLoading) {
            binding.progressBar.bringToFront() // Membawa ProgressBar ke depan
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
