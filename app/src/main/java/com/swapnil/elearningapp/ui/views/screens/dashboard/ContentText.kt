package com.swapnil.elearningapp.ui.views.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.swapnil.elearningapp.ui.theme.blue

@Composable
fun ContentText(first: String, second: String,call:String,navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(15.dp, 40.dp, 20.dp, 8.dp)
            .fillMaxWidth()
            .background(Color.White)

    ) {
        Text(
            text = first,
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color.Black)
        )

        Text(
            text = second,
            modifier = Modifier
                .clickable {
                    if(call=="popular")
                    {
                        navController.navigate("popular_lessons")
                    }
                    else
                    {
                         navController.navigate("ar_assets")
                    }
                },
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium, color = blue)
        )
    }
}