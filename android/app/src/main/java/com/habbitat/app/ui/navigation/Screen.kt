package com.habitAt.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Rounded.Home)
    object Calendar : Screen("calendar", "Calendar", Icons.Rounded.CalendarToday)
    object Settings : Screen("settings", "Settings", Icons.Rounded.Settings)
    object ProofCapture : Screen("proof/{habitId}", "Proof Capture", Icons.Rounded.Home)

    companion object {
        fun navigationItems(): List<Screen> = listOf(Home, Calendar, Settings)
    }
}
