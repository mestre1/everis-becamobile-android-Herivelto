package com.example.praticetest.presenter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.praticetest.R
import com.example.praticetest.data.vo.Movie
import com.example.praticetest.databinding.SearchFragmentBinding
import com.example.praticetest.domain.utils.setToolbar
import com.example.praticetest.presenter.adapters.SearchMoviesPagingAdapter
import com.example.praticetest.presenter.viewmodel.SearchMoviesViewModel
import com.example.praticetest.domain.model.PopularMoviesLoadStateAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchMoviesFragment : Fragment() {

    private lateinit var listAdapter: SearchMoviesPagingAdapter
    private lateinit var binding: SearchFragmentBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val moviesViewModel: SearchMoviesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        view?.let { setToolbar(it.findViewById(R.id.appBar)) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideButtons()
        setRecyclerView()
        insertListener()
    }

    private fun insertListener() {
        binding.searchImageButton.setOnClickListener {
            val query = binding.searchTextInputEdit.text.toString()
            collectResults(query)
        }

        binding.detailsAppBar.appBar.setNavigationOnClickListener {
            goToMainActivity()
        }
    }

    private fun setRecyclerView() {
        listAdapter = SearchMoviesPagingAdapter() { movie -> goToMovieDetails(movie) }
        linearLayoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.searchRecyclerView.layoutManager = linearLayoutManager
        binding.searchRecyclerView.adapter =
            listAdapter.withLoadStateFooter(PopularMoviesLoadStateAdapter { listAdapter.retry() })
    }

    private fun collectResults(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            moviesViewModel.searchMovies(query).collectLatest {
                listAdapter.submitData(it)
            }
        }
    }

    private fun hideButtons() {
        binding.detailsAppBar.appBar.menu.findItem(R.id.search).isVisible = false
        activity?.activity_main_bottomNavigation?.isVisible = false
    }

    private fun goToMovieDetails(movie: Movie) {
        val bundle = bundleOf("movie" to movie)
        findNavController().navigate(R.id.action_global_detailsMovies, bundle)
    }

    private fun goToMainActivity() {
        findNavController().popBackStack()
        activity?.activity_main_bottomNavigation?.isVisible = true
    }


}
