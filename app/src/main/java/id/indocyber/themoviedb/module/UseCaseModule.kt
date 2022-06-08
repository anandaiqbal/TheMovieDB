package id.indocyber.themoviedb.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import id.indocyber.api_service.service.*
import id.indocyber.api_service.usecase.GenresUseCase
import id.indocyber.api_service.usecase.MovieDetailUseCase
import id.indocyber.api_service.usecase.MovieReviewUseCase
import id.indocyber.api_service.usecase.MoviesByGenresUseCase

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideGenresUseCase(genresService: GenresService) = GenresUseCase(genresService)

    @Provides
    fun provideMoviesByGenresUseCase(moviesByGenresService: MoviesByGenresService) =
        MoviesByGenresUseCase(moviesByGenresService)

    @Provides
    fun provideMovieDetailUseCase(
        movieDetailService: MovieDetailService,
        movieVideoService: MovieVideoService
    ) =
        MovieDetailUseCase(movieDetailService, movieVideoService)

    @Provides
    fun provideMovieReviewUseCase(
        movieReviewService: MovieReviewService
    ) = MovieReviewUseCase(movieReviewService)
}