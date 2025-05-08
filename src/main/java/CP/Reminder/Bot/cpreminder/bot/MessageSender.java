package CP.Reminder.Bot.cpreminder.bot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageSender {
    @Value("${BOT_TOKEN}")
    private String botToken;


    Map<String,String> messages = new HashMap<>();
    private TelegramClient telegramClient;

    @PostConstruct
    public void init(){
        this.telegramClient = new OkHttpTelegramClient(botToken);
        messages.put(
                "start",
                "Welcome to the Competitive Programming Bot!\n"
                        + "I’m here to help you stay on top of your practice. Simply submit a problem and set a revision interval, and I’ll send you reminders to revise it!\n\n"
                        + "Let’s get started!\n"
                        + "Use the /help command for more instructions."
        );

        messages.put(
                "solved",
                "Problem was successfully saved."
        );

        messages.put(
                "invalid_data",
                "Invalid input, write text please."
        );

        messages.put(
                "invalid_command",
                "https://youtube.com"
                //"Command not found."
        );

        // TODO
        // help, random_problem

    }

    public void sendMessage(Update update,String option){

        SendMessage message = new SendMessage(
                String.valueOf(update.getMessage().getChatId()),
                messages.get(option)
        );

        try{
            telegramClient.execute(message);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

}
