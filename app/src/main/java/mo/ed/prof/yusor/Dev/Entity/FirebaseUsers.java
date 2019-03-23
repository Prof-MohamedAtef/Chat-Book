package mo.ed.prof.yusor.Dev.Entity;

/**
 * Created by Prof-Mohamed Atef on 3/21/2019.
 */

public class FirebaseUsers {

    private String imageUrl, userName, ID, Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public FirebaseUsers(){}

    public FirebaseUsers(String imageUrl, String userName, String ID, String status) {
        this.imageUrl = imageUrl;
        this.userName = userName;
        this.ID = ID;
        this.Status=status;
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
