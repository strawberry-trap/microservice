package message.event.member;

public class MemberEvent {

    private final String message;

    public MemberEvent (String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
