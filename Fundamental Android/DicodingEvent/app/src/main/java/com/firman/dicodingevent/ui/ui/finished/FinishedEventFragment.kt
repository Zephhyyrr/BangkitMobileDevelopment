package com.firman.dicodingevent.ui.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.firman.dicodingevent.data.Result
import com.firman.dicodingevent.databinding.FragmentFinishedEventBinding
import com.firman.dicodingevent.ui.FinishedEventAdapter

class FinishedEventFragment : Fragment() {

    private var _binding: FragmentFinishedEventBinding? = null
    private val binding get() = _binding!!

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

        showLoading(true)
    }

    private fun setupRecyclerView() {
        eventAdapter = FinishedEventAdapter { _ -> }
        binding.rvActive.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvActive.adapter = eventAdapter

        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvActive.addItemDecoration(itemDecoration)
    }

    private fun observeFinishedEvents() {
        finishedEventViewModel.finishedEvents.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    val events = result.data
                    eventAdapter.submitList(events)
                }
                is Result.Error -> {
                    showLoading(false)
                }
            }
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
