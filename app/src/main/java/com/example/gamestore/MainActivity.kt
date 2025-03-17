package com.example.gamestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gamestore.ui.composables.LeftAppBar
import com.example.gamestore.ui.composables.TopAppBar
import com.example.gamestore.ui.screens.LoginScreen
import com.example.gamestore.ui.screens.MainScreen
import com.example.gamestore.ui.theme.GamestoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GamestoreTheme {
                GameStoreApp()
            }
        }
    }
}

@Composable
fun GameStoreApp(modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            LeftAppBar(onNavigateToSignIn = { navController.navigate("sign") })  // Pass lambda
        },
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = { TopAppBar(drawerState) },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = "home") {
                    MainScreen(modifier)
                }
                composable(route = "sign") {
                    LoginScreen(navController)  // Ensure you implement this screen
                }
            }
        }
    }
}
