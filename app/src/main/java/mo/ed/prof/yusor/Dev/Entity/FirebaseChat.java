package mo.ed.prof.yusor.Dev.Entity;

/**
 * Created by Prof-Mohamed Atef on 3/21/2019.
 */

public class FirebaseChat {

    public FirebaseChat(){}

    private String sender, receiver, message;
    private boolean isseen;
//    private boolean receiver_approve;
    private boolean sender_approve;

    public boolean getSender_approve() {
        return sender_approve;
    }

    public void setSender_approve(boolean sender_approve) {
        this.sender_approve = sender_approve;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }

    public FirebaseChat(String sender, String receiver, String message, boolean is_seen, boolean sender_approve) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isseen=is_seen;
        this.sender_approve=sender_approve;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
