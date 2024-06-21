package com.swapnil.elearningapp.ui.views.screens.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.swapnil.elearningapp.viewmodel.DashboardViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CourseCard(navController: NavController) {
    val viewModel: DashboardViewModel = viewModel()
    val courses by viewModel.courseList

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(310.dp)
    ) {
        LazyRow(modifier = Modifier.padding(top = 10.dp)) {
            itemsIndexed(courses) { index, course ->

                Card(
                    modifier = Modifier
                        .padding(15.dp, 0.dp, 15.dp, 10.dp)
                        .height(290.dp)
                        .clickable {
                            if(index == 0) {

                                val lesson=viewModel.rotationalDynamicsLesson.value
                                val links = lesson.videoLinks.joinToString(",") { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) }
                                val chapter=lesson.lessonName
                                val subject=lesson.subjectName
                                val chapterNumber= lesson.chapterNumber

                                navController.navigate("video_lessons/${links}/${chapter}/$subject/$chapterNumber")

                            }
                            else if(index == 1) {
                                val lesson=viewModel.biotechnologyLesson.value
                                val links = lesson.videoLinks.joinToString(",") { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) }
                                val chapter=lesson.lessonName
                                val subject=lesson.subjectName
                                val chapterNumber= lesson.chapterNumber

                                navController.navigate("video_lessons/${links}/${chapter}/$subject/$chapterNumber")
                            }
                            else if(index == 2) {
                                val lesson=viewModel.electrochemistryLesson.value
                                val links = lesson.videoLinks.joinToString(",") { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) }
                                val chapter=lesson.lessonName
                                val subject=lesson.subjectName
                                val chapterNumber= lesson.chapterNumber

                                navController.navigate("video_lessons/${links}/${chapter}/$subject/$chapterNumber")
                            }
                        }
                        .width(250.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 12.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = course.imageResourceId),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = course.name,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Lessons: ${course.numberOfLessons}",
                            style = TextStyle(fontWeight = FontWeight.Medium, color = Color.Gray)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,

                            ) {

                            Text(
                                text = "Duration: ${course.duration}",
                                style = TextStyle(fontWeight = FontWeight.Medium, color = Color.Gray)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = "Clock Icon",
                                tint = Color.Gray,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}