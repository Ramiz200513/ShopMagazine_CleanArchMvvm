package com.example.shopmagazine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shopmagazine.presentation.screen.navigation.AppNavGraph
import com.example.shopmagazine.presentation.screen.navigation.Screen
import com.example.shopmagazine.presentation.ui.components.CustomBottomBar
import com.example.shopmagazine.ui.theme.ShopMagazineTheme
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopMagazineTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(

                    bottomBar = {
                        if (currentRoute != Screen.Login.route) {
                            CustomBottomBar(navController = navController)
                        }
                    },

                    containerColor = MaterialTheme.colorScheme.background

                ) { paddingValues ->
                    AppNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}
