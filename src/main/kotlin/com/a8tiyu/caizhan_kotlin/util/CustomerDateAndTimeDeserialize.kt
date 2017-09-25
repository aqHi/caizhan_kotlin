package com.a8tiyu.caizhan_kotlin.util

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CustomerDateAndTimeDeserialize : JsonDeserializer<Date>() {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Date {
        val str = p!!.getText().trim()
        try {
            return dateFormat.parse(str)
        } catch (e: ParseException) {

        }
        return ctxt!!.parseDate(str)
    }

}