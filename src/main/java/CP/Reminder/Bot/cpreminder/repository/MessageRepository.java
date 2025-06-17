package CP.Reminder.Bot.cpreminder.repository;


import CP.Reminder.Bot.cpreminder.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;

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

    public List<Message> findAll() {
        String sql = "SELECT * FROM messages";

        return jdbcTemplate.query(sql, (rs, rowNum) -> Message.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .url(rs.getString("url"))
                .intervalDays(rs.getInt("interval_days"))
                .sentAt(rs.getObject("sent_at", ZonedDateTime.class))
                .lastNotified(rs.getObject("last_notified", ZonedDateTime.class))
                .build()
        );
    }

    private Timestamp toTimestamp(ZonedDateTime zonedDateTime) {
        return zonedDateTime == null ? null : Timestamp.from(zonedDateTime.toInstant());
    }

}
