package com.firman.dicodingevent.ui.ui.finished

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.firman.dicodingevent.data.Result
import com.firman.dicodingevent.databinding.FragmentFinishedEventBinding
import com.firman.dicodingevent.ui.FinishedEventAdapter
import com.firman.dicodingevent.ui.ui.upcoming.UpcomingEventFragment.Companion.TAG

class FinishedEventFragment : Fragment() {

    private var _binding: FragmentFinishedEventBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FinishedEventViewModel
    private val factory: FinishedEventModelFactory by lazy {
        FinishedEventModelFactory.getInstance(requireContext())
    }

    private val finishedEventViewModel: FinishedEventViewModel by viewModels { factory }
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

        setupRecyclerView()
        observeFinishedEvents()
        observeLoadingState()
//        viewModel = ViewModelProvider(requireContext(), factory)[FinishedEventViewModel::class.java]
//        binding.progressBar
    }

    private fun setupRecyclerView() {
        eventAdapter = FinishedEventAdapter()
        binding.rvActive.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvActive.adapter = eventAdapter

        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvActive.addItemDecoration(itemDecoration)
    }

    private fun observeFinishedEvents() {
        finishedEventViewModel.finishedEvents.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    eventAdapter.submitList(result.data)
                }
                is Result.Error -> {
                    Log.d(TAG, "Error fetching upcoming events:")
                    Toast.makeText(requireContext(), "Error fetching finished events", Toast.LENGTH_SHORT).show()
                }
                Result.Loading -> binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun observeLoadingState() {
        finishedEventViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
