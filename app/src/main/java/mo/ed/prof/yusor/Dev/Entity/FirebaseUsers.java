package mo.ed.prof.yusor.Dev.Entity;

/**
 * Created by Prof-Mohamed Atef on 3/21/2019.
 */

public class FirebaseUsers {

    private String imageUrl, userName, ID, Status, approved;

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public FirebaseUsers(){}

    public FirebaseUsers(String imageUrl, String userName, String ID, String status, String approved_) {
        this.imageUrl = imageUrl;
        this.userName = userName;
        this.ID = ID;
        this.Status=status;
        this.approved=approved_;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
