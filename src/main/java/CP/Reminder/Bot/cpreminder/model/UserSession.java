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
    private long date;

    public UserSession(UserState userState,long date){
        this.userState=userState;
        this.name=null;
        this.url=null;
        this.interval=0;
        this.date=date;
    }

    public UserSession(long date){
        this.date=date;
    }
}
