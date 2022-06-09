package id.indocyber.api_service.usecase

import id.indocyber.api_service.service.MovieDetailService
import id.indocyber.common.base.AppResponse
import id.indocyber.common.entity.movie_detail.MovieDetailResponse
import kotlinx.coroutines.flow.flow

class MovieDetailUseCase(
    private val movieDetailService: MovieDetailService
) {
    operator fun invoke(id: Int) =
        flow<AppResponse<MovieDetailResponse>> {
            emit(AppResponse.loading())
            val detailMovieData = movieDetailService.getMovieDetail(id)
            if (detailMovieData.isSuccessful) {
                detailMovieData.body()?.let { detail ->
                    emit(AppResponse.success(detail))
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