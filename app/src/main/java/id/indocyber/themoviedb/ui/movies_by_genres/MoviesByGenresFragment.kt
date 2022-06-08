package id.indocyber.themoviedb.ui.movies_by_genres

import android.app.ProgressDialog
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.base.BaseFragment
import id.indocyber.themoviedb.R
import id.indocyber.themoviedb.databinding.MoviesByGenresFragmentBinding
import id.indocyber.themoviedb.viewmodel.MoviesByGenresViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesByGenresFragment : BaseFragment<MoviesByGenresViewModel,
        MoviesByGenresFragmentBinding>() {
    override val layoutResourceId: Int = R.layout.movies_by_genres_fragment
    override val vm: MoviesByGenresViewModel by viewModels()
    val adapter = MoviesByGenreAdapter {
        vm.getMoviesForDetail(it.id)
    }
    val args by navArgs<MoviesByGenresFragmentArgs>()

    override fun initBinding(binding: MoviesByGenresFragmentBinding) {
        super.initBinding(binding)
        val dialog = ProgressDialog.show(
            context, "Loading",
            "Please wait...", true
        )
        binding.moviesRecycler.adapter = adapter
        vm.loadMovies(args.genres.toList())
        vm.moviesByGenreData.observe(this@MoviesByGenresFragment) {
            CoroutineScope(Dispatchers.Main).launch {
                adapter.submitData(it)
            }
        }
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading && adapter.itemCount == 0) {
                dialog.show()
            } else if (it.refresh is LoadState.Error && adapter.itemCount == 0) {
                dialog.dismiss()
                binding.moviesRecycler.visibility = View.GONE
                binding.retryButt.visibility = View.VISIBLE
            } else if (it.refresh is LoadState.NotLoading) {
                dialog.dismiss()
            }
        }
        binding.retryButt.setOnClickListener{
            adapter.retry()
            binding.moviesRecycler.visibility = View.VISIBLE
            binding.retryButt.visibility = View.GONE
        }
    }
}