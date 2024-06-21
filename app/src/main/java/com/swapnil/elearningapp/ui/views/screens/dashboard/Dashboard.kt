package com.swapnil.elearningapp.ui.views.screens.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.swapnil.elearningapp.R
import com.swapnil.elearningapp.ui.theme.blue
import com.swapnil.elearningapp.viewmodel.DashboardViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Dashboard(navcontroller: NavController) {
    val viewModel: DashboardViewModel = viewModel()
    val scrollState1 = rememberScrollState()
    var selectedTab by remember { mutableStateOf(0) }


    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                // Icon to open the sidebar
                IconButton(onClick = {   },
                    modifier = Modifier
                        .padding(top=5.dp, start = 15.dp, bottom=5.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.edtechapp), // Replace with your image
                        contentDescription = "Logo Image",
                        contentScale = ContentScale.Crop
                    )
                }
                // Text "Hi" followed by person's name
                Text(
                    text = " Hello Learners!",
                    modifier = Modifier
                        .padding(top=5.dp,bottom=5.dp),
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Medium, color = Color.Black),
                    )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color.White,
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White,
                elevation = 8.dp
            ) {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = "Home",
                            tint = if (selectedTab == 0) blue else Color.Gray
                        )
                    },
                    label = { Text("Home",color = if (selectedTab == 0) blue else Color.Gray) },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.Filled.Book,
                            contentDescription = "Books",
                            tint = if (selectedTab == 1) blue else Color.Gray
                        )
                    },
                    label = { Text("Books",color = if (selectedTab == 1) blue else Color.Gray) },
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        navcontroller.navigate("books")
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.Filled.Description,
                            contentDescription = "Papers",
                            tint = if (selectedTab == 2) blue else Color.Gray
                        )
                    },
                    label = { Text("Papers",color = if (selectedTab == 2) blue else Color.Gray) },
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                        val link="https://drive.google.com/drive/folders/1mzT7SXD_EE2y0g-Lcm7KTkMTL9uqxDn6?usp=sharing"
                        navcontroller.navigate(
                            "video_lesson/${
                                URLEncoder.encode(
                                    link,
                                    StandardCharsets.UTF_8.toString()
                                )
                            }"
                        )
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.Filled.Info,
                            contentDescription = "Disclaimer",
                            tint = if (selectedTab == 3) blue else Color.Gray
                        )
                    },
                    label = { Text("About", color = if (selectedTab == 3) blue else Color.Gray) },
                    selected = selectedTab == 3,
                    onClick = {
                        selectedTab = 3
                        navcontroller.navigate("about_us")
                    }
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState1)
                    .fillMaxSize(),
            ) {

                Spacer(modifier = Modifier.height(5.dp))
                SearchBar(navcontroller)
                CourseList(navcontroller)

                ContentText(first = "Popular Lessons", second ="See All",call="popular",navcontroller )
                CourseCard(navcontroller)

                ContentText(first = "AR Learning", second ="See All",call="ar",navcontroller )
                AssessmentCard(navcontroller)

            }
        }
    )
}