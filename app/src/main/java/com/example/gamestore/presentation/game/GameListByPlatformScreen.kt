package com.example.gamestore.presentation.game

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.gamestore.presentation.platform.PlatformGameViewModel
import com.example.gamestore.presentation.utils.SearchBar
import androidx.compose.ui.unit.dp
import android.util.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameListScreenFilteredByPlatfrom(
    navController: NavController,
    platformId: Int,
    platformName: String, // <<< platformName
    onGameClick: (Int) -> Unit
) {
    val viewModel = remember { PlatformGameViewModel(platformId) }
    val pagingItems = viewModel.games.collectAsLazyPagingItems()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            Column {
                SearchBar(
                    title = "Search for game",
                    navController = navController,
                    value = searchQuery,
                    onValueChange = { viewModel.onQueryChanged(it) }
                )
                Text(
                    text = platformName,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    )

    { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey(),
                contentType = pagingItems.itemContentType()
            ) { index ->
                val item = pagingItems[index]
                if (item != null) {
                    GameListItem(
                        game = item,
                        onClick = {
                            onGameClick(item.id)
                        }
                    )
                    //Log.d("PlatformGame", "Clicked game with id: ${item.id}")
                }
            }
        }
    }
}
