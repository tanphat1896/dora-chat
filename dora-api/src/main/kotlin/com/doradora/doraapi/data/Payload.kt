package com.doradora.doraapi.data

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.Date

data class Payload(
    val sender: String,
    val avt: String,
    val content: String,
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    val date: Date? = null
)