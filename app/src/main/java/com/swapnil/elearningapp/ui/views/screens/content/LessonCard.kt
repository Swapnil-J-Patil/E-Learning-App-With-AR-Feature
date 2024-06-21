package com.swapnil.elearningapp.ui.views.screens.content

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.swapnil.elearningapp.data.Lesson
import com.swapnil.elearningapp.viewmodel.DashboardViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun LessonCard(lesson: Lesson,navController:NavController,viewModel: DashboardViewModel) {
    val links = lesson.videoLinks.joinToString(",") { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) }
    val chapter=lesson.lessonName
    val subject=lesson.subjectName
    val chapterNumber= lesson.chapterNumber
    Log.d("videoLink", "video Links in Lesson: ${links}")

    Card(
        modifier = Modifier
            .padding(2.dp, 5.dp, 2.dp, 8.dp)
            .clickable {
                navController.navigate("video_lessons/${links}/${chapter}/$subject/$chapterNumber")
            }
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(
                text = "${lesson.subjectName}: Chapter ${lesson.chapterNumber} ",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = lesson.lessonName,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                color = Color.Black
            )
        }
    }
}