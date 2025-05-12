package CP.Reminder.Bot.cpreminder.service;


import CP.Reminder.Bot.cpreminder.entity.UserSession;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FsmService {
    Map<Long, UserSession> sessions = new HashMap<>();

}
