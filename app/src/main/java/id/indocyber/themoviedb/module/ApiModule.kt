package id.indocyber.themoviedb.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.indocyber.api_service.retrofit.RetrofitClient
import id.indocyber.api_service.service.*
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context) = RetrofitClient.getClient(context)

    @Provides
    @Singleton
    fun provideGenres(retrofit: Retrofit) = retrofit.create(GenresService::class.java)

    @Provides
    @Singleton
    fun provideMoviesByGenres(retrofit: Retrofit) =
        retrofit.create(MoviesByGenresService::class.java)

    @Provides
    @Singleton
    fun provideMovieDetail(retrofit: Retrofit) = retrofit.create(MovieDetailService::class.java)

    @Provides
    @Singleton
    fun provideMovieReview(retrofit: Retrofit) = retrofit.create(MovieReviewService::class.java)

    @Provides
    @Singleton
    fun provideMovieVideo(retrofit: Retrofit) = retrofit.create(MovieVideoService::class.java)
}