package com.rizwanamjadnov.labterminal.utils

import com.rizwanamjadnov.labterminal.models.Student
import org.json.JSONArray
import org.json.JSONObject

class NetworkResponseParser {
    companion object{
        fun parseStudentsList(jsonArray: JSONArray): List<Student>{
            val list = mutableListOf<Student>()
            for(i in 0 until jsonArray.length()){
                val current: JSONObject = jsonArray.getJSONObject(i)
                list.add(
                    Student(
                        current.getString("name"),
                        current.getString("registerationNumber"),
                        current.getString("phoneNumber")
                    )
                )
            }
            return list
        }
    }
}