package com.doradora.doraapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@SpringBootApplication
class DoraApiApplication : ApplicationListener<ApplicationEvent> {
	override fun onApplicationEvent(event: ApplicationEvent) {
		if (event is ContextRefreshedEvent) {
			val ctx = event.applicationContext
			ctx.getBean(RequestMappingHandlerMapping::class.java).handlerMethods.forEach { t, u ->
				println(t)
			}
		}
	}


}

fun main(args: Array<String>) {
	runApplication<DoraApiApplication>(*args)
}
