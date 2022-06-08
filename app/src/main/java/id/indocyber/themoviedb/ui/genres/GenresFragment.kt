package id.indocyber.themoviedb.ui.genres

import android.app.ProgressDialog
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.base.BaseFragment
import id.indocyber.themoviedb.R
import id.indocyber.themoviedb.databinding.GenresFragmentBinding
import id.indocyber.themoviedb.viewmodel.GenresViewModel

@AndroidEntryPoint
class GenresFragment : BaseFragment<GenresViewModel, GenresFragmentBinding>() {
    override val layoutResourceId: Int = R.layout.genres_fragment
    override val vm: GenresViewModel by viewModels()
    private val adapter = GenreAdapter {
        vm.getGenresForMovies(listOf(it.id))
    }

    override fun initBinding(binding: GenresFragmentBinding) {
        super.initBinding(binding)
        val dialog = ProgressDialog.show(
            context, "Loading",
            "Please wait...", true
        )
        binding.genresRecycler.adapter = adapter
        observeResponseData(vm.genreData, {
            dialog.dismiss()
            adapter.differ.submitList(it)
        }, {
            dialog.dismiss()
            binding.retryButt.visibility = View.VISIBLE
        }, {
            dialog.show()
        })
        binding.retryButt.setOnClickListener {
            vm.loadGenre()
            binding.retryButt.visibility = View.GONE
            binding.genresRecycler.visibility = View.VISIBLE
        }
    }


}