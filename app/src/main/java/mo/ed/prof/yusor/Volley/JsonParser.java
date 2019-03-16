package mo.ed.prof.yusor.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

import static mo.ed.prof.yusor.helpers.Designsers.ProgressGenerator.KEY_Email;
import static mo.ed.prof.yusor.helpers.Designsers.ProgressGenerator.KEY_Gender;
import static mo.ed.prof.yusor.helpers.Designsers.ProgressGenerator.KEY_PersonName;
import static mo.ed.prof.yusor.helpers.Designsers.ProgressGenerator.KEY_UserName;

/**
 * Created by Prof-Mohamed Atef on 3/13/2019.
 */

public class JsonParser {


    private static String API_Token_STR;
    private static String Gender_STR;
    private static String UserName_STR;
    private static String Email_STR;
    private static String PersonName_STR;
    private static String Department_STR;
    private static String API_TOKEN_KEY="api_token";
    private static String USER_Obj_KEY="user";
    private static String Department_KEY="department";
    private static String Department_Name_KEY="name";
    private static StudentsEntity studentsEntity;
    private static String MSG_STR;
    private static String DONE_KEY="Done";
    private static String Email_Already_Taken ="The email has already been taken.";
    private static String UserName_Already_Taken ="The user name has already been taken.";
    public static String Error_="error";
    private static String Invalid_Format="The email format is invalid.";
    private JSONArray BookEntityJsonAray;
    private JSONObject oneBookJsonObjectData;
    private String Book_Title_KEY ="title";
    private String Book_Title_STR;
    private String Description_KEY="desc";
    private String Book_Description_STR;
    private String PublishYear_KEY="publish_year";
    private String PublishYear_STR;
    private String AuthorID_KEY="author_id";
    private String AuthorID_STR;
    private String PHOTO_KEY="photo";
    private String PHOTO_STR;
    private String ISBN_Num_KEY="ISBN_num";
    private String ISBN_STR;
    private String AUTHOR_Obj_KEY="author";
    private String KEY_NAME="name";
    private String AuthorName_STR;

    public JsonParser( ){

    }


    public static ArrayList<StudentsEntity> signUpJsonParse(String UsersDesires)
            throws JSONException {
        studentsEntity= new StudentsEntity();
        UsersDesiresJson = new JSONObject(UsersDesires);
        MSG_STR = UsersDesiresJson.getString("msg");
        if (MSG_STR.equals(DONE_KEY)){
            try {
                StudentEntityJsonAray = UsersDesiresJson.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.clear();
            for (int i = 0; i < StudentEntityJsonAray.length(); i++) {
                // Get the JSON object representing a movie per each loop
                oneJsonObjectData = StudentEntityJsonAray.getJSONObject(i);
                API_Token_STR= oneJsonObjectData .getString(API_TOKEN_KEY);
                JSONArray userJsonArray = null;
                try {
                    userJsonArray=oneJsonObjectData.getJSONArray(USER_Obj_KEY);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int x=0; x<userJsonArray.length(); x++){
                    JSONObject oneUserJsonObject=userJsonArray.getJSONObject(x);
                    PersonName_STR= oneUserJsonObject.getString(KEY_PersonName);
                    Email_STR= oneUserJsonObject.getString(KEY_Email);
                    UserName_STR= oneUserJsonObject.getString(KEY_UserName);
                    Gender_STR= oneUserJsonObject.getString(KEY_Gender);
//                JSONArray departmentJsonArray=null;
//                try {
//                    departmentJsonArray=oneUserJsonObject.getJSONArray(Department_KEY);
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                for (int j=0; j<departmentJsonArray.length(); j++){
//                    JSONObject oneDepartmentJsonObject=departmentJsonArray.getJSONObject(j);
//                    Department_STR= oneDepartmentJsonObject.getString(Department_Name_KEY);
//
//                }
                    studentsEntity= new StudentsEntity(API_Token_STR, PersonName_STR, Email_STR, UserName_STR,Gender_STR,Department_STR);
                    list.add(studentsEntity);
                }
            }
        }else if (MSG_STR.equals( Email_Already_Taken)){
            studentsEntity.setException(Email_Already_Taken);
            list.add(studentsEntity);
        }else if (MSG_STR.equals(UserName_Already_Taken)){
            studentsEntity.setException(UserName_Already_Taken);
            list.add(studentsEntity);
        }else if (MSG_STR.equals( Error_)){
            studentsEntity.setException(Error_);
            list.add(studentsEntity);
        }
        return list;
    }



    public static ArrayList<StudentsEntity> signInJsonParse(String UsersDesires)
            throws JSONException {
        studentsEntity= new StudentsEntity();
        UsersDesiresJson = new JSONObject(UsersDesires);
        MSG_STR = UsersDesiresJson.getString("msg");
        if (MSG_STR.equals(DONE_KEY)){
            try {
                StudentEntityJsonAray = UsersDesiresJson.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.clear();
            for (int i = 0; i < StudentEntityJsonAray.length(); i++) {
                // Get the JSON object representing a movie per each loop
                oneJsonObjectData = StudentEntityJsonAray.getJSONObject(i);
                API_Token_STR= oneJsonObjectData .getString(API_TOKEN_KEY);
                JSONArray userJsonArray = null;
                try {
                    userJsonArray=oneJsonObjectData.getJSONArray(USER_Obj_KEY);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int x=0; x<userJsonArray.length(); x++){
                    JSONObject oneUserJsonObject=userJsonArray.getJSONObject(x);
                    PersonName_STR= oneUserJsonObject.getString(KEY_PersonName);
                    Email_STR= oneUserJsonObject.getString(KEY_Email);
                    UserName_STR= oneUserJsonObject.getString(KEY_UserName);
                    Gender_STR= oneUserJsonObject.getString(KEY_Gender);
//                JSONArray departmentJsonArray=null;
//                try {
//                    departmentJsonArray=oneUserJsonObject.getJSONArray(Department_KEY);
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                for (int j=0; j<departmentJsonArray.length(); j++){
//                    JSONObject oneDepartmentJsonObject=departmentJsonArray.getJSONObject(j);
//                    Department_STR= oneDepartmentJsonObject.getString(Department_Name_KEY);
//
//                }
                    studentsEntity= new StudentsEntity(API_Token_STR, PersonName_STR, Email_STR, UserName_STR,Gender_STR,Department_STR);
                    list.add(studentsEntity);
                }
            }
        }else if (MSG_STR.equals( Email_Already_Taken)){
            studentsEntity.setException(Email_Already_Taken);
            list.add(studentsEntity);
        }else if (MSG_STR.equals(UserName_Already_Taken)){
            studentsEntity.setException(UserName_Already_Taken);
            list.add(studentsEntity);
        }else if (MSG_STR.equals( Error_)){
            studentsEntity.setException(Error_);
            list.add(studentsEntity);
        }else if (MSG_STR.equals(Invalid_Format)){
            studentsEntity.setException(Invalid_Format);
            list.add(studentsEntity);
        }
        return list;
    }

    public static JSONObject UsersDesiresJson;
    public static JSONArray StudentEntityJsonAray;
    public static JSONObject oneJsonObjectData;
    static ArrayList<StudentsEntity> list = new ArrayList<StudentsEntity>();

    public static ArrayList<StudentsEntity> getLoggerData(String response) {
        return null;
    }

    public ArrayList<StudentsEntity> parseBooksJsontoBeAdded(String response) throws JSONException {
        studentsEntity= new StudentsEntity();
        UsersDesiresJson = new JSONObject(response);
        MSG_STR = UsersDesiresJson.getString("msg");
        if (MSG_STR.equals(DONE_KEY)){
            try {
                BookEntityJsonAray = UsersDesiresJson.getJSONArray("data");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.clear();
            for (int i = 0; i < BookEntityJsonAray.length(); i++) {
                // Get the JSON object representing a movie per each loop
                oneBookJsonObjectData = BookEntityJsonAray.getJSONObject(i);
                Book_Title_STR = oneBookJsonObjectData.getString(Book_Title_KEY);
                Book_Description_STR = oneBookJsonObjectData.getString(Description_KEY);
                PublishYear_STR = oneBookJsonObjectData.getString(PublishYear_KEY);
                PHOTO_STR = oneBookJsonObjectData.getString(PHOTO_KEY);
                ISBN_STR = oneBookJsonObjectData.getString(ISBN_Num_KEY);
                JSONArray authorJsonArray = null;
                try {
                    authorJsonArray = oneBookJsonObjectData.getJSONArray(AUTHOR_Obj_KEY);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int x = 0; x < authorJsonArray.length(); x++) {
                    JSONObject oneAuthorJsonObject = authorJsonArray.getJSONObject(x);
                    AuthorName_STR = oneAuthorJsonObject.getString(KEY_NAME);
                    JSONArray departmentJsonArray = null;
                    try {
                        departmentJsonArray = oneBookJsonObjectData.getJSONArray(Department_KEY);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int j = 0; j < departmentJsonArray.length(); j++) {
                        JSONObject oneDepartmentJsonObject = departmentJsonArray.getJSONObject(j);
                        Department_STR = oneDepartmentJsonObject.getString(Department_Name_KEY);
                        studentsEntity= new StudentsEntity(Book_Title_STR, Book_Description_STR, PublishYear_STR, PHOTO_STR, ISBN_STR, AuthorName_STR, Department_STR);
                        list.add(studentsEntity);
                    }
                }
            }
        }else if (MSG_STR.equals( Email_Already_Taken)){
            studentsEntity.setException(Email_Already_Taken);
            list.add(studentsEntity);
        }else if (MSG_STR.equals(UserName_Already_Taken)){
            studentsEntity.setException(UserName_Already_Taken);
            list.add(studentsEntity);
        }else if (MSG_STR.equals( Error_)){
            studentsEntity.setException(Error_);
            list.add(studentsEntity);
        }else if (MSG_STR.equals(Invalid_Format)){
            studentsEntity.setException(Invalid_Format);
            list.add(studentsEntity);
        }
        return list;
    }
}