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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.swapnil.elearningapp.viewmodel.DashboardViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AssessmentCard(navController: NavController) {
    val viewModel: DashboardViewModel = viewModel()
    val assessments by viewModel.assessmentList

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
            .padding(bottom =30.dp)
    ) {
    LazyRow(modifier = Modifier.padding(top = 10.dp)) {
        itemsIndexed(assessments) { index,assessment ->

            Card(
                modifier = Modifier
                    .padding(15.dp, 0.dp, 15.dp, 10.dp)
                    .height(270.dp)
                    .clickable {
                        if(index == 0) {

                            val asset="solenoid"
                            navController.navigate("ar_screen/${asset}")
                        }
                        else if(index == 1) {
                            val asset="heart"
                            navController.navigate("ar_screen/${asset}")
                        }
                        else if(index == 2) {
                            val asset="biomolecules"
                            navController.navigate("ar_screen/${asset}")
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
                        painter = painterResource(id = assessment.imageResourceId),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = assessment.name,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    )

                }
                }
            }
        }
    }
}