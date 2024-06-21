package com.swapnil.elearningapp.data

data class Lesson(
    val subjectName: String = "",
    val lessonName: String = "",
    val numberOfLessons: String = "",
    val duration: String = "",
    val videoLinks: List<String> = emptyList(),
    val imageUrl: String = "",
    val chapterNumber: String = "",
    val weightage: String = ""
)