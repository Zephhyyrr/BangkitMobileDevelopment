package com.firman.dicodingevent.ui.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.firman.dicodingevent.databinding.FragmentUpcomingEventBinding
import com.firman.dicodingevent.ui.EventAdapter

class UpcomingEventFragment : Fragment() {

    private var _binding: FragmentUpcomingEventBinding? = null
    private val binding get() = _binding!!
    private val upcomingEventViewModel by viewModels<UpcomingEventViewModel>()
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingEventBinding.inflate(inflater, container, false)

        upcomingEventViewModel.searchEvents(keyword = "")

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, actionId, event ->
                val keyword = searchView.text.toString()
                if (keyword.isNotBlank()) {
                    upcomingEventViewModel.searchEvents(keyword)
                } else {
                    upcomingEventViewModel.searchEvents(keyword = "")
                }

                searchBar.setText(searchView.text)
                searchView.hide()

                Toast.makeText(requireContext(), "Searching for: $keyword", Toast.LENGTH_SHORT).show()
                false
            }
        }

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
        eventAdapter = EventAdapter { event ->
        }
        binding.rvActive.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvActive.adapter = eventAdapter

        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvActive.addItemDecoration(itemDecoration)
    }

    private fun observeViewModel() {
        upcomingEventViewModel.activeEvent.observe(viewLifecycleOwner) { eventList ->
            eventAdapter.submitList(eventList)
        }
        upcomingEventViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
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
