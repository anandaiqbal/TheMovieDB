package id.indocyber.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.selection.SelectionTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.usecase.GenresUseCase
import id.indocyber.common.base.AppResponse
import id.indocyber.common.base.BaseViewModel
import id.indocyber.common.entity.genres.Genre
import id.indocyber.themoviedb.ui.genres.GenresFragmentDirections
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    application: Application,
    val genresUseCase: GenresUseCase
) :
    BaseViewModel(application) {
    var selectionTracker: SelectionTracker<Long>? = null
    val genreData = MutableLiveData<AppResponse<List<Genre>>>()

    init {
        loadGenre()
    }

    fun loadGenre() {
        viewModelScope.launch {
            genresUseCase().collect {
                genreData.value = it
            }
        }
    }

    fun getGenresForMovies() {
        navigate(
            GenresFragmentDirections.actionGenreFragmentToMoviesByGenresFragment(
                selectionTracker?.selection?.toList().orEmpty().map {
                    it.toInt()
                }.toIntArray()
            )
        )
    }
}