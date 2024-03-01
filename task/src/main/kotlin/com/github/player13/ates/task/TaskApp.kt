package com.github.player13.ates.task

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskApp

fun main(args: Array<String>) {
	runApplication<TaskApp>(*args)
}
