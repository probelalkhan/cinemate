package dev.belalkhan.cinemate.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CinemateHttpClientBuilder @Inject constructor() {

    private lateinit var protocol: URLProtocol
    private lateinit var host: String
    private lateinit var authToken: String
    fun protocol(protocol: URLProtocol) = apply { this.protocol = protocol }

    fun host(host: String) = apply { this.host = host }

    fun authToken(authToken: String) = apply { this.authToken = authToken }

    fun build() = HttpClient(Android) {
        expectSuccess = true

        engine {
            socketTimeout = 10_000
            connectTimeout = 10_000
        }

        defaultRequest {
            url {
                protocol = this@CinemateHttpClientBuilder.protocol
                host = this@CinemateHttpClientBuilder.host
            }
            header(HttpHeaders.ContentType, "application/json")
            header(
                HttpHeaders.Authorization,
                "Bearer $authToken",
            )
        }

        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                },
            )
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.ALL
        }
    }
}
