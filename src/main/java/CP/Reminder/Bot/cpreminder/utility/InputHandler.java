package CP.Reminder.Bot.cpreminder.utility;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class InputHandler {
    private final int MIN_INTERVAL = 7;
    private final int MAX_INTERVAL = 210;

    public boolean checkName(String name){
        return (name!=null && !name.isEmpty());
    }

    public boolean checkUrl(String url){
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkInterval(String url){
        try {
            int interval = Integer.parseInt(url.trim());
            return (interval>=MIN_INTERVAL && interval<=MAX_INTERVAL);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public  boolean isValidMessage(Update update){
        if (!update.hasMessage()) return false;

        var message = update.getMessage();

        return message.hasText()
                && !message.hasAudio()
                && !message.hasDocument()
                && !message.hasPhoto()
                && !message.hasVideo()
                && !message.hasVoice()
                && !message.hasSticker()
                && !message.hasContact()
                && !message.hasLocation()
                && !message.hasDice()
                && !message.hasVideoNote()
                && !message.hasAnimation()
                && !hasEmoji(message.getText());
    }

    private boolean hasEmoji(String text) {
        String emojiRegex = "[\uD83C-\uDBFF\uDC00-\uDFFF]+";
        Pattern pattern = Pattern.compile(emojiRegex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

}
