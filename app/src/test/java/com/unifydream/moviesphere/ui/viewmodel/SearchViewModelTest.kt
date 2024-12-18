package com.unifydream.moviesphere.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.unifydream.moviesphere.data.models.Search
import com.unifydream.moviesphere.data.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchViewModel

    @Mock
    private lateinit var searchRepository: SearchRepository

    @Before
    fun setUp() {
        searchRepository = mock()
        viewModel = SearchViewModel(searchRepository)
    }

    @Test
    fun `searchRemoteMovie emits filtered PagingData`() = runTest {
        val mockSearchData = PagingData.from(
            listOf(
                Search(
                    id = 1,
                    title = "Jack Reacher",
                    originalTitle = "Jack Reacher",
                    originalName = null,
                    adult = false,
                    backdropPath = null,
                    genreIds = null,
                    genres = null,
                    imdbId = null,
                    mediaType = null,
                    originCountry = null,
                    originalLanguage = "en",
                    overview = null,
                    popularity = 10.0,
                    posterPath = null,
                    releaseDate = "2022-01-01",
                    runtime = null,
                    video = null,
                    voteAverage = 7.5,
                    voteCount = 100
                )
            )
        )
        val mockFlow: Flow<PagingData<Search>> = flow { emit(mockSearchData) }

        whenever(searchRepository.multiSearch("Jack Reacher", true)).thenReturn(mockFlow)

        viewModel.searchRemoteMovie(includeAdult = true)


        verify(searchRepository).multiSearch("Jack Reacher", true)
    }

    @Test
    fun `searchRemoteMovie does not call repository when searchParam is empty`() = runTest {
        viewModel.searchParam.value = ""

        viewModel.searchRemoteMovie(includeAdult = true)

        verify(searchRepository, never()).multiSearch(any(), any())
    }
}