package dev.belalkhan.cinemate.data.remote

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dev.belalkhan.cinemate.data.local.LocalMovie
import dev.belalkhan.cinemate.data.local.MovieDatabase
import dev.belalkhan.cinemate.data.toLocalMovie
import javax.inject.Inject

class MovieRemoteMediator @Inject constructor(
    private val db: MovieDatabase,
    private val repository: MovieRepository,
) : RemoteMediator<Int, LocalMovie>() {

    override suspend fun initialize(): InitializeAction = InitializeAction.SKIP_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalMovie>,
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true,
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val remoteMovies = repository.getMovies(page = loadKey)

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.movieDao.clearAll()
                }
                val localMovies = remoteMovies.movies.map { it.toLocalMovie() }
                db.movieDao.upsertAll(localMovies)
            }

            return MediatorResult.Success(
                endOfPaginationReached = remoteMovies.movies.isEmpty(),
            )
        } catch (e: Exception) {
            return MediatorResult.Error(
                throwable = e
            )
        }
    }
}
