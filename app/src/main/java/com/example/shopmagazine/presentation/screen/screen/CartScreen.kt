package com.example.shopmagazine.presentation.screen.screen

import android.R.attr.clickable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shopmagazine.data.local.model.CartWithProduct
import com.example.shopmagazine.presentation.viewModel.CartWithProductViewModel

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartWithProductViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    if (state.items.isEmpty()) {
        EmptyCartView(onGoShopping = { navController.navigate("catalog_screen") })
    } else {
        Box(modifier = Modifier.fillMaxSize())
        {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 96.dp)
                    .background(Color(0xFFF5F5F5)),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(
                    items = state.items,
                    key = { it.cartItem.id }
                ) { item ->
                    CartItemSwipeable(
                        item = item,
                        onRemove = { viewModel.deleteItem(item.cartItem) }
                    ) {
                        CartItemRow(
                            item = item,
                            onIncrease = { viewModel.increaseQuantity(item.product.id) },
                            onDecrease = { viewModel.decreaseQuantity(item.cartItem) }
                        )
                    }
                }

            }
            FloatingTotalBar(
                total = state.totalPrice,
                onCheckout = { /* оформить */ },
                onClear = { viewModel.clearCart() },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun EmptyCartView(onGoShopping: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = Color.LightGray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Your cart is empty",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onGoShopping) {
            Text("Go Shopping")
        }
    }
}

@Composable
fun CartItemRow(
    item: CartWithProduct,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.product.image,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .padding(4.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f),) {
            Text(
                text = item.product.title,
                maxLines = 1,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "$${item.product.price}",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
        ) {
            IconButton(onClick = onDecrease, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = if (item.cartItem.quantity > 1) Icons.Default.Remove else Icons.Default.Delete,
                    contentDescription = "Remove",
                    modifier = Modifier.size(16.dp)
                )
            }

            Text(
                text = item.cartItem.quantity.toString(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            IconButton(onClick = onIncrease, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun FloatingTotalBar(
    total: Double,
    onCheckout: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 💰 Total
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Итого",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "$${"%.2f".format(total)}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(onClick = onClear) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Clear cart"
                )
            }

            Button(
                onClick = onCheckout,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.height(48.dp)
            ) {
                Text("Оформить")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartItemSwipeable(
    item: CartWithProduct,
    onRemove: () -> Unit,
    content: @Composable () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onRemove()
                true
            } else false
        },
        positionalThreshold = { totalDistance -> totalDistance * 0.4f }
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) Color(0xFFD32F2F) else Color(0xFFFF5252),
        label = "bgColorAnimation"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) 1.3f else 1.0f,
        label = "iconScaleAnimation"
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(backgroundColor, RoundedCornerShape(12.dp))
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.scale(iconScale)
                )
            }
        },
        content = { content() }
    )
}