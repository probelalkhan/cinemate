package dev.belalkhan.cinemate.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.belalkhan.cinemate.data.MovieRepository
import dev.belalkhan.cinemate.data.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val pagingDataFlow = loadMovies().cachedIn(viewModelScope)

    private fun loadMovies(): Flow<PagingData<Movie>> = repository.getMoviesStream().map { pagingData ->
        pagingData.map { localMovie -> localMovie.toMovie() }
    }

}
