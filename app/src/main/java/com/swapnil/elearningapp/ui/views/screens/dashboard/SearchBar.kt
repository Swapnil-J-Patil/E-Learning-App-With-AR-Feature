package com.swapnil.elearningapp.ui.views.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.swapnil.elearningapp.data.Lesson
import com.swapnil.elearningapp.ui.theme.blue
import com.swapnil.elearningapp.viewmodel.DashboardViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(navController: NavController) {
    var searchText by remember { mutableStateOf("") }
    val viewmodel: DashboardViewModel = viewModel()
    var lesson by remember { mutableStateOf<Lesson?>(null) }

    Card(
        modifier = Modifier
            .padding(15.dp, 60.dp, 15.dp, 15.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                tint = Color.Gray,
                modifier = Modifier
                    .padding(start = 12.dp, end = 8.dp)
                    .size(30.dp)
            )
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White), // Set background color to white
                placeholder = {
                    Text(
                        "Search topics",
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                },
                singleLine = true, // Ensure single line input
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = Color.Black,
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent, // Remove bottom line when focused
                    unfocusedIndicatorColor = Color.Transparent // Remove bottom line when unfocused
                )

            )

            Text(
                text = "Search  ",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    viewmodel.fetchchapterByChapterName(chapterName = searchText) { data ->
                        lesson = data
                    }
                },
                color = blue,
            )
        }
    }

    LaunchedEffect(lesson) {
        lesson?.let {
            val links = it.videoLinks.joinToString(",") { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) }
            val chapter = it.lessonName
            val subject = it.subjectName
            val chapterNumber = it.chapterNumber

            navController.navigate("video_lessons/${links}/${chapter}/$subject/$chapterNumber")
        }
    }
}
