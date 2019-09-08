package com.doradora.doraapi.controller.ws

import com.doradora.doraapi.data.Greeting
import com.doradora.doraapi.data.HelloMessage
import com.doradora.doraapi.data.Payload
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.util.HtmlUtils



@CrossOrigin
@Controller
class MessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/test")
    @Throws(Exception::class)
    fun greeting(message: HelloMessage): Greeting {
        return Greeting("Hello, " + HtmlUtils.htmlEscape(message.name.toString()) + "!")
    }
}