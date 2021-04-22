package io.ryszardp.handler;

import io.ryszardp.bot.Bot;
import io.ryszardp.command.Command;
import io.ryszardp.command.ParsedCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class AdminHandler extends AbstractHandler {

    public AdminHandler(Bot bot) {
        super(bot);
    }

    /*
     * Chooses message template based on parsed command
     */
    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        Command command = parsedCommand.getCommand();

        switch (command) {
            case UNAUTHORIZED:
                bot.sendQueue.add(getMessageUnauthorized(chatId));
                break;
            case ADMIN_MESSAGE:
                bot.sendQueue.addAll(getMessageNotifyUsers(chatId, parsedCommand.getText()));
                break;
            default:
                return "No command specified";
        }
        return "";
    }

    /*
     * Notify all users
     */
    private List<SendMessage> getMessageNotifyUsers(String chatId, String text) {
        List<SendMessage> messagesToSend = new ArrayList<>();
        List<String> users = bot.getUsers();

        for (String user_id : users) {
            SendMessage sendMessage = createMessageTemplate(user_id);
            sendMessage.setText(text);
            messagesToSend.add(sendMessage);
        }

        SendMessage sendMessage = createMessageTemplate(chatId);
        sendMessage.setText(String.format("Notified %d users", users.size()));
        messagesToSend.add(sendMessage);

        return messagesToSend;
    }
}