package io.ryszardp.handler;

import io.ryszardp.bot.Bot;
import io.ryszardp.command.ParsedCommand;
import org.telegram.telegrambots.meta.api.objects.Update;

public class DefaultHandler extends AbstractHandler {
    public DefaultHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        return null;
    }
}
