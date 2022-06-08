package id.indocyber.api_service.service

import id.indocyber.common.entity.movies_by_genres.MoviesByGenreResponse
import id.indocyber.common.base.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesByGenresService {
    @GET("discover/movie")
    suspend fun discoverMovieByGenre(
        @Query("with_genres") with_genres: String,
        @Query("page") page: Int = 1,
        @Query("api_key") api_key: String = Constants.API_KEY,
    ): Response<MoviesByGenreResponse>
}