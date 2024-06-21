package com.swapnil.elearningapp.ui.views.screens.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swapnil.elearningapp.ui.theme.blue
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.LightEstimationMode
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.ArNode
import io.github.sceneview.ar.node.PlacementMode


@Composable
fun ARView(animation:String,asset:String) {

    val nodes = remember(animation) {
        mutableStateListOf<ArNode>()
    }
    val modelNode = remember {
        mutableStateOf<ArModelNode?>(null)
    }
    val placeModel = remember {
        mutableStateOf(true)
    }
    var previousAnimation by remember {
        mutableStateOf("idle")
    }

    Box(modifier = Modifier.fillMaxSize()){
        ARScene(
            modifier = Modifier.fillMaxSize(),
            nodes = nodes,
            planeRenderer = true,
            onCreate = {arSceneView ->
                arSceneView.lightEstimationMode = LightEstimationMode.DISABLED
                modelNode.value = ArModelNode(PlacementMode.INSTANT).apply {
                    loadModelGlbAsync("models/$asset.glb", scaleToUnits = 3f) {
                        playAnimation(animation)
                    }

                    onAnchorChanged = {
                        placeModel.value = !isAnchored
                    }

                }

                nodes.add(modelNode.value!!)
            }
        )

        if(placeModel.value){
            Button(
                onClick = {
                modelNode.value?.anchor()
                },
                colors = ButtonDefaults.buttonColors(containerColor = blue),
                modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)) {
                Text(text = "Place it",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Medium
                    ),
                )
            }
        }

    }

    LaunchedEffect(key1 = animation){
        modelNode.value?.stopAnimation(previousAnimation)
        modelNode.value?.playAnimation(animation)
        previousAnimation = animation
    }
}