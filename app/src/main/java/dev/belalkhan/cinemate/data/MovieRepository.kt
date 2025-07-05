package dev.belalkhan.cinemate.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.belalkhan.cinemate.data.local.LocalMovie
import dev.belalkhan.cinemate.data.local.MovieDatabase
import dev.belalkhan.cinemate.data.remote.MovieRemoteMediator
import dev.belalkhan.cinemate.data.remote.MovieService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: MovieService,
    private val db: MovieDatabase
) {
    fun getMoviesStream(): Flow<PagingData<LocalMovie>> {
        val pagingSourceFactory = { db.movieDao.getMovies() }
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
            ),
            remoteMediator = MovieRemoteMediator(
                db = db,
                service = service
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}