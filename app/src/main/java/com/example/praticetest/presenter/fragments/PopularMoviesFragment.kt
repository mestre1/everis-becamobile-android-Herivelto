package com.example.praticetest.presenter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.praticetest.R
import com.example.praticetest.data.vo.Movie
import com.example.praticetest.databinding.ListMoviesFragmentBinding
import com.example.praticetest.presenter.adapters.PopularMoviesPagingAdapter
import com.example.praticetest.presenter.viewmodel.PopularMoviesViewModel
import com.example.praticetest.domain.model.PopularMoviesLoadStateAdapter
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class PopularMoviesFragment : Fragment() {
    private lateinit var listAdapter: PopularMoviesPagingAdapter
    private lateinit var binding: ListMoviesFragmentBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val moviesViewModel: PopularMoviesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListMoviesFragmentBinding.inflate(inflater, container, false)
        binding.moviesTopRatedTextView.text = getString(R.string.popular_movies)
        binding.detailsAppBar.appBar.navigationIcon = null
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        collectResults()
        insertListeners()
    }


    private fun insertListeners() {
        binding.detailsAppBar.appBar.menu.findItem(R.id.search)
            .setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.search -> {
                        findNavController().navigate(R.id.action_global_searchMovies)
                        true
                    }
                    else -> false
                }
            }
    }

    private fun setRecyclerView() {
        listAdapter = PopularMoviesPagingAdapter() { movie -> goToMovieDetails(movie) }
        linearLayoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.listMovieRecyclerView.layoutManager = linearLayoutManager
        binding.listMovieRecyclerView.adapter =
            listAdapter.withLoadStateFooter(PopularMoviesLoadStateAdapter { listAdapter.retry() })

    }

    private fun collectResults() {
        viewLifecycleOwner.lifecycleScope.launch {
            moviesViewModel.getPopularMovies().collectLatest {
                listAdapter.submitData(it)
            }
        }
    }

    private fun goToMovieDetails(movie: Movie) {
        val bundle = bundleOf("movie" to movie)
        findNavController().navigate(R.id.action_global_detailsMovies, bundle)
    }
}

