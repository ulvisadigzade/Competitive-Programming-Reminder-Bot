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
                "Command not found."
        );

        messages.put(
                "help",
                "\uD83E\uDD16 Bot Help Menu\n" +
                        "\n" +
                        "Welcome! Here are the available commands you can use:\n" +
                        "\n" +
                        "/start – Initialize the bot and start interacting.\n" +
                        "\n" +
                        "/solved – Log a problem you've solved.\n" +
                        "After sending the command, please enter the following on separate lines:\n" +
                        "\n" +
                        "Problem Name  \n" +
                        "Problem URL  \n" +
                        "Interval in days (when you'd like to review it again)\n" +
                        "\n" +
                        "/random_problem – Get a random problem from your solved list for practice or review.\n" +
                        "\n" +
                        "If you have any questions or issues, feel free to reach out!"
        );

        // TODO
        //random_problem

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
