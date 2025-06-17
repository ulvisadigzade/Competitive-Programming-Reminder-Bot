package CP.Reminder.Bot.cpreminder.service;


import CP.Reminder.Bot.cpreminder.bot.MessageSender;
import CP.Reminder.Bot.cpreminder.model.Message;
import CP.Reminder.Bot.cpreminder.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService{
    private final MessageRepository messageRepository;
    private final MessageSender messageSender;


    @Scheduled(cron = "0 * * * * *")
    public void checkAndSendReminders(){
        List<Message> messages = messageRepository.findAll();
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        for(Message message : messages){
            if(shouldNotify(message,now)){
                System.out.println(message.getUserId());
                messageSender.sendMessage(messageFormat(message),message.getUserId());
                messageRepository.updateLastNotified(message.getId(), now);
            }
        }
    }

    private boolean shouldNotify(Message message, ZonedDateTime now) {
        if (message.getLastNotified() == null) return true;
        LocalDate lastDate = message.getLastNotified().toLocalDate();
        LocalDate today = now.toLocalDate();
        return !today.isBefore(lastDate.plusDays(message.getIntervalDays()));
    }

    private String messageFormat(Message message){
        return "Hey, don't forget to solve " + message.getName() + "!"
                + "\n" + "Problem link: " + message.getUrl();
    }
}