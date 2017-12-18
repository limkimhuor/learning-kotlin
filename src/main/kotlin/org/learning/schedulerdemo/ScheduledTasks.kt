package org.learning.schedulerdemo

import mu.KLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@Component class ScheduledTasks {
    companion object: KLogging()
    var dateFormat = SimpleDateFormat("HH:mm:ss")

    @Scheduled(fixedDelay = 2000L)
    fun scheduleTaskWithFixedDelay() {
//        logger.info("Fixed Delay Task :: Execution Time - ${dateFormat.format(Date())}")
        logger.info("Current Thread : ${Thread.currentThread().name}")
        try {
            TimeUnit.SECONDS.sleep(5)
        } catch (ex: InterruptedException) {
            logger.error { "Run into an error ${ex}" }
            throw IllegalStateException(ex);
        }
    }

    @Scheduled(cron = "* * * * * ?")
    fun scheduleTaskWithCronExpression() {
        //logger.info { "Cron Task : Execution Time - ${dateFormat.format(Date())}" }
        logger.info("Current Thread : ${Thread.currentThread().name}")
    }
}