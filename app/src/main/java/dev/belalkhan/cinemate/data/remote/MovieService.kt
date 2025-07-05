package dev.belalkhan.cinemate.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.text.get

class MovieServiceException(message: String, cause: Throwable? = null) : Exception(message, cause)

class MovieService @Inject constructor(private val httpClient: HttpClient) {
    suspend fun getMovies(page: Int): MovieResponse {
        delay(2_000) // Simulating network delay
        return try {
            httpClient.get("movie/popular") {
                parameter("language", "en-US")
                parameter("page", page)
            }.body()
        } catch (e: ClientRequestException) {
            throw MovieServiceException("Client error: ${e.response.status}", e)
        } catch (e: ServerResponseException) {
            throw MovieServiceException("Server error: ${e.response.status}", e)
        } catch (e: IOException) {
            throw MovieServiceException("Network error: ${e.localizedMessage}", e)
        } catch (e: Exception) {
            throw MovieServiceException("Unknown error: ${e.localizedMessage}", e)
        }
    }
}
