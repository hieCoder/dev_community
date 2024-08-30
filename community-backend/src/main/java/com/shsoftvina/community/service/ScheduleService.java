package com.shsoftvina.community.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;

@Component
public class ScheduleService {

    private final Logger log = LoggerFactory.getLogger(ScheduleService.class);

    private final TaskScheduler taskScheduler;
    private final Map<String, ScheduledFuture<?>> scheduledTasks = new HashMap<>();

    public ScheduleService(TaskScheduler taskScheduler){
        this.taskScheduler = taskScheduler;
    }

    public void registerSchedule(Runnable task, String cronExpression, String scheduleId) {
        Trigger trigger = new CronTrigger(cronExpression);
        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(task, trigger);
        scheduledTasks.put(scheduleId, scheduledFuture);
        log.info("Scheduled task with ID: {}", scheduleId);
    }

    public void startSchedule(String scheduleId) {
        ScheduledFuture<?> scheduledFuture = scheduledTasks.get(scheduleId);
        if (scheduledFuture != null) {
            if (!scheduledFuture.isCancelled() && !scheduledFuture.isDone()) {
                try {
                    scheduledFuture.get();
                    log.info("Started task with ID: {}", scheduleId);
                } catch (InterruptedException | ExecutionException e) {
                    log.error("Error starting task with ID: {}", scheduleId, e);
                }
            } else {
                log.warn("Task with ID {} is already cancelled or done.", scheduleId);
            }
        } else {
            log.error("No task found with ID: {}", scheduleId);
        }
    }

    @Async
    public void startSchedule(Runnable task, String cronExpression, String scheduleId) {
        this.registerSchedule(task, cronExpression, scheduleId);
        this.startSchedule(scheduleId);
    }

    public void stopSchedule(String scheduleId) {
        ScheduledFuture<?> scheduledFuture = scheduledTasks.remove(scheduleId);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            log.info("Stopped task with ID: {}", scheduleId);
        } else {
            log.error("No task found with ID: {}", scheduleId);
        }
    }

    public void restartSchedule(String scheduleId) {
        stopSchedule(scheduleId);
        startSchedule(scheduleId);
    }
}