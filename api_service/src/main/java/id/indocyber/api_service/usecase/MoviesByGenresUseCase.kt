package id.indocyber.api_service.usecase

import id.indocyber.api_service.paging.MoviesByGenrePager
import id.indocyber.api_service.service.MoviesByGenresService

class MoviesByGenresUseCase(private val moviesByGenresService: MoviesByGenresService) {
    operator fun invoke(genres: List<Int>) =
        MoviesByGenrePager.createPager(15, moviesByGenresService, genres).flow
}