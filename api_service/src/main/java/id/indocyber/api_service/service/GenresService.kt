package id.indocyber.api_service.service

import id.indocyber.common.entity.genres.GenreResponse
import id.indocyber.common.base.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresService {
    @GET("genre/movie/list")
    suspend fun getGenre(
        @Query("api_key") api_key: String = Constants.API_KEY
    ): Response<GenreResponse>
}