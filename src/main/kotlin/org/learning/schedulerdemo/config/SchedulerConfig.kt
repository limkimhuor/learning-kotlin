package org.learning.schedulerdemo.config

import mu.KLogging
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar

@Configuration abstract class SchedulerConfig : SchedulingConfigurer {
    val POOL_SIZE: Int = 10

    override fun configureTasks(scheduledTaskRegistrar: ScheduledTaskRegistrar) {
        var threadPoolTaskScheduler = ThreadPoolTaskScheduler()

        threadPoolTaskScheduler.poolSize = POOL_SIZE

        threadPoolTaskScheduler.setThreadNamePrefix("my-scheduled-task-pool-")
        threadPoolTaskScheduler.initialize()

        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler)

    }
}