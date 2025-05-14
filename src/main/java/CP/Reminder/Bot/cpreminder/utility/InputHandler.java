package CP.Reminder.Bot.cpreminder.utility;


import org.springframework.stereotype.Component;

import java.net.URL;

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
}
