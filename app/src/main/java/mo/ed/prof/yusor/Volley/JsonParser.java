package mo.ed.prof.yusor.Volley;

import android.widget.Toast;

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
}