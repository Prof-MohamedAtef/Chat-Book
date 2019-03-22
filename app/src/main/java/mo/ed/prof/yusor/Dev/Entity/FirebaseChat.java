package mo.ed.prof.yusor.Dev.Entity;

/**
 * Created by Prof-Mohamed Atef on 3/21/2019.
 */

public class FirebaseChat {

    public FirebaseChat(){}

    private String sender, receiver, message;

    public FirebaseChat(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
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
