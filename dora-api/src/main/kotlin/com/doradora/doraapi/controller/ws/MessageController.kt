package com.doradora.doraapi.controller.ws

import com.doradora.doraapi.data.Payload
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
@Controller
class MessageController {

    @MessageMapping("/chat")
    @SendTo("/topic/public")
    @Throws(Exception::class)
    fun sendMessage(message: Payload) = message

    @MessageMapping("/join")
    @SendTo("/topic/public")
    @Throws(Exception::class)
    fun join(message: Payload): Payload {
        return message
    }
}