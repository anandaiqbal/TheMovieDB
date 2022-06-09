package id.indocyber.themoviedb.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import id.indocyber.api_service.service.*
import id.indocyber.api_service.usecase.*

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
        movieDetailService: MovieDetailService
    ) =
        MovieDetailUseCase(movieDetailService)

    @Provides
    fun provideMovieVideoUseCase(
        movieVideoService: MovieVideoService
    ) = MovieVideoUseCase(movieVideoService)

    @Provides
    fun provideMovieReviewUseCase(
        movieReviewService: MovieReviewService
    ) = MovieReviewUseCase(movieReviewService)
}