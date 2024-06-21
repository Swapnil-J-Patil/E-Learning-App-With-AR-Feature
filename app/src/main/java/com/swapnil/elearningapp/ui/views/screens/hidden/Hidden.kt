package com.swapnil.elearningapp.ui.views.screens.hidden

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.storage


// Data class to represent your data model
data class Lesson(
    val subjectName: String,
    val lessonName: String,
    val numberOfLessons: String,
    val duration: String,
    val videoLinks: List<String>, // Change to List<String>
    val imageUrl: String,
    val threeDAssets: List<String>,
    val chapterNumber: String,
    val weightage: String
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonForm(
    subjectName: String,
    onSubjectNameChange: (String) -> Unit,
    lessonName: String,
    onLessonNameChange: (String) -> Unit,
    numberOfLessons: String,
    onNumberOfLessonsChange: (String) -> Unit,
    duration: String,
    onDurationChange: (String) -> Unit,
    videoLinks: List<String>,
    onVideoLinksChange: (List<String>) -> Unit,
    onSubmit: (Lesson) -> Unit,
    threeDAssets: List<String>,
    onThreeDAssetsChange: (List<String>) -> Unit,
    chapterNumber: String,
    onChapterNumberChange: (String) -> Unit,
    weightage:String,
    onWeightageChange: (String) -> Unit,
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val scrollState1 = rememberScrollState()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState1)
            .padding(8.dp)
            .background(Color.White)
    ) {
        TextField(
            value = subjectName,
            onValueChange = onSubjectNameChange,
            label = { Text("Subject Name") }
        )
        TextField(
            value = lessonName,
            onValueChange = onLessonNameChange,
            label = { Text("Lesson Name") }
        )
        TextField(
            value = chapterNumber,
            onValueChange = onChapterNumberChange,
            label = { Text("Chapter Number") }
        )
        TextField(
            value = weightage,
            onValueChange = onWeightageChange,
            label = { Text("Weightage") }
        )
        TextField(
            value = numberOfLessons,
            onValueChange = onNumberOfLessonsChange,
            label = { Text("Number of Lessons") }
        )
        TextField(
            value = duration,
            onValueChange = onDurationChange,
            label = { Text("Duration") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier =Modifier.height(100.dp)
            .fillMaxWidth()) {
            items(videoLinks.size) { index ->
                TextField(
                    value = videoLinks.getOrNull(index) ?: "",
                    onValueChange = { newValue ->
                        val updatedLinks = videoLinks.toMutableList()
                        if (index < updatedLinks.size) {
                            updatedLinks[index] = newValue
                        } else {
                            updatedLinks.add(newValue)
                        }
                        onVideoLinksChange(updatedLinks)
                    },
                    label = { Text("Video Link ${index + 1}") }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier =Modifier.height(100.dp)
            .fillMaxWidth()) {
            items(threeDAssets.size) { index ->
                TextField(
                    value = threeDAssets.getOrNull(index) ?: "",
                    onValueChange = { newValue ->
                        val updatedAssets = threeDAssets.toMutableList()
                        if (index < updatedAssets.size) {
                            updatedAssets[index] = newValue
                        } else {
                            updatedAssets.add(newValue)
                        }
                        onThreeDAssetsChange(updatedAssets)
                    },
                    label = { Text("3D Asset URL ${index + 1}") }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Button to add a new TextField
        Button(
            onClick = {
                val updatedAssets = threeDAssets.toMutableList()
                updatedAssets.add("")
                onThreeDAssetsChange(updatedAssets)
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add 3D Asset URL")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Pick Image")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val updatedLinks = videoLinks.toMutableList()
                updatedLinks.add("")
                onVideoLinksChange(updatedLinks)
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add Video Link")
        }
        Spacer(modifier = Modifier.height(8.dp))
        imageUri?.let { uri ->
            Button(onClick = {
                uploadImageToFirebase(uri) { imageUrl ->
                    val lesson = Lesson(subjectName, lessonName, numberOfLessons, duration, videoLinks, imageUrl,threeDAssets,chapterNumber, weightage) // Change videoLink to videoLinks
                    onSubmit(lesson)
                }
            }) {
                Text("Submit")
            }
        }
    }

}
private fun uploadImageToFirebase(uri: Uri, onComplete: (String) -> Unit) {
    val storage = Firebase.storage
    val storageRef = storage.reference
    val imagesRef = storageRef.child("lesson_images/${uri.lastPathSegment}")

    val uploadTask = imagesRef.putFile(uri)

    uploadTask.addOnSuccessListener { _ ->
        // Get the download URL
        imagesRef.downloadUrl.addOnSuccessListener { uri ->
            onComplete(uri.toString())
        }.addOnFailureListener {
            // Handle failures
        }
    }.addOnFailureListener { exception ->
        // Handle unsuccessful uploads
    }
}
// Usage
@Composable
fun MyApp() {
    var subjectName by remember { mutableStateOf("") }
    var chapterNumber by remember { mutableStateOf("") }
    var weightage by remember { mutableStateOf("") }

    var lessonName by remember { mutableStateOf("") }
    var numberOfLessons by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var videoLinks by remember { mutableStateOf(listOf<String>()) } // Define videoLinks as a mutableStateOf<List<String>>()
    var threeDAssets by remember { mutableStateOf(listOf<String>()) }

    val context = LocalContext.current

    var submittedLesson by remember { mutableStateOf<Lesson?>(null) }

    // Write data to Firebase Realtime Database
    submittedLesson?.let { lesson ->
        Firebase.database.reference.child("lessons").child(lesson.lessonName).setValue(lesson)
            .addOnSuccessListener {
                // Show toast message if data is uploaded successfully
                Toast.makeText(context, "Data uploaded successfully", Toast.LENGTH_SHORT).show()

                // Reset all text field values
                //subjectName = ""
                lessonName = ""
                numberOfLessons = ""
                duration = ""
                videoLinks = emptyList() // Reset videoLinks to an empty list
                threeDAssets= emptyList()
            }
            .addOnFailureListener { exception ->
                // Handle error if data upload fails
                Toast.makeText(context, "Failed to upload data: ${exception.message}", Toast.LENGTH_SHORT).show()
            }

        // Reset submittedLesson after writing to database
        submittedLesson = null
    }

    LessonForm(
        subjectName = subjectName,
        onSubjectNameChange = { subjectName = it },
        lessonName = lessonName,
        onLessonNameChange = { lessonName = it },
        numberOfLessons = numberOfLessons,
        onNumberOfLessonsChange = { numberOfLessons = it },
        duration = duration,
        onDurationChange = { duration = it },
        videoLinks = videoLinks,
        onVideoLinksChange = { videoLinks = it },
        threeDAssets = threeDAssets,
        onThreeDAssetsChange = { threeDAssets = it },
        chapterNumber = chapterNumber,
        onChapterNumberChange = { chapterNumber = it },
        weightage = weightage,
        onWeightageChange = { weightage = it },
        onSubmit = { submittedLesson = it } // Include the onSubmit parameter
    )

}
// Function to fetch data from Firebase
