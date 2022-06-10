package id.indocyber.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.usecase.MoviesByGenresUseCase
import id.indocyber.common.base.BaseViewModel
import id.indocyber.common.entity.movies_by_genres.Result
import id.indocyber.themoviedb.ui.movies_by_genres.MoviesByGenresFragmentDirections
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesByGenresViewModel @Inject constructor(
    application: Application,
    val moviesByGenresUseCase: MoviesByGenresUseCase
) : BaseViewModel(application) {
    val moviesByGenreData = MutableLiveData<PagingData<Result>>()

    fun loadMovies(genres: List<Int>) {
        if (moviesByGenreData.value == null) {
            viewModelScope.launch {
                moviesByGenresUseCase.invoke(genres).cachedIn(viewModelScope).collect {
                    moviesByGenreData.postValue(it)
                }
            }
        } else {
            moviesByGenreData.postValue(moviesByGenreData.value)
        }
    }

    fun getMoviesForDetail(movie: Int) {
        navigate(
            MoviesByGenresFragmentDirections.actionMoviesByGenresFragmentToMovieDetailFragment(
                movie
            )
        )
    }
}