package com.firman.dicodingevent.ui.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        handleWindowInsets()

        setupRecyclerView()
        observeViewModel()

        requireActivity().window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
    }

    private fun handleWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemWindowInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.updatePadding(
                top = 0
            )
            insets
        }
    }

    private fun setupRecyclerView() {
        eventAdapter = FinishedEventAdapter { event ->

            binding.rvActive.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.rvActive.adapter = eventAdapter

            val itemDecoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            binding.rvActive.addItemDecoration(itemDecoration)
        }
    }

    private fun observeViewModel() {
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
