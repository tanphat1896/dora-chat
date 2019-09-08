package com.doradora.doraapi.data

import com.fasterxml.jackson.annotation.JsonFormat
import java.sql.Timestamp

data class Payload(
    val sender: String,
    val message: String,
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    val date: Timestamp
)