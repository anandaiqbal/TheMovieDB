package id.indocyber.api_service.usecase

import id.indocyber.api_service.service.GenresService
import id.indocyber.common.base.AppResponse
import id.indocyber.common.entity.genres.Genre
import kotlinx.coroutines.flow.flow

class GenresUseCase(private val genresService: GenresService) {
    operator fun invoke() = flow<AppResponse<List<Genre>>> {
        emit(AppResponse.loading())
        val genreData = genresService.getGenre()
        if (genreData.isSuccessful)
            emit(genreData.body()?.let {
                AppResponse.success(it.genres)
            } ?: run {
                AppResponse.failure(
                    Exception("Invalid Data")
                )
            })
        else {
            emit(AppResponse.failure(Exception("Invalid Data")))
        }
    }
}