package com.noman.movie.ui.screens.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.noman.movie.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private lateinit var binding: FragmentMovieBinding
    val viewModel: MovieViewModel by viewModels()
    private val movieAdapter = MoviePagingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRecyclerView()
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
        viewModel.list.observe(viewLifecycleOwner) {
            movieAdapter.submitData(lifecycle, it)
        }
    }

    private fun setRecyclerView() {
        binding.movieRecycler.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 1)
        }
    }


}