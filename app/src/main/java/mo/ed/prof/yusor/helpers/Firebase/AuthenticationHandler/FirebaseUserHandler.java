package mo.ed.prof.yusor.helpers.Firebase.AuthenticationHandler;

/**
 * Created by Prof-Mohamed Atef on 3/19/2019.
 */

public class FirebaseUserHandler {

    String UserID;
    String API_TOKEN;
    String Gender, USerName, Email, PersonName;

    public String getAPI_TOKEN() {
        return API_TOKEN;
    }

    public void setAPI_TOKEN(String API_TOKEN) {
        this.API_TOKEN = API_TOKEN;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getUSerName() {
        return USerName;
    }

    public void setUSerName(String USerName) {
        this.USerName = USerName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
    }

    public FirebaseUserHandler(){}

    public FirebaseUserHandler(String user_id ,String api_token,String gender,String user_name,String email,String person_name){
        this.UserID=user_id;
        this.API_TOKEN=api_token;
        this.Gender=gender;
        this.USerName=user_name;
        this.Email=email;
        this.PersonName=person_name;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
