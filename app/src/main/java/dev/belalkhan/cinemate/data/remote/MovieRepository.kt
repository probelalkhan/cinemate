package dev.belalkhan.cinemate.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class MovieRepository @Inject constructor(private val httpClient: HttpClient) {
    suspend fun getMovies(page: Int): MovieResponse = httpClient.get("movie/popular") {
        parameter("language", "en-US")
        parameter("page", page)
    }.body()
}
