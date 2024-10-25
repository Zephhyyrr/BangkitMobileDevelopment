package com.firman.dicodingevent.ui.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.firman.dicodingevent.data.entity.EventEntity
import com.firman.dicodingevent.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteEventAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = FavoriteModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        binding.rvFavorite.layoutManager = LinearLayoutManager(context)
        adapter = FavoriteEventAdapter()
        binding.rvFavorite.adapter = adapter

        viewModel.favoriteEvents.observe(viewLifecycleOwner) { favoriteEvents ->
            val items = favoriteEvents.map { event ->
                EventEntity(
                    id = event.id,
                    name = event.name,
                    mediaCover = event.mediaCover,
                    active = event.active,
                    isFavorite = true
                )
            }
            adapter.submitList(items)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.loadFavoriteEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
