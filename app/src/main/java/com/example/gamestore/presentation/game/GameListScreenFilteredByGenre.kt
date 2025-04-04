

//paging
package com.example.gamestore.presentation.game

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.gamestore.presentation.genre.GenreGameViewModel
import com.example.gamestore.presentation.utils.AppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameListScreenFilteredByGenre(
    navController: NavController, // add navController
    genreSlug: String,
    onGameClick: (Int) -> Unit
) {
    val viewModel = remember { GenreGameViewModel(genreSlug) }
    val pagingItems = viewModel.games.collectAsLazyPagingItems()

    Scaffold(
//        topBar = {
//            TopAppBar(title = { Text("Games in $genreSlug") })
//        }
        topBar = {
            AppTopBar(title = "Games in $genreSlug", navController = navController) // call AppTopBar have button Search
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(pagingItems) { game ->
                if (game != null) {
                    GameListItem(game = game, onClick = {
                        onGameClick(game.id)
                    })
                }
            }
        }
    }
}





