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
                //messageSender.sendMessage(messageFormat());
            }
        }
    }

    private boolean shouldNotify(Message message, ZonedDateTime now) {
        if (message.getLastNotified() == null) return true;
        LocalDate lastDate = message.getLastNotified().toLocalDate();
        LocalDate today = now.toLocalDate();
        return !today.isBefore(lastDate.plusDays(message.getIntervalDays()));
    }

    private String messageFormat(){
        return "";
    }
}