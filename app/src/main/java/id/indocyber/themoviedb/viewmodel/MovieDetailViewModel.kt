package id.indocyber.themoviedb.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.usecase.MovieDetailUseCase
import id.indocyber.api_service.usecase.MovieReviewUseCase
import id.indocyber.common.base.AppResponse
import id.indocyber.common.base.BaseViewModel
import id.indocyber.common.entity.movie_detail.Genre
import id.indocyber.common.entity.movie_detail.MovieDetailResponse
import id.indocyber.common.entity.movie_review.Result
import id.indocyber.common.entity.movie_video.MovieVideoResponse
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    application: Application,
    val movieDetailUseCase: MovieDetailUseCase,
    val movieReviewUseCase: MovieReviewUseCase
) : BaseViewModel(application) {
    val movieDetailData =
        MutableLiveData<AppResponse<Pair<MovieDetailResponse, MovieVideoResponse>>>()
    val movieReviewData = MutableLiveData<PagingData<Result>>()

    fun genreListToNames(genres: List<Genre>?) =
        genres.orEmpty().map { it.name }.toTypedArray().joinToString(separator = ", ")

    fun loadDetail(movies: Int) {
        viewModelScope.launch {
            movieDetailUseCase(movies).collect {
                movieDetailData.postValue(it)
            }
            movieReviewUseCase(movies).collect {
                movieReviewData.postValue(it)
            }
        }
    }
}
