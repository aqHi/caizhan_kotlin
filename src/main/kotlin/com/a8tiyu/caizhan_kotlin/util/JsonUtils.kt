package com.a8tiyu.caizhan_kotlin.util

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import java.text.SimpleDateFormat


object JsonUtils {


    private val objectMapper = ObjectMapper()

    fun <T> fromJson(jsonString: String, type: Class<T>) = objectMapper.readValue(jsonString, type)

    fun toJson(any: Any?) = objectMapper.writeValueAsString(any)

    fun jackSonParse(jsonString: String) = objectMapper.readTree(jsonString)

    init {

        objectMapper.dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        objectMapper.enable(JsonParser.Feature.ALLOW_COMMENTS)
    }
}

