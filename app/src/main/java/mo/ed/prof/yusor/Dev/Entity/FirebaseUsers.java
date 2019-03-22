package mo.ed.prof.yusor.Dev.Entity;

/**
 * Created by Prof-Mohamed Atef on 3/21/2019.
 */

public class FirebaseUsers {

    private String imageUrl, userName, ID;

    public FirebaseUsers(){}

    public FirebaseUsers(String imageUrl, String userName, String ID) {
        this.imageUrl = imageUrl;
        this.userName = userName;
        this.ID = ID;
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
