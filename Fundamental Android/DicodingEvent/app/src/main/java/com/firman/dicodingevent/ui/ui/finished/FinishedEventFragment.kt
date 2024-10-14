package com.firman.dicodingevent.ui.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.firman.dicodingevent.databinding.FragmentFinishedEventBinding
import com.firman.dicodingevent.ui.FinishedEventAdapter

class FinishedEventFragment : Fragment() {

    private var _binding: FragmentFinishedEventBinding? = null
    private val binding get() = _binding!!

    // Ganti mainViewModel dengan FinishedEventViewModel
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

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        eventAdapter = FinishedEventAdapter { event ->
            // Tindakan saat item diklik, jika perlu
        }
        binding.rvActive.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvActive.adapter = eventAdapter

        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvActive.addItemDecoration(itemDecoration)
    }

    private fun observeViewModel() {
        // Mengganti mainViewModel dengan finishedEventViewModel
        finishedEventViewModel.finishedEvent.observe(viewLifecycleOwner) { eventList ->
            eventAdapter.submitList(eventList)
        }
        finishedEventViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
