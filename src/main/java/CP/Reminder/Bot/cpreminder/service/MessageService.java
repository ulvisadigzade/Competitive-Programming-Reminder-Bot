package CP.Reminder.Bot.cpreminder.service;


import CP.Reminder.Bot.cpreminder.bot.MessageSender;
import CP.Reminder.Bot.cpreminder.entity.Message;
import CP.Reminder.Bot.cpreminder.entity.UserSession;
import CP.Reminder.Bot.cpreminder.entity.UserState;
import CP.Reminder.Bot.cpreminder.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageSender messageSender;
    private final MessageRepository messageRepository;
    private final FsmService fsmService;

    // TODO
    //Handle emojis


    public void handleMessage(Update update){
        if(!update.hasMessage())return;
        if(!update.getMessage().hasText()){
            messageSender.sendMessage(update,"invalid_data");
        }
        String textMessage = update.getMessage().getText();
        Long userId = update.getMessage().getFrom().getId();

        if(!fsmService.sessions.containsKey(userId)){
            fsmService.sessions.put(userId,new UserSession());
        }


        if(textMessage.equals("/start")){
            fsmService.sessions.remove(userId);

            messageSender.sendMessage(update,"start");
        }
        else if(textMessage.equals("/solved")){
            fsmService.sessions.remove(userId);

            messageSender.sendMessage(update,"ask_name");
            fsmService.sessions.put(userId,new UserSession(UserState.ASK_NAME));
        }
        else if(textMessage.equals("/random_problem")){
            fsmService.sessions.remove(userId);

            //messageSender.sendMessage(update,"random_problem");
        }
        else if(textMessage.equals("/help")){
            fsmService.sessions.remove(userId);

            messageSender.sendMessage(update,"help");
        }
        else{
            //use enum for it maybe
            switch (fsmService.sessions.get(userId).getUserState()){
                case START:
                    messageSender.sendMessage(update,"invalid_command");
                    break;

                case ASK_NAME:
                    // handle start
                    fsmService.sessions.get(userId).setName(textMessage);

                    messageSender.sendMessage(update,"ask_url");
                    fsmService.sessions.get(userId).setUserState(UserState.ASK_URL);
                    break;

                case ASK_URL:
                    // handle url;
                    fsmService.sessions.get(userId).setUrl(textMessage);

                    messageSender.sendMessage(update,"ask_interval");
                    fsmService.sessions.get(userId).setUserState(UserState.ASK_INTERVAL);
                    break;

                case ASK_INTERVAL:
                    // handle interval
                    fsmService.sessions.get(userId).setInterval(Integer.valueOf(textMessage));
                    fsmService.sessions.get(userId).setUserState(UserState.START);
                    UserSession userSession = fsmService.sessions.get(userId);
                    Message message = Message.builder()
                            .name(userSession.getName())
                            .url(userSession.getUrl())
                            .interval_minutes(userSession.getInterval())
                            .build();

                    messageSender.sendMessage(update,"solved");
                    messageRepository.save(message);
                    break;
            }
        }
    }
}
