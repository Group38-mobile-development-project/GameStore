package com.example.gamestore.presentation.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gamestore.data.model.Game
import com.example.gamestore.data.repository.GenreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GenreGameViewModel(
    genreSlug: String,
    repository: GenreRepository = GenreRepository()
) : ViewModel() {
    val games: Flow<PagingData<Game>> = repository
        .getGamesByGenrePaged(genreSlug)
        .cachedIn(viewModelScope)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun onQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }
}

