package CP.Reminder.Bot.cpreminder.utility;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateTimeUtils {
    public static ZonedDateTime toUTC(long telegramDate){
        if (telegramDate > 1000000000000L) {
            return Instant.ofEpochMilli(telegramDate).atZone(ZoneOffset.UTC);
        } else {
            return Instant.ofEpochSecond(telegramDate).atZone(ZoneOffset.UTC);
        }
    }
}
