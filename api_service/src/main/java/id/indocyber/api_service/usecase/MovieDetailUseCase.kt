package id.indocyber.api_service.usecase

import id.indocyber.api_service.service.MovieDetailService
import id.indocyber.api_service.service.MovieVideoService
import id.indocyber.common.base.AppResponse
import id.indocyber.common.entity.movie_detail.MovieDetailResponse
import id.indocyber.common.entity.movie_video.MovieVideoResponse
import kotlinx.coroutines.flow.flow

class MovieDetailUseCase(
    private val movieDetailService: MovieDetailService,
    private val movieVideoService: MovieVideoService
) {
    operator fun invoke(id: Int) =
        flow<AppResponse<Pair<MovieDetailResponse, MovieVideoResponse>>> {
            emit(AppResponse.loading())
            val detailMovieData = movieDetailService.getMovieDetail(id)
            if (detailMovieData.isSuccessful) {
                detailMovieData.body()?.let { detail ->
                    val videoMovieData = movieVideoService.getMovieVideo(id)
                    if (videoMovieData.isSuccessful) {
                        videoMovieData.body()?.let { video ->
                            emit(AppResponse.success(detail to video))
                        } ?: run {
                            emit(
                                AppResponse.failure(
                                    Exception("Invalid Data")
                                )
                            )
                        }
                    } else {
                        emit(
                            AppResponse.failure(
                                Exception("Invalid Data")
                            )
                        )
                    }
                } ?: run {
                    emit(
                        AppResponse.failure(
                            Exception("Invalid Data")
                        )
                    )
                }
            } else {
                emit(
                    AppResponse.failure(
                        Exception("Invalid Data")
                    )
                )
            }
        }
}