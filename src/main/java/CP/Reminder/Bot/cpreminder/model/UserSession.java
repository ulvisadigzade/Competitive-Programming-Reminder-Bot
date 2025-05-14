package CP.Reminder.Bot.cpreminder.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {
    private UserState userState=UserState.START;
    private String name;
    private String url;
    private int interval;

    public UserSession(UserState userState){
        this.userState=userState;
        this.name=null;
        this.url=url;
        this.interval=0;
    }
}
