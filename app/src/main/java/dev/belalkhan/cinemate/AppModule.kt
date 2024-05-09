package dev.belalkhan.cinemate

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.belalkhan.cinemate.data.local.LocalMovie
import dev.belalkhan.cinemate.data.local.MovieDatabase
import dev.belalkhan.cinemate.data.remote.CinemateHttpClientBuilder
import dev.belalkhan.cinemate.data.remote.MovieRemoteMediator
import dev.belalkhan.cinemate.movies.Movie
import io.ktor.client.HttpClient
import io.ktor.http.URLProtocol

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    fun providesHttpClient(builder: CinemateHttpClientBuilder): HttpClient = builder
        .protocol(URLProtocol.HTTPS)
        .host("api.themoviedb.org/3/")
        .authToken("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiYTM4ZTc0NmU4ODRhMTMzZDcxN2Y1OTgyMjI2Y2FkYSIsInN1YiI6IjYzM2Q2NDBjY2Y0OGExMDA4MjI0ZTVlOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.zOOy77MmAZ4XIdBsIpioZxDic_2egpUNd5Cyv9HZlFs")
        .build()

    @Provides
    fun providesMovieDatabase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movies_db"
        ).build()

    @Provides
    fun providesMoviePager(
        db: MovieDatabase,
        mediator: MovieRemoteMediator
    ): Pager<Int, LocalMovie> = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = mediator,
        pagingSourceFactory = { db.movieDao.pagingSource() }
    )
}