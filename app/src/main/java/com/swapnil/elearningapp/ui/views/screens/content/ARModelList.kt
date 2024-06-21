package com.swapnil.elearningapp.ui.views.screens.content

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.swapnil.elearningapp.R
import com.swapnil.elearningapp.data.Lesson
import com.swapnil.elearningapp.ui.theme.blue
import com.swapnil.elearningapp.viewmodel.DashboardViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun ARModelList(navController: NavController) {
    var assets by remember { mutableStateOf<List<Lesson>>(emptyList()) }
    val viewModel: DashboardViewModel = viewModel()
    val allAssets = viewModel.assetsList
    // Fetch physics lessons when the composable is first launched
    /*LaunchedEffect(Unit) {
        viewModel.fetchLessons(subjectName = lesson,
            onLessonsFetched = { allLessons ->
                assets = allLessons
            }
        )
    }*/

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.TopCenter
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ar),
                contentDescription = "Your Image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 16.dp)
                    .background(Color.White)
                    .fillMaxSize()
            ) {
                items(allAssets.value) { allLessons ->
                    AssetCard(asset = allLessons, navController)
                }
            }
        }
    }
}