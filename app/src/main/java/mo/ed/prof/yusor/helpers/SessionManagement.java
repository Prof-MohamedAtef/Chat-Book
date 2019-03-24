package mo.ed.prof.yusor.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import mo.ed.prof.yusor.Activities.Authentication.SplashActivity;

/**
 * Created by Prof-Mohamed Atef on 2/5/2019.
 */

public class SessionManagement {
    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    public static Context _context;
    int PRIVATE_MODE = 0;
    public static final String PREFS_Logger = "LoggerFile";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_Profile_Pic = "profile_picture";
    public static final String KEY_idToken= "idToken";
    public static final String KEY_userID= "userID";
    public static final String KEY_LoginType= "LoginType";
    public static final String KEY_UserName= "show_message";
    public static final String KEY_Gender= "Gender";
    public static final String KEY_DepartmentName= "DepartmentName";
    public static final String firebase_UID_KEY="firebase_UID_KEY";

    public SessionManagement(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFS_Logger, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, SplashActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to startSignUp new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public void createLoginSessionType(String type){
        editor.putString(KEY_LoginType, type);
        editor.commit();
    }

    public HashMap<String, String> getLoginType(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_LoginType, pref.getString(KEY_LoginType, null));
        return user;
    }


    public void createLoginSession(String name, String email,String AccountPhoto, String idToken,String userID){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_Profile_Pic, AccountPhoto);
        editor.putString(KEY_idToken, idToken);
        editor.putString(KEY_userID, userID);
        editor.commit();
    }


    public void createYusorLoginSession(String idToken, String P_name, String email, String userName,String gender, String departmentName, String user_id, String firbase_id){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_idToken, idToken);
        editor.putString(KEY_NAME, P_name);
        editor.putString(KEY_userID, user_id);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_UserName, userName);
        editor.putString(KEY_Gender, gender);
        editor.putString(KEY_DepartmentName, departmentName);
        editor.putString(firebase_UID_KEY, firbase_id);

        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_UserName, pref.getString(KEY_UserName, null));
        user.put(KEY_Gender, pref.getString(KEY_Gender, null));
        user.put(KEY_DepartmentName, pref.getString(KEY_DepartmentName, null));
        user.put(KEY_userID, pref.getString(KEY_userID, null));
        user.put(KEY_idToken, pref.getString(KEY_idToken, null));
        user.put(firebase_UID_KEY, pref.getString(firebase_UID_KEY, null));
        return user;
    }


}
