package ir.dunijet.studentManager.util

import com.google.gson.JsonObject
import ir.dunijet.studentManager.model.Student

fun studentTOJsonObject(student: Student): JsonObject {
    val jsonObject = JsonObject()
    jsonObject.addProperty("name", student.name)
    jsonObject.addProperty("course", student.course)
    jsonObject.addProperty("score", student.score)
    return jsonObject
}