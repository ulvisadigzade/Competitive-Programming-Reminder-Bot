package CP.Reminder.Bot.cpreminder.service;


import CP.Reminder.Bot.cpreminder.bot.MessageSender;
import CP.Reminder.Bot.cpreminder.model.UserSession;
import CP.Reminder.Bot.cpreminder.utility.InputHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageSender messageSender;
    private final FsmService fsmService;
    private final CommandHandlerService commandHandlerService;
    private final UserStateHandlerService userStateHandler;
    private final InputHandler inputHandler;


    public void handleMessage(Update update){
        if(!inputHandler.isValidMessage(update)){
            messageSender.sendMessage(update,"invalid_message");
            return;
        }

        String textMessage = update.getMessage().getText();
        Long userId = update.getMessage().getFrom().getId();

        Map<Long,UserSession> sessions = fsmService.sessions;

        if(!sessions.containsKey(userId)){
            sessions.put(userId,new UserSession());
        }


        if(textMessage.equals("/start")){
            commandHandlerService.handleStart(update);
        }
        else if(textMessage.equals("/solved")){
            commandHandlerService.handleSolved(update);
        }
        else if(textMessage.equals("/help")){
            commandHandlerService.handleHelp(update);
        }
        else{
            switch (sessions.get(userId).getUserState()){
                case START -> messageSender.sendMessage(update,"invalid_command");
                case ASK_NAME -> userStateHandler.handleAskName(textMessage,userId,update);
                case ASK_URL -> userStateHandler.handleAskUrl(textMessage,userId,update);
                case ASK_INTERVAL -> userStateHandler.handleAskInterval(textMessage,userId,update);
            }
        }
    }
}
