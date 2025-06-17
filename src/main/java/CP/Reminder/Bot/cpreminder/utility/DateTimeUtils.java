package CP.Reminder.Bot.cpreminder.utility;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateTimeUtils {
    public static ZonedDateTime toUTC(long telegramDate){
        return Instant.ofEpochSecond(telegramDate).atZone(ZoneOffset.UTC);
    }
}
