package dev.belalkhan.cinemate.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.belalkhan.cinemate.data.local.LocalMovie
import dev.belalkhan.cinemate.data.toMovie
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    pager: Pager<Int, LocalMovie>,
) : ViewModel() {

    val moviesPagingFlow =
        pager.flow.map { it.map { localMovie -> localMovie.toMovie() } }.cachedIn(viewModelScope)
}
