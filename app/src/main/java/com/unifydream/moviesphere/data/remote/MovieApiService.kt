package com.unifydream.moviesphere.data.remote

import com.unifydream.moviesphere.data.remote.response.MovieDetails
import com.unifydream.moviesphere.data.remote.response.CastResponse
import com.unifydream.moviesphere.data.remote.response.MovieResponse
import com.unifydream.moviesphere.data.remote.response.GenreResponse
import com.unifydream.moviesphere.data.remote.response.MultiSearchResponse
import com.unifydream.moviesphere.utlis.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendedMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("discover/movie")
    suspend fun getGenreWiseMovieList(
        @Query("with_genres") genresId: Int,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") filmId: Int,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("discover/movie?")
    suspend fun getDiscoverMovies(
        @Query("page") page: Int = 0,
        @Query("primary_release_date.gte") gteReleaseDate: String = "1940-01-01",
        @Query("primary_release_date.lte") lteReleaseDate: String = "1981-01-01",
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en",
        @Query("sort_by") sortBy: String = "vote_count.desc"
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMoviesDetails(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") appendToResponse: String = "videos,credits",
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en",
    ): MovieDetails
    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") filmId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): CastResponse

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): GenreResponse

    @GET("search/multi")
    suspend fun multiSearch(
        @Query("query") searchParams: String,
        @Query("page") page: Int = 0,
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MultiSearchResponse
}