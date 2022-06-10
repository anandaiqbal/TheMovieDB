package id.indocyber.themoviedb.ui.genres

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
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
        vm.selectionTracker?.isSelected(it) ?: false
    }

    override fun initBinding(binding: GenresFragmentBinding) {
        super.initBinding(binding)
        binding.genresRecycler.adapter = adapter
        createTracker()
        observeResponseData(vm.genreData, {
            binding.loading.visibility = View.GONE
            binding.fab.visibility =View.VISIBLE
            adapter.differ.submitList(it)
        }, {
            binding.loading.visibility = View.GONE
            binding.retryButt.visibility = View.VISIBLE
        }, {
            binding.loading.visibility = View.VISIBLE
            binding.fab.visibility = View.GONE
        })
        binding.retryButt.setOnClickListener {
            vm.loadGenre()
            binding.retryButt.visibility = View.GONE
            binding.loading.visibility = View.GONE
            binding.genresRecycler.visibility = View.VISIBLE
        }
        binding.fab.setOnClickListener {
            vm.getGenresForMovies()
        }
    }

    fun createTracker() {
        vm.selectionTracker = vm.selectionTracker?.let {
            SelectionTracker.Builder<Long>(
                "genreId", binding.genresRecycler,
                GenreItemKeyProvider(adapter), GenreItemDetailsLookup(binding.genresRecycler),
                StorageStrategy.createLongStorage()
            ).withOnItemActivatedListener { itemDetails, event ->
                vm.selectionTracker?.select(itemDetails.selectionKey ?: 0)
                true
            }.withSelectionPredicate(SelectionPredicates.createSelectAnything()).build().apply {
                it.selection.forEach {
                    this.select(it)
                }
            }
        } ?: kotlin.run {
            SelectionTracker.Builder<Long>(
                "genreId", binding.genresRecycler,
                GenreItemKeyProvider(adapter), GenreItemDetailsLookup(binding.genresRecycler),
                StorageStrategy.createLongStorage()
            ).withOnItemActivatedListener { itemDetails, event ->
                vm.selectionTracker?.select(itemDetails.selectionKey ?: 0)
                true
            }.withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()
        }
    }

}