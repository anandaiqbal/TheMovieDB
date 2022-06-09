package id.indocyber.api_service.usecase

import id.indocyber.api_service.service.MovieVideoService
import id.indocyber.common.base.AppResponse
import id.indocyber.common.entity.movie_video.Result
import kotlinx.coroutines.flow.flow

class MovieVideoUseCase(
    private val movieVideoService: MovieVideoService
) {
    operator fun invoke(id: Int) = flow<AppResponse<Result>> {
        emit(AppResponse.loading())
        val videoMovieData = movieVideoService.getMovieVideo(id)
        if (videoMovieData.isSuccessful) {
            videoMovieData.body()?.let { video ->
                val filter = video.results.filter {
                    it.type == "Trailer"
                }
                if (video.results.isEmpty()) {
                    emit(
                        AppResponse.failure(
                            Exception("Invalid Data")
                        )
                    )
                } else
                    emit(AppResponse.success(filter[0]))
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