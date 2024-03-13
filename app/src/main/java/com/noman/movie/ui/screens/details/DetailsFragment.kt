package com.noman.movie.ui.screens.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.noman.movie.R
import com.noman.movie.databinding.FragmentDetailsBinding
import com.noman.movie.ui.screens.movies.MovieViewModel
import com.noman.movie.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.backPress.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.getMovieDetails(args.movie)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieDetails.collect {
                binding.movieDetails = viewModel.getMoviesDetailsFromDataBase(args.movie)
            }
        }

    }
}