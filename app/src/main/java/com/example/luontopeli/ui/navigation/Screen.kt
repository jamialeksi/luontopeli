package com.example.luontopeli.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Map : Screen("map", "Kartta", Icons.Filled.Map)
    object Camera : Screen("camera", "Kamera", Icons.Filled.CameraAlt)
    object Discover : Screen("discover", "Löydöt", Icons.Filled.Explore)
    object Stats : Screen("stats", "Tilastot", Icons.Filled.BarChart)

    companion object {
        val bottomNavScreens = listOf(Map, Camera, Discover, Stats)
    }
}