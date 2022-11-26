package com.example.roomex.room.converters

import androidx.room.TypeConverter
import java.util.stream.Collectors

class QuestionAnswersConverter {

    @TypeConverter
    fun fromListOfAnswers(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toListOfAnswers(string: String): List<String> {
        return string.split(',')
    }

}