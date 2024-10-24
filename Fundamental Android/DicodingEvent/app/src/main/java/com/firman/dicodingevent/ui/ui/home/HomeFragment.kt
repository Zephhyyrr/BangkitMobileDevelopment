package com.firman.dicodingevent.ui.ui.home

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.firman.dicodingevent.R
import com.firman.dicodingevent.databinding.FragmentHomeBinding
import com.firman.dicodingevent.ui.HomeEventFinishedAdapter
import com.firman.dicodingevent.ui.HomeEventUpcomingAdapter
import androidx.navigation.fragment.findNavController
import com.firman.dicodingevent.utils.NetworkReceiver

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var upcomingEventAdapter: HomeEventUpcomingAdapter
    private lateinit var finishedEventAdapter: HomeEventFinishedAdapter
    private lateinit var networkReceiver: BroadcastReceiver

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        upcomingEventAdapter = HomeEventUpcomingAdapter()
        finishedEventAdapter = HomeEventFinishedAdapter()

        setupRecyclerViews()
        observeEvents()

        if (homeViewModel.upcomingEvents.value == null || homeViewModel.finishedEvents.value == null) {
            homeViewModel.fetchEvents()
        }

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
            if (events != null) {
                upcomingEventAdapter.updateEvents(events)
            }
        }

        homeViewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            if (events != null) {
                finishedEventAdapter.updateEvents(events)
            }
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading && (homeViewModel.upcomingEvents.value == null && homeViewModel.finishedEvents.value == null)) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        networkReceiver = NetworkReceiver(requireActivity() as AppCompatActivity)
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireActivity().registerReceiver(networkReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(networkReceiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
