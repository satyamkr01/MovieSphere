package com.unifydream.moviesphere.data.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.unifydream.moviesphere.data.models.Search
import com.unifydream.moviesphere.data.paging.SearchFilmSource
import com.unifydream.moviesphere.data.remote.MovieApiService
import com.unifydream.moviesphere.data.remote.response.MultiSearchResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import retrofit2.HttpException

class SearchRepositoryTest {

    private val api: MovieApiService = mock()

    @Test
    fun `SearchFilmSource load returns expected data`() = runTest {
        val fakeSearch = listOf(
            Search(
                adult = false,
                backdropPath = null,
                genreIds = emptyList(),
                genres = emptyList(),
                id = 1,
                imdbId = null,
                mediaType = null,
                originCountry = emptyList(),
                originalLanguage = null,
                originalName = null,
                originalTitle = "Test Original Title",
                overview = "Test Overview",
                popularity = null,
                posterPath = null,
                releaseDate = null,
                title = "Test Movie",
                video = false,
                runtime = null,
                voteAverage = null,
                voteCount = null
            ),
            Search(
                adult = false,
                backdropPath = null,
                genreIds = emptyList(),
                genres = emptyList(),
                id = 2,
                imdbId = null,
                mediaType = null,
                originCountry = emptyList(),
                originalLanguage = null,
                originalName = null,
                originalTitle = "Another Original Title",
                overview = "Another Overview",
                popularity = null,
                posterPath = null,
                releaseDate = null,
                title = "Another Movie",
                video = false,
                runtime = null,
                voteAverage = null,
                voteCount = null
            )
        )

        val mockResponse = MultiSearchResponse(results = fakeSearch, page = 1, totalResults = 2, totalPages = 1)

        `when`(api.multiSearch(searchParams = "test", page = 1, includeAdult = true)).thenReturn(mockResponse)

        val pagingSource = SearchFilmSource(api, "test", true)

        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(loadResult is PagingSource.LoadResult.Page)
        val page = loadResult as PagingSource.LoadResult.Page
        assertEquals(fakeSearch, page.data)
        assertEquals(null, page.prevKey)
        assertEquals(2, page.nextKey)
        verify(api).multiSearch(searchParams = "test", page = 1, includeAdult = true)
    }

    @Test
    fun `SearchFilmSource load handles HttpException`() = runTest {
        val httpException = mock(HttpException::class.java)
        `when`(api.multiSearch(searchParams = "test", page = 1, includeAdult = true)).thenThrow(httpException)

        val pagingSource = SearchFilmSource(api, "test", true)

        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(loadResult is PagingSource.LoadResult.Error)
        val error = loadResult as PagingSource.LoadResult.Error
        assertTrue(error.throwable is HttpException)
    }

    @Test
    fun `SearchFilmSource getRefreshKey returns correct key`() {
        val pagingState = PagingState(
            pages = listOf(
                PagingSource.LoadResult.Page(
                    data = listOf(
                        Search(
                            adult = false,
                            backdropPath = null,
                            genreIds = emptyList(),
                            genres = emptyList(),
                            id = 1,
                            imdbId = null,
                            mediaType = null,
                            originCountry = emptyList(),
                            originalLanguage = null,
                            originalName = null,
                            originalTitle = "Test Original Title",
                            overview = "Test Overview",
                            popularity = null,
                            posterPath = null,
                            releaseDate = null,
                            title = "Test Movie",
                            video = false,
                            runtime = null,
                            voteAverage = null,
                            voteCount = null
                        )
                    ),
                    prevKey = null,
                    nextKey = 2
                )
            ),
            anchorPosition = 0,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        val pagingSource = SearchFilmSource(api, "test", true)
        val refreshKey = pagingSource.getRefreshKey(pagingState)

        assertEquals(0, refreshKey)
    }
}