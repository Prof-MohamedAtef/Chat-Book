package mo.ed.prof.yusor.Dev.Entity;

/**
 * Created by Prof-Mohamed Atef on 3/23/2019.
 */

public class FirebaseApproval {

    String sender, receiver;
    boolean senderApprove, receiverApprove;

    public boolean isSenderApprove() {
        return senderApprove;
    }

    public void setSenderApprove(boolean senderApprove) {
        this.senderApprove = senderApprove;
    }

    public boolean isReceiverApprove() {
        return receiverApprove;
    }

    public void setReceiverApprove(boolean receiverApprove) {
        this.receiverApprove = receiverApprove;
    }

    public FirebaseApproval(String sender, String receiver, boolean senderApprove, boolean receiverApprove) {
        this.sender = sender;
        this.receiver = receiver;
        this.senderApprove = senderApprove;
        this.receiverApprove = receiverApprove;
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
}
