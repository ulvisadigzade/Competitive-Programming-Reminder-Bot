package CP.Reminder.Bot.cpreminder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CpreminderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CpreminderApplication.class, args);
	}

}
