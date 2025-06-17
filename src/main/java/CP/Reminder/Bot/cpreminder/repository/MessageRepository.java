package CP.Reminder.Bot.cpreminder.repository;

import CP.Reminder.Bot.cpreminder.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String TABLE_NAME = "messages";
    private static final ZoneId DEFAULT_ZONE = ZoneId.of("UTC");

    public int save(Message message) {
        String sql = "INSERT INTO " + TABLE_NAME + " (user_id, name, url, interval_days, sent_at, last_notified) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                message.getUserId(),
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
                .userId(rs.getLong("user_id"))
                .name(rs.getString("name"))
                .url(rs.getString("url"))
                .intervalDays(rs.getInt("interval_days"))
                .sentAt(toZonedDateTime(rs.getTimestamp("sent_at")))
                .lastNotified(toZonedDateTime(rs.getTimestamp("last_notified")))
                .build()
        );
    }

    public void updateLastNotified(long id, ZonedDateTime time) {
        String sql = "UPDATE messages SET last_notified = ? WHERE id = ?";
        jdbcTemplate.update(sql, toTimestamp(time), id);
    }

    private Timestamp toTimestamp(ZonedDateTime zonedDateTime) {
        return zonedDateTime == null ? null : Timestamp.from(zonedDateTime.toInstant());
    }

    private ZonedDateTime toZonedDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toInstant().atZone(DEFAULT_ZONE);
    }
}
