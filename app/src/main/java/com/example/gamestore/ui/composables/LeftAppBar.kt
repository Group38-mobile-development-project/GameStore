package com.example.gamestore.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun LeftAppBar(
    onNavigateToSignIn: () -> Unit,
    onSignOut: () -> Unit,
    drawerState: DrawerState
) {
    val auth = remember { FirebaseAuth.getInstance() }
    var userEmail by remember { mutableStateOf(auth.currentUser?.email) }
    val scope = rememberCoroutineScope()

    // Listen for auth state changes
    DisposableEffect(auth) {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            userEmail = firebaseAuth.currentUser?.email
        }
        auth.addAuthStateListener(listener)

        onDispose {
            auth.removeAuthStateListener(listener)
        }
    }

    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(12.dp))

            // Show user email if logged in, otherwise show "Not logged in"
            Text(
                text = userEmail ?: "Not logged in",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider()

            Text("Shop", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
            NavigationDrawerItem(
                label = { Text("ShoppingCart") },
                selected = false,
                icon = { Icon(Icons.Outlined.ShoppingCart, contentDescription = null) },
                onClick = { /* Handle shopping cart */ }
            )
            NavigationDrawerItem(
                label = { Text("WishList") },
                selected = false,
                icon = { Icon(Icons.Outlined.Favorite, contentDescription = null) },
                onClick = { /* Handle wishlist */ }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text("Management", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
            NavigationDrawerItem(
                label = { Text("Settings") },
                selected = false,
                icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                badge = { Text("20") }, // Placeholder badge
                onClick = { /* Handle settings */ }
            )

            if (userEmail == null) {
                // Show Login button if user is not logged in
                NavigationDrawerItem(
                    label = { Text("Login") },
                    selected = false,
                    icon = { Icon(Icons.Outlined.AccountCircle, contentDescription = null) },
                    onClick = {
                        onNavigateToSignIn()
                        scope.launch { drawerState.close() }
                    }
                )
            } else {
                // Show Logout button if user is logged in
                NavigationDrawerItem(
                    label = { Text("Logout") },
                    selected = false,
                    icon = { Icon(Icons.Outlined.AccountCircle, contentDescription = null) },
                    onClick = {
                        auth.signOut() // Sign out the user
                        userEmail = null // Immediately update UI
                        onSignOut() // Navigate back to home
                        scope.launch { drawerState.close() }
                    }
                )
            }

            Spacer(Modifier.height(12.dp))
        }
    }
}
