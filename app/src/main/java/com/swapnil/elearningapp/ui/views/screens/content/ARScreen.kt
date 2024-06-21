package com.swapnil.elearningapp.ui.views.screens.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController

@Composable
fun ARScreen(asset: String, navController: NavController) {
    var animation by remember {
        mutableStateOf("idle")
    }

    ARView(animation,asset)
}