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
import io.ktor.client.HttpClient
import io.ktor.http.URLProtocol

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    fun providesHttpClient(builder: CinemateHttpClientBuilder): HttpClient = builder
        .protocol(URLProtocol.HTTPS)
        .host("api.themoviedb.org/3")
        .authToken(AUTH_TOKEN)
        .build()

    @Provides
    fun providesMovieDatabase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movies_db",
        ).build()

    @Provides
    fun providesMoviePager(
        db: MovieDatabase,
        mediator: MovieRemoteMediator,
    ): Pager<Int, LocalMovie> = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = mediator,
        pagingSourceFactory = { db.movieDao.pagingSource() },
    )
}
