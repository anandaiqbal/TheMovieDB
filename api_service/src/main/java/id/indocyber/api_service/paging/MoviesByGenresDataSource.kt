package id.indocyber.api_service.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.indocyber.api_service.service.MoviesByGenresService
import id.indocyber.common.entity.movies_by_genres.Result

class MoviesByGenresDataSource(
    val moviesByGenresService: MoviesByGenresService,
    val genres: List<Int>
) : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val result =
                moviesByGenresService.discoverMovieByGenre(
                    genres.joinToString(","),
                    params.key ?: 1
                )
            result.body()?.let {
                LoadResult.Page(
                    it.results,
                    if (it.page == 1) null else it.page.minus(1),
                    if (it.results.isEmpty()) null else it.page.plus(1)
                )
            } ?: LoadResult.Error(Exception("Invalid Data"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

object MoviesByGenrePager {
    fun createPager(
        pageSize: Int,
        moviesByGenresService: MoviesByGenresService,
        genres: List<Int>
    ): Pager<Int, Result> {
        return Pager(config = PagingConfig(pageSize), pagingSourceFactory = {
            MoviesByGenresDataSource(moviesByGenresService, genres)
        })
    }
}