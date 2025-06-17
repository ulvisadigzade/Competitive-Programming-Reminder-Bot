package CP.Reminder.Bot.cpreminder.service;


import CP.Reminder.Bot.cpreminder.bot.MessageSender;
import CP.Reminder.Bot.cpreminder.model.Message;
import CP.Reminder.Bot.cpreminder.model.UserSession;
import CP.Reminder.Bot.cpreminder.model.UserState;
import CP.Reminder.Bot.cpreminder.repository.MessageRepository;
import CP.Reminder.Bot.cpreminder.utility.DateTimeUtils;
import CP.Reminder.Bot.cpreminder.utility.InputHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class UserStateHandlerService {
    private final InputHandler inputHandler;
    private final FsmService fsmService;
    private final MessageSender messageSender;
    private final MessageRepository messageRepository;


    public void handleAskName(String textMessage, Long userId, Update update){
        if(inputHandler.checkName(textMessage)) {
            fsmService.sessions.get(userId).setName(textMessage);

            messageSender.sendMessage(update, "ask_url");
            fsmService.sessions.get(userId).setUserState(UserState.ASK_URL);
        }
        else{
            messageSender.sendMessage(update,"invalid_name");
        }
    }

    public void handleAskUrl(String textMessage, Long userId, Update update){
        if(inputHandler.checkUrl(textMessage)){
            fsmService.sessions.get(userId).setUrl(textMessage);

            messageSender.sendMessage(update,"ask_interval");
            fsmService.sessions.get(userId).setUserState(UserState.ASK_INTERVAL);
        }
        else{
            messageSender.sendMessage(update,"invalid_url");
        }
    }

    public void handleAskInterval(String textMessage, Long chatId, Update update){
        if(inputHandler.checkInterval(textMessage)){
            fsmService.sessions.get(chatId).setInterval(Integer.valueOf(textMessage));
            fsmService.sessions.get(chatId).setUserState(UserState.START);
            UserSession userSession = fsmService.sessions.get(chatId);
            Message message = Message.builder()
                    .userId(chatId)
                    .name(userSession.getName())
                    .url(userSession.getUrl())
                    .intervalDays(userSession.getInterval())
                    .sentAt(DateTimeUtils.toUTC(userSession.getDate()))
                    .lastNotified(DateTimeUtils.toUTC(userSession.getDate()))
                    .build();
            messageSender.sendMessage(update,"solved");
            messageRepository.save(message);
        }
        else{
            messageSender.sendMessage(update,"invalid_interval");
        }
    }

}
