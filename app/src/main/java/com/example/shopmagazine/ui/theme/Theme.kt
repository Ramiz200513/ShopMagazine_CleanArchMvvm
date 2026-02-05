package com.example.shopmagazine.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Настраиваем только светлую тему (для простоты)
private val LightColorScheme = lightColorScheme(
    primary = Black,              // Кнопки, заголовки
    onPrimary = White,            // Текст на кнопках
    background = LightGray,       // Фон приложения
    surface = White,              // Фон карточек
    onSurface = Black,            // Текст на карточках
    error = ErrorRed,
    secondary = DarkGray
)

@Composable
fun ShopMagazineTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Делаем статус бар (где часы) белым с черными иконками
            window.statusBarColor = White.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}