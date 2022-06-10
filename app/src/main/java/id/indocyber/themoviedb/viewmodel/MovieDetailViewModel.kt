package id.indocyber.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.usecase.MovieDetailUseCase
import id.indocyber.api_service.usecase.MovieReviewUseCase
import id.indocyber.api_service.usecase.MovieVideoUseCase
import id.indocyber.common.base.AppResponse
import id.indocyber.common.base.BaseViewModel
import id.indocyber.common.entity.movie_detail.Genre
import id.indocyber.common.entity.movie_detail.MovieDetailResponse
import id.indocyber.common.entity.movie_review.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    application: Application,
    val movieDetailUseCase: MovieDetailUseCase,
    val movieVideoUseCase: MovieVideoUseCase,
    val movieReviewUseCase: MovieReviewUseCase
) : BaseViewModel(application) {
    val movieDetailData =
        MutableLiveData<AppResponse<MovieDetailResponse>>()
    val movieVideoData =
        MutableLiveData<AppResponse<id.indocyber.common.entity.movie_video.Result>>()
    val movieReviewData = MutableLiveData<PagingData<Result>>()

    fun genreListToNames(genres: List<Genre>?) =
        genres.orEmpty().map { it.name }.toTypedArray().joinToString(separator = ", ")

    fun loadDetail(movies: Int) {
        viewModelScope.launch {
            movieDetailUseCase(movies).collect {
                movieDetailData.postValue(it)
            }
            movieVideoUseCase(movies).collect {
                movieVideoData.postValue(it)
            }
            movieReviewUseCase(movies).cachedIn(CoroutineScope(Dispatchers.IO)).collect {
                movieReviewData.postValue(it)
            }
        }
    }
}
