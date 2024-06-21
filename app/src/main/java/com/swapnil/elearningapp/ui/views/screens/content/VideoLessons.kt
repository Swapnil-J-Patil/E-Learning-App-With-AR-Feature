package com.swapnil.elearningapp.ui.views.screens.content

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import coil.compose.AsyncImage
import com.swapnil.elearningapp.R
import com.swapnil.elearningapp.ui.theme.blue
import com.swapnil.elearningapp.viewmodel.DashboardViewModel
import java.io.File
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun VideoLessons(
    links: List<String>?,
    chapter: String,
    subject: String,
    chapterNumber: String,
    navController: NavController
) {
    var selectedTab by remember { mutableStateOf("Lessons") }

    val viewModel: DashboardViewModel = viewModel()
    var image by remember { mutableStateOf("") }
    viewModel.fetchImageUrlByChapterName(
        chapterName = chapter,
        onImageUrlFetched = { url ->
            image = url.toString()
        }
    )
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
            Column {
                // Tabs
                Box {
                    Box {
                        AsyncImage(
                            model = image,
                            contentDescription = "Image",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(Color(0x55000000))
                        )
                    }
                    Text(
                        text = "$subject: Chapter $chapterNumber",
                        style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Medium),
                        color = Color.White,
                        modifier = Modifier
                            .padding(15.dp, 0.dp, 0.dp, 49.dp)
                            .align(Alignment.BottomStart)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = chapter,
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal),
                        color = Color.White,
                        modifier = Modifier
                            .padding(15.dp, 0.dp, 0.dp, 5.dp)
                            .align(Alignment.BottomStart)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TabItem(
                        title = "Lessons",
                        isSelected = selectedTab == "Lessons",
                        onClick = { selectedTab = "Lessons" }
                    )
                    TabItem(
                        title = "AR Learning",
                        isSelected = selectedTab == "AR Learning",
                        onClick = { selectedTab = "AR Learning" }
                    )
                }

                if (selectedTab == "Lessons") {

                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 16.dp)
                            .background(Color.White)
                            .fillMaxSize()
                    ) {
                        links?.let {
                            itemsIndexed(links) { index, eachLink ->
                                Card(
                                    modifier = Modifier
                                        .padding(2.dp, 15.dp, 2.dp, 8.dp)
                                        .clickable {
                                            // Handle click
                                        }
                                        .fillMaxWidth(),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 12.dp
                                    ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White,
                                    ),
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Chapter $chapterNumber:  Lesson ${index + 1}",
                                            style = TextStyle(
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Medium
                                            ),
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                        FloatingActionButton(
                                            onClick = {
                                                navController.navigate(
                                                    "video_lesson/${
                                                        URLEncoder.encode(
                                                            eachLink,
                                                            StandardCharsets.UTF_8.toString()
                                                        )
                                                    }"
                                                )
                                            },
                                            modifier = Modifier
                                                .size(45.dp),
                                            containerColor = blue,
                                            shape = CircleShape
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.PlayArrow,
                                                contentDescription = "Play",
                                                tint = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {

                    val filteredAssets by remember { derivedStateOf { viewModel.assetsList.value.filter { it.chapterName == chapter } } }
                    if (filteredAssets.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 16.dp)
                                .background(Color.White)
                        ) {
                            items(filteredAssets) { asset ->
                                Card(
                                    modifier = Modifier
                                        .padding(2.dp, 15.dp, 2.dp, 8.dp)
                                        .fillMaxWidth(),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 12.dp
                                    ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White,
                                    ),
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Asset: " + asset.assetName,
                                            style = TextStyle(
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Medium
                                            ),
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                        FloatingActionButton(
                                            onClick = {
                                                navController.navigate("ar_screen/${asset.actualAssetName}")
                                            },
                                            modifier = Modifier
                                                .size(45.dp),
                                            containerColor = blue,
                                            shape = CircleShape
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.PlayArrow,
                                                contentDescription = "Play",
                                                tint = Color.White
                                            )
                                        }
                                    }
                                }
                            }

                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No AR learning for this chapter",
                                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TabItem(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = if (isSelected) blue else Color.Gray,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .height(2.dp)
                .width(40.dp)
                .background(if (isSelected) blue else Color.Gray)
        )
    }
}