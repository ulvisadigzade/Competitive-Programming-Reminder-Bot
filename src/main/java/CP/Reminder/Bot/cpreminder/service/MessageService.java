package CP.Reminder.Bot.cpreminder.service;


import CP.Reminder.Bot.cpreminder.bot.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageSender messageSender;

    // TODO
    //Handle emojis


    public void handleMessage(Update update){
        if(!update.hasMessage())return;
        if(!update.getMessage().hasText()){
            messageSender.sendMessage(update,"invalid_data");
        }
        String textMessage = update.getMessage().getText();

        if(textMessage.equals("/start")){
            messageSender.sendMessage(update,"start");
        }
        else if(textMessage.equals("/solved")){

            messageSender.sendMessage(update,"solved");
        }
        else if(textMessage.equals("/random_problem")){

            messageSender.sendMessage(update,"random_problem");
        }
        else if(textMessage.equals("/help")){
            messageSender.sendMessage(update,"help");
        }
        else{
            messageSender.sendMessage(update,"invalid_command");
        }
    }
}
