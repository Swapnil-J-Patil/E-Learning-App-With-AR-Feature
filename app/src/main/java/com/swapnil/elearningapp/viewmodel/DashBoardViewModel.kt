package com.swapnil.elearningapp.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.swapnil.elearningapp.R
import com.swapnil.elearningapp.data.Assessments
import com.swapnil.elearningapp.data.AssetsClass
import com.swapnil.elearningapp.data.Course
import com.swapnil.elearningapp.data.Lesson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class DashboardViewModel : ViewModel() {

    private val _imageResources = mutableStateOf<List<Int>>(emptyList())
    val imageResources: State<List<Int>> = _imageResources

    private val _courseList = mutableStateOf<List<Course>>(emptyList())
    val courseList: State<List<Course>> = _courseList

    private val _assessmentList = mutableStateOf<List<Assessments>>(emptyList())
    val assessmentList: State<List<Assessments>> = _assessmentList

    private val _personName = mutableStateOf("")
    val personName: State<String> = _personName

    private val _lesson = MutableLiveData<Lesson?>(null)
    val lesson: LiveData<Lesson?> = _lesson

    private val _videoLinks = MutableStateFlow<List<String>>(emptyList())
    val videoLinks: StateFlow<List<String>> = _videoLinks

    private val _assetsList = MutableStateFlow<List<AssetsClass>>(emptyList())
    val assetsList: StateFlow<List<AssetsClass>> = _assetsList

    private val _rotationalDynamicsLesson = mutableStateOf(
        Lesson(
            subjectName = "Physics",
            lessonName = "Rotational Dynamics",
            numberOfLessons = "5",
            duration = "5h 11m",
            videoLinks = listOf(
                "https://youtu.be/mvRiD68aO9A?si=QBcATjYb1O14JQlY",
                "https://youtu.be/v16k-k4e2Mk?si=-7hX5h-89ihpUE3P",
                "https://youtu.be/pALxLOMKyZo?si=igZ3qumWUDgS2SDA",
                "https://youtu.be/MMhKVHViZYw?si=CQ8xgIaifdUFvtZp",
                "https://youtu.be/GL_lalGgK0g?si=vMhykVaVvjUZN-W_",
                "https://youtu.be/M2Isk5nQOpw?si=Rn2-yY71zwelYP6y"
            ),
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/elearningapp-d8c38.appspot.com/o/lesson_images%2F1000017585?alt=media&token=5f437606-c398-4fd8-af79-f41454db2a2c",
            chapterNumber = "1",
            weightage = "7"
        )
    )
    val rotationalDynamicsLesson: State<Lesson> = _rotationalDynamicsLesson
    private val _biotechnologyLesson = mutableStateOf(
        Lesson(
            subjectName = "Biology",
            lessonName = "Biotechnology",
            numberOfLessons = "6",
            duration = "3h 33m",
            videoLinks = listOf(
                "https://youtu.be/_00Nk0S7Mzs?si=WLW-TZadKioZ12po",
                "https://youtu.be/7Ao0fM96178?si=38rZpINycy8be5E-",
                "https://youtu.be/Vo4rVM9sFog?si=C8cRWvW6eQi3Al-b",
                "https://youtu.be/foRIrnxzb1g?si=kV_FZpKf0kg_QHBO",
                "https://youtu.be/zE1flBriSgs?si=Dhl4DZaMrJlHuTTj",
                "https://youtu.be/SmFinBAHepE?si=J0lNZG1Omu4KoaOl"
            ),
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/elearningapp-d8c38.appspot.com/o/lesson_images%2F1000021454?alt=media&token=ebb47e7a-eeb1-435b-b114-efd28dbfb820",
            chapterNumber = "12",
            weightage = "7"
        )
    )
    val biotechnologyLesson: State<Lesson> = _biotechnologyLesson

    private val _electrochemistryLesson = mutableStateOf(
        Lesson(
            subjectName = "Chemistry",
            lessonName = "Electrochemistry",
            numberOfLessons = "6",
            duration = "4h 17m",
            videoLinks = listOf(
                "https://youtu.be/oHyl2f1ppVg?si=gJAZeK1-Ujvc3oii",
                "https://youtu.be/ZIqHsaUx3Lk?si=GxHnDbrI97D32MkH",
                "https://youtu.be/EZjLLXnIMGE?si=MLSPP2odPejU3lKz",
                "https://youtu.be/O6jADBRGE94?si=kwiYQ8YIWPSpsvY0",
                "https://youtu.be/gHlBulEOfIk?si=FmU8AjG3TDIJFzq0",
                "https://youtu.be/4gflZZZ7YHQ?si=s_iVFTZJUQobsuIj"
            ),
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/elearningapp-d8c38.appspot.com/o/lesson_images%2F1000023306?alt=media&token=56aa64c5-6e89-4a22-8db2-42395714b793",
            chapterNumber = "5",
            weightage = "7"
        )
    )
    val electrochemistryLesson: State<Lesson> = _electrochemistryLesson
    init {
        fetchPersonName() // Fetch the person's name when ViewModel is initialized
        fetchImageResources()
        fetchCourses()
        fetchAssessments()
        setAssets()
    }

    private fun fetchImageResources() {
        // Fetch image resources from API or other source
        // For demonstration purposes, I'll use hardcoded image resources
        _imageResources.value = listOf(
            R.drawable.physics,
            R.drawable.bio,
            R.drawable.chem,
           /* R.drawable.maths,
            R.drawable.english*/
        )
    }
    private fun fetchCourses() {
        // Fetch image resources from API or other source
        // For demonstration purposes, I'll use hardcoded image resources
        _courseList.value=listOf(
            Course(R.drawable.rotationalmotion, "Rotational Dynamics", 5, "5h 11m"),
            Course(R.drawable.biotechnology, "Biotechnology", 6, "3h 33m"),
            Course(R.drawable.electrochemistry, "Electrochemisty", 6, "4h 17m"),
            // Add more courses as needed
        )
    }
    private fun fetchAssessments() {
        // Fetch image resources from API or other source
        // For demonstration purposes, I'll use hardcoded image resources
        _assessmentList.value=listOf(
            Assessments(R.drawable.magneticfield, "Magnetic Fields due to Electric Current", 10, "20m"),
            Assessments(R.drawable.respiration, "Respiration and Circulation", 10, "20m"),
            Assessments(R.drawable.biomolecules, "Biomolecules", 10, "20m"),
            // Add more courses as needed
        )
    }

    private fun fetchPersonName() {
        // Perform your data fetching here to get the person's name
        // For demonstration purposes, I'll use a hardcoded name
        _personName.value = "Swapnil"
    }

    fun fetchLessons(onLessonsFetched: (List<Lesson>) -> Unit, subjectName: String) {
        val database = FirebaseDatabase.getInstance()
        val lessonsRef = database.getReference("lessons")

        lessonsRef.orderByChild("subjectName").equalTo(subjectName).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lessons = mutableListOf<Lesson>()
                for (lessonSnapshot in snapshot.children) {
                    val lesson = lessonSnapshot.toLesson()
                    if (lesson != null) {
                        lessons.add(lesson)
                    } else {
                        Log.e("FetchLessons", "Failed to convert snapshot to Lesson: ${lessonSnapshot.key}")
                    }
                }
                // Debugging: Log all fetched lessons before sorting
                lessons.forEach { Log.d("FetchedLesson", "Chapter: ${it.chapterNumber}, Name: ${it.lessonName}") }

                // Sort lessons by chapter number
                lessons.sortBy { it.chapterNumber.toIntOrNull() ?: Int.MAX_VALUE }

                // Debugging: Log all lessons after sorting
                lessons.forEach { Log.d("SortedLesson", "Chapter: ${it.chapterNumber}, Name: ${it.lessonName}") }

                onLessonsFetched(lessons)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FetchLessons", "Failed to fetch lessons: ${error.message}")
            }
        })
    }

    fun fetchPopularLessons(onLessonsFetched: (List<Lesson>) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val lessonsRef = database.getReference("lessons")

        lessonsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lessons = mutableListOf<Lesson>()
                for (lessonSnapshot in snapshot.children) {
                    val lesson = lessonSnapshot.toLesson()
                    if (lesson != null && lesson.weightage.toInt() > 6) {
                        lessons.add(lesson)
                    } else {
                        Log.e("FetchLessons", "Failed to convert snapshot to Lesson: ${lessonSnapshot.key}")
                    }
                }
                lessons.sortBy { it.chapterNumber.toIntOrNull() ?: Int.MAX_VALUE }
                onLessonsFetched(lessons)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FetchLessons", "Failed to fetch lessons: ${error.message}")
            }
        })
    }

    fun fetchImageUrlByChapterName(chapterName: String, onImageUrlFetched: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val database = FirebaseDatabase.getInstance()
                val lessonsRef = database.getReference("lessons")

                // Fetch data snapshot asynchronously
                val dataSnapshot = lessonsRef.child(chapterName).get().await()
                val imageUrl = dataSnapshot.child("imageUrl").value as? String
                if (imageUrl != null) {
                    onImageUrlFetched(imageUrl)
                } else {
                    Log.e("fetchImageUrlByChapterName", "Image URL not found for chapter: $chapterName")
                    onImageUrlFetched(null)
                }
            } catch (e: Exception) {
                Log.e("fetchImageUrlByChapterName", "Failed to fetch image URL: ${e.message}")
                onImageUrlFetched(null)
            }
        }
    }

    fun fetchchapterByChapterName(chapterName: String,onLessonsFetched: (Lesson) -> Unit) {
        viewModelScope.launch {
            try {
                val database = FirebaseDatabase.getInstance()
                val lessonsRef = database.getReference("lessons")

                // Fetch data snapshot asynchronously
                val dataSnapshot = lessonsRef.child(chapterName).get().await()
                val lesson = dataSnapshot.toLesson()
                if (lesson != null) {
                   _lesson.value=lesson
                    onLessonsFetched(lesson)
                    Log.d("FetchLessons", _lesson.value.toString())

                } else {
                    Log.e("FetchLessons", "Not found for chapter: $chapterName")

                }
            } catch (e: Exception) {
                Log.e("FetchLessons", "Failed to fetch chapter: ${e.message}")

            }
        }
    }
    private fun setAssets()
    {
        _assetsList.value= listOf(

            //Biology
            AssetsClass(assetName = "Human Heart", actualAssetName = "heart", chapterName = "Respiration and Circulation", subjectName = "Biology",chapterNumber =8),
            AssetsClass(assetName = "Human Lungs", actualAssetName = "lungs", chapterName = "Respiration and Circulation", subjectName = "Biology",chapterNumber =8),
            AssetsClass(assetName = "Human Brain", actualAssetName = "brain", chapterName = "Control and Co-ordination", subjectName = "Biology",chapterNumber =9),
            AssetsClass(assetName = "Animal Cell ", actualAssetName = "cell", chapterName = "Control and Co-ordination", subjectName = "Biology",chapterNumber =9),
            AssetsClass(assetName = "Human Cell", actualAssetName = "humancell", chapterName = "Control and Co-ordination", subjectName = "Biology",chapterNumber =9),
            AssetsClass(assetName = "Digestive System", actualAssetName = "digestivesystem", chapterName = "Control and Co-ordination", subjectName = "Biology",chapterNumber =9),
            AssetsClass(assetName = "DNA and RNA", actualAssetName = "dna", chapterName = "Biotechnology", subjectName = "Biology",chapterNumber =12),

            //Chemistry
            AssetsClass(assetName = "NaCl", actualAssetName = "nacl", chapterName = "Solid State", subjectName = "Chemistry",chapterNumber =1),
            AssetsClass(assetName = "Alpha Bond", actualAssetName = "alphabond", chapterName = "Halogen Derivatives", subjectName = "Chemistry", chapterNumber = 10),
            AssetsClass(assetName = "Anti Bond", actualAssetName = "antibond", chapterName = "Halogen Derivatives", subjectName = "Chemistry", chapterNumber = 10),
            AssetsClass(assetName = "Beryllium", actualAssetName = "beryllium", chapterName = "Transition and Inner transition Elements", subjectName = "Chemistry",chapterNumber =8),
            AssetsClass(assetName = "Biomolecules", actualAssetName = "biomolecules", chapterName = "Biomolecules", subjectName = "Chemistry",chapterNumber =14),

            //Physics
            AssetsClass(assetName = "Solenoid", actualAssetName = "solenoid", chapterName = "Magnetic Fields due to Electric Current", subjectName = "Physics", chapterNumber = 10),
            AssetsClass(assetName = "Magnetic Field", actualAssetName = "magnetism", chapterName = "Magnetic Fields due to Electric Current", subjectName = "Physics", chapterNumber = 10),

            )
    }

    fun DataSnapshot.toLesson(): Lesson? {
        return try {
            Lesson(
                subjectName = getString("subjectName"),
                lessonName = getString("lessonName"),
                numberOfLessons = getString("numberOfLessons"),
                duration = getString("duration"),
                videoLinks = getList("videoLinks"),
                imageUrl = getString("imageUrl"),
                chapterNumber = getString("chapterNumber"),
                weightage = getString("weightage")
            )
        } catch (e: Exception) {
            Log.e("toLesson", "Error parsing lesson: ${e.message}")
            null
        }
    }

    fun DataSnapshot.getString(fieldName: String): String {
        val value = child(fieldName).value
        return when (value) {
            is Long -> value.toString()
            is String -> value
            else -> ""
        }
    }

    fun DataSnapshot.getList(fieldName: String): List<String> {
        val value = child(fieldName).value
        return when (value) {
            is List<*> -> value.filterIsInstance<String>()
            is ArrayList<*> -> value.filterIsInstance<String>()
            else -> emptyList()
        }
    }
}