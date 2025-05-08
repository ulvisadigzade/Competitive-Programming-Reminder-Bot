package CP.Reminder.Bot.cpreminder.bot;

import CP.Reminder.Bot.cpreminder.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
@RequiredArgsConstructor
public class CPbot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    @Value("${BOT_TOKEN}")
    private String botToken;

    private final MessageService messageService;

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        messageService.handleMessage(update);
    }
}
