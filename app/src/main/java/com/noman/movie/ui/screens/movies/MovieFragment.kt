package com.noman.movie.ui.screens.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.noman.movie.databinding.FragmentMovieBinding
import com.noman.movie.utils.ConnectivityObserver
import com.noman.movie.utils.NetworkConnectivityObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private lateinit var binding: FragmentMovieBinding
    val viewModel: MovieViewModel by viewModels()
    private val movieAdapter = MoviePagingAdapter()
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.moviesProgress.isVisible = true
        setRecyclerView()
        viewModel.observeConnectivity(requireContext().applicationContext)
//        observeConnectivity()
        init()
    }

    private fun init() {

        lifecycleScope.launch {
            viewModel.internetStatus.collect{ status ->
                updateNetworkStatus(status)
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.moviesProgress.isVisible = isLoading
                viewModel.movieList.collect { pagingData ->
                    movieAdapter.submitData(pagingData)
                }
            }
        }

        binding.movieSearch.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.setQuery(it)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let {
                    viewModel.setQuery(it)
                }
                return true
            }
        })

        movieAdapter.onMovieClick {
            val action = MovieFragmentDirections.actionMovieFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun setRecyclerView() {
        binding.movieRecycler.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 1)
        }
    }

    private fun updateNetworkStatus(status: ConnectivityObserver.Status) {
        binding.networkStatusTextView.text = "Network status: $status"
            lifecycleScope.launch {
                viewModel.movieList.collect { pagingData ->
                    binding.moviesProgress.isVisible = false
                    movieAdapter.submitData(pagingData)
                }
            }
        }


}