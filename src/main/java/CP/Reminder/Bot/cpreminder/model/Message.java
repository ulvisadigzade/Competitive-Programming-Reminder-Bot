package CP.Reminder.Bot.cpreminder.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    private long id;
    private String name;
    private String url;
    private int intervalDays;
    private ZonedDateTime sentAt;
    private ZonedDateTime lastNotified;
}