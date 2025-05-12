package CP.Reminder.Bot.cpreminder.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    private long id;
    private String name;
    private String url;
    private int interval_minutes;//days
}
