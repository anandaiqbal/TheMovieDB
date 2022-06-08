package id.indocyber.api_service.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.indocyber.api_service.service.MovieReviewService
import id.indocyber.common.entity.movie_review.Result

class MovieReviewPagingDataSource(
    private val movieReviewService: MovieReviewService,
    private val movie_id: Int
) : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val reviewMovieData = movieReviewService.getMovieReview(
                movie_id = movie_id,
                page = params.key ?: 1
            )
            reviewMovieData.body()?.let {
                LoadResult.Page(
                    data = it.results,
                    prevKey = if (it.page == 1) null else it.page.minus(1),
                    nextKey = if (it.results.isEmpty()) null else it.page.plus(1)
                )
            } ?: LoadResult.Error(Exception("Invalid Data"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

object MovieReviewPager {
    fun createPager(
        pageSize: Int,
        movieReviewService: MovieReviewService,
        movie_id: Int
    ): Pager<Int, Result> {
        return Pager(
            config = PagingConfig(pageSize),
            pagingSourceFactory = {
                MovieReviewPagingDataSource(movieReviewService, movie_id)
            }
        )
    }
}