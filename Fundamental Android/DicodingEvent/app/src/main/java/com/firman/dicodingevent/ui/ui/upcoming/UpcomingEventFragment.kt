package com.firman.dicodingevent.ui.ui.upcoming

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.firman.dicodingevent.data.Result
import com.firman.dicodingevent.databinding.FragmentUpcomingEventBinding
import com.firman.dicodingevent.ui.EventAdapter

class UpcomingEventFragment : Fragment() {

    private var _binding: FragmentUpcomingEventBinding? = null
    private val binding get() = _binding!!

    private val factory: UpcomingEventModelFactory by lazy {
        UpcomingEventModelFactory.getInstance(requireContext())
    }
    private val upcomingEventViewModel: UpcomingEventViewModel by viewModels { factory }
    private lateinit var eventAdapter: EventAdapter

    companion object {
        const val TAG = "UpcomingEventFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeUpcomingEvents()
        observeLoadingState()
    }

    private fun setupRecyclerView() {
        eventAdapter = EventAdapter { }
        binding.rvActive.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvActive.adapter = eventAdapter
        binding.rvActive.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    private fun observeUpcomingEvents() {
        upcomingEventViewModel.upcomingEvents.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    eventAdapter.submitList(result.data)
                }
                is Result.Error -> {
                    Log.d(TAG, "Error fetching upcoming events:")
                    Toast.makeText(requireContext(), "Error fetching Upcoming events", Toast.LENGTH_SHORT).show()
                }
                Result.Loading -> binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun observeLoadingState() {
        upcomingEventViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
