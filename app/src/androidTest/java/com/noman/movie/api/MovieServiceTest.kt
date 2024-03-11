package com.noman.movie.api

import com.noman.movie.data.remote.client.MovieService
import com.noman.movie.utils.Constants
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var movieService: MovieService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        movieService = retrofit.create(MovieService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    suspend fun testGetMovies() {
        enqueueResponse("movies.json")

        val response = movieService.getMovies(Constants.API_KEY)

        val request = mockWebServer.takeRequest()
        assertEquals("/movie/popular?api_key=93392016e98fbcc722bcb5cb608881f0", request.path)

        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
    }

    @Test
    suspend fun testGetMovieDetails() {
        enqueueResponse("movie_details.json")

        val response = movieService.getMovieDetails("123456", Constants.API_KEY)

        val request = mockWebServer.takeRequest()
        assertEquals("/movie/123456?api_key=93392016e98fbcc722bcb5cb608881f0", request.path)

        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
    }

    @Test
    suspend fun testSearchMovies() {
        enqueueResponse("search_movies.json")

        val response = movieService.searchMovies(Constants.API_KEY, "query")

        val request = mockWebServer.takeRequest()
        assertEquals("/search/movie?api_key=93392016e98fbcc722bcb5cb608881f0&query=query", request.path)

        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
    }

    private fun enqueueResponse(fileName: String) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val source = inputStream?.bufferedReader().use { it?.readText() }
        mockWebServer.enqueue(MockResponse().setBody(source ?: ""))
    }
}