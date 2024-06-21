package com.swapnil.elearningapp.ui.views.screens.content

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.swapnil.elearningapp.data.AssetsClass
import com.swapnil.elearningapp.data.Lesson

@Composable
fun AssetCard(asset: AssetsClass,navController: NavController) {
    Card(
        modifier = Modifier
            .padding(2.dp, 5.dp, 2.dp, 8.dp)
            .clickable {
                navController.navigate("ar_screen/${asset.actualAssetName}")
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
                text = "${asset.subjectName}: Chapter ${asset.chapterNumber} ",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = asset.chapterName,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Asset: "+asset.assetName,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
                color = Color.Black
            )
        }
    }
}