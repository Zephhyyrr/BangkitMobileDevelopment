package com.firman.dicodingevent.ui.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.firman.dicodingevent.R
import com.firman.dicodingevent.databinding.FragmentHomeBinding
import com.firman.dicodingevent.ui.HomeEventFinishedAdapter
import com.firman.dicodingevent.ui.HomeEventUpcomingAdapter
import com.firman.dicodingevent.ui.ui.finished.FinishedEventFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var upcomingEventAdapter: HomeEventUpcomingAdapter
    private lateinit var finishedEventAdapter: HomeEventFinishedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        upcomingEventAdapter = HomeEventUpcomingAdapter()
        finishedEventAdapter = HomeEventFinishedAdapter()

        setupRecyclerViews()
        observeEvents()
        homeViewModel.fetchEvents()

        binding.btnSelengkapnya.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_finished)
        }

        return binding.root
    }

    private fun setupRecyclerViews() {
        binding.carouselRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingEventAdapter
        }

        binding.finishedEventRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = finishedEventAdapter
        }
    }

    private fun observeEvents() {
        homeViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            upcomingEventAdapter.updateEvents(events)
        }

        homeViewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            finishedEventAdapter.updateEvents(events)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
