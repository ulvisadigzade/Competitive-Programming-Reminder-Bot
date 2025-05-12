package CP.Reminder.Bot.cpreminder.repository;


import CP.Reminder.Bot.cpreminder.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MessageRepository {
    private final JdbcTemplate jdbcTemplate;
    private static  final String TABLE_NAME = "messages";


    public int save(Message message){
        String sql = "INSERT INTO " + TABLE_NAME + " (name,url,interval_minutes) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql,message.getName(),message.getUrl(),message.getInterval_minutes());
    }
}
