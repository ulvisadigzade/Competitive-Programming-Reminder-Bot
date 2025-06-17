package CP.Reminder.Bot.cpreminder.repository;


import CP.Reminder.Bot.cpreminder.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Repository
@RequiredArgsConstructor
public class MessageRepository {
    private final JdbcTemplate jdbcTemplate;
    private static  final String TABLE_NAME = "messages";


    public int save(Message message){
        String sql = "INSERT INTO " + TABLE_NAME + " (name,url,interval_days,sent_at,last_notified) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                message.getName(),
                message.getUrl(),
                message.getIntervalDays(),
                toTimestamp(message.getSentAt()),
                toTimestamp(message.getLastNotified())
        );
    }

    private Timestamp toTimestamp(ZonedDateTime zonedDateTime) {
        return zonedDateTime == null ? null : Timestamp.from(zonedDateTime.toInstant());
    }

}
