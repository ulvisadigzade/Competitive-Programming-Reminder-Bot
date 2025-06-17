package CP.Reminder.Bot.cpreminder.service;


import CP.Reminder.Bot.cpreminder.bot.MessageSender;
import CP.Reminder.Bot.cpreminder.model.UserSession;
import CP.Reminder.Bot.cpreminder.model.UserState;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommandHandlerService {
    private final MessageSender messageSender;
    private final FsmService fsmService;

    Map<Long,UserSession> sessions;
    @PostConstruct
    void init(){
        sessions = fsmService.sessions;
    }

    public void handleStart(Update update){
        Long userId = update.getMessage().getFrom().getId();

        sessions.remove(userId);
        messageSender.sendMessage(update,"start");
    }

    public void handleSolved(Update update){
        Long chatId = update.getMessage().getChatId();
        long date = update.getMessage().getDate();

        sessions.put(chatId,new UserSession(UserState.ASK_NAME,date,chatId));
        messageSender.sendMessage(update,"ask_name");
    }

    public void handleHelp(Update update){
        Long userId = update.getMessage().getFrom().getId();

        sessions.remove(userId);
        messageSender.sendMessage(update,"help");
    }
}
