package com.swapnil.elearningapp.ui.views.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.swapnil.elearningapp.viewmodel.DashboardViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CourseList(navController: NavController) {

    val viewModel: DashboardViewModel = viewModel()
    val imageResources by viewModel.imageResources
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    var currentIndex by remember { mutableStateOf(0) }


    LaunchedEffect(Unit) {
        while (true) {
            coroutineScope.launch {
                // Calculate the next index to scroll to
                val nextIndex = (currentIndex + 1) % imageResources.size
                lazyListState.animateScrollToItem(nextIndex)
                currentIndex = nextIndex
            }
            delay(3000) // Adjust the delay to control the speed of scrolling (e.g., 3000 ms = 3 seconds)
        }
    }
    LazyRow(state = lazyListState, modifier = Modifier.padding(start = 6.dp)) {
        items(imageResources.size) { index ->
            Card(
                modifier = Modifier
                    .padding(10.dp, 25.dp, 10.dp, 0.dp)
                    .height(200.dp)
                    .fillMaxWidth()
                    .clickable {
                        if(index == 0) {
                            val subject = "Physics" // Set the subject dynamically if needed
                            navController.navigate("lessons/$subject")
                        }
                        else if(index == 1) {
                            val subject = "Biology" // Set the subject dynamically if needed
                            navController.navigate("lessons/$subject")
                        }
                        else if(index == 2) {
                            val subject = "Chemistry" // Set the subject dynamically if needed
                            navController.navigate("lessons/$subject")
                        }
                        },
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 12.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                content = {
                    Image(
                        painter = painterResource(id = imageResources[index]),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
        }
    }
}