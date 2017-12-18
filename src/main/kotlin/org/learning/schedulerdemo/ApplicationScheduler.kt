package org.learning.schedulerdemo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
class ApplicationScheduler

fun main(args: Array<String>) {
    SpringApplication.run(ApplicationScheduler::class.java, *args)
}

//data class Greeting(val id: Long, val content: String)
//
//@RestController
//class GreetingController {
//
//    val counter = AtomicLong()
//
//    @GetMapping("/greeting")
//    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
//            Greeting(counter.incrementAndGet(), "Hello, $name")
//
//}

