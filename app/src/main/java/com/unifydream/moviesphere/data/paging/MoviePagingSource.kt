package com.unifydream.moviesphere.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.unifydream.moviesphere.data.remote.MovieApiService
import com.unifydream.moviesphere.data.models.Movies
import com.unifydream.moviesphere.utlis.Constants.Companion.discoverListScreen
import com.unifydream.moviesphere.utlis.Constants.Companion.nowPlayingAllListScreen
import com.unifydream.moviesphere.utlis.Constants.Companion.popularAllListScreen
import com.unifydream.moviesphere.utlis.Constants.Companion.upcomingListScreen
import kotlinx.coroutines.delay

class MoviePagingSource(private val movieApiService: MovieApiService, private val tags: String) : PagingSource<Int, Movies>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movies> {
        return try {
            val nextPage = params.key ?: 1
            delay(3000L)
            val response = when(tags) {
                nowPlayingAllListScreen -> {
                    movieApiService.getNowPlayingMovies(page = nextPage)
                }
                discoverListScreen -> {
                    movieApiService.getDiscoverMovies(page = nextPage)
                }
                upcomingListScreen -> {
                    movieApiService.getUpcomingMovies(page = nextPage)
                }

                popularAllListScreen -> {
                    movieApiService.getPopularMovies(page = nextPage)

                }

                else -> {movieApiService.getPopularMovies(page = nextPage)}
            }
            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.page >= response.totalPages) null else response.page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movies>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
