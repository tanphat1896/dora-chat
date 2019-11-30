package com.doradora.doraapi

import com.doradora.doraapi.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@SpringBootApplication
class DoraApiApplication : ApplicationListener<ApplicationEvent>, CommandLineRunner {
	@Autowired lateinit var userRepository: UserRepository
	override fun onApplicationEvent(event: ApplicationEvent) {
		if (event is ContextRefreshedEvent) {
			val ctx = event.applicationContext
			ctx.getBean(RequestMappingHandlerMapping::class.java).handlerMethods.forEach { t, u ->
				println(t)
			}
		}
	}

	override fun run(vararg args: String?) {
		println(userRepository.findAll())
	}
}

fun main(args: Array<String>) {
	runApplication<DoraApiApplication>(*args)
}
