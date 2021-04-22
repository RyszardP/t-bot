package io.ryszardp.service;

import io.ryszardp.bot.Bot;
import io.ryszardp.command.Command;
import io.ryszardp.command.ParsedCommand;
import io.ryszardp.handler.SystemHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public class TaskScheduler implements Runnable {
    private static final int UPDATE_DELAY = 60_000;
    private final Logger log = LoggerFactory.getLogger(TaskScheduler.class);
    private final Bot bot;

    public TaskScheduler(Bot bot) {
        this.bot = bot;
    }

    /*
     * Scheduler's main loop
     */
    @Override
    public void run() {
        log.info(String.format("[STARTED] TaskScheduler.  Bot class: %s", bot));
        while (true) {
            processScheduledTasks();

            try {
                Thread.sleep(UPDATE_DELAY);
            } catch (InterruptedException e) {
                log.error("Catch interrupt. Exit", e);
                return;
            }
        }
    }

    /*
     * Used to send scheduled messages
     */
    private void processScheduledTasks() {
        final LocalDateTime ldt = LocalDateTime.now().plusHours(3);
        if (ldt.getDayOfWeek() != DayOfWeek.SUNDAY && ldt.getDayOfWeek() != DayOfWeek.SATURDAY) {
            log.debug("Checking for scheduled messages");

            List<String> scheduledUsers = bot.isAnyScheduled(ldt);
            if (!scheduledUsers.isEmpty()) {
                for (String chatId : scheduledUsers) {
                    log.debug("Scheduled message for {} sent at {}:{}", chatId, ldt.getHour(), ldt.getMinute());
                    ParsedCommand command = new ParsedCommand();
                    command.setCommand(Command.GET);
                    new SystemHandler(bot).operate(chatId, command, null);
                }
            }
        }

        // Update Customer info daily
        if (ldt.getHour() == 11 && ldt.getMinute() == 30) {
            bot.updateCustomers();
        }
    }
}
