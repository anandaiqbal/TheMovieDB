package id.indocyber.api_service.service

import id.indocyber.common.base.Constants
import id.indocyber.common.entity.movie_detail.MovieDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailService {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String = Constants.API_KEY
    ): Response<MovieDetailResponse>
}