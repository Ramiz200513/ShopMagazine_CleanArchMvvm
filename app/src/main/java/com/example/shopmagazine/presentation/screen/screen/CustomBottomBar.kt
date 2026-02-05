package com.example.shopmagazine.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shopmagazine.presentation.screen.navigation.Screen

@Composable
fun CustomBottomBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 16.dp, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)), // Тень сверху
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomBarItem(
                label = "Catalog",
                icon = if (currentRoute == Screen.Catalog.route) Icons.Filled.Home else Icons.Outlined.Home,
                isSelected = currentRoute == Screen.Catalog.route,
                onClick = {
                    if (currentRoute != Screen.Catalog.route) {
                        navController.navigate(Screen.Catalog.route) {
                            popUpTo(Screen.Catalog.route) { inclusive = true }
                        }
                    }
                }
            )

            BottomBarItem(
                label = "Cart",
                icon = if (currentRoute == Screen.Cart.route) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
                isSelected = currentRoute == Screen.Cart.route,
                onClick = {
                    if (currentRoute != Screen.Cart.route) {
                        navController.navigate(Screen.Cart.route)
                    }
                }
            )
        }
    }
}

@Composable
fun BottomBarItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = if (isSelected) Color.Black else Color.Gray
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )

        if (isSelected) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}