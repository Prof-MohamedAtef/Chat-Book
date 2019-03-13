package mo.ed.prof.yusor.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

/**
 * Created by Prof-Mohamed Atef on 3/13/2019.
 */

public class JsonParser {


    public JsonParser( ){

    }



    public static ArrayList<StudentsEntity> jsonParse(String UsersDesires)
            throws JSONException {

        UsersDesiresJson = new JSONObject(UsersDesires);
        UsersDesiresJsonAray = UsersDesiresJson.getJSONArray("Result");

        list.clear();
        for (int i = 0; i < UsersDesiresJsonAray.length(); i++) {
            // Get the JSON object representing a movie per each loop
            oneOptionData = UsersDesiresJsonAray.getJSONObject(i);
//            ID = oneOptionData .getString("ID");
//            Name = oneOptionData .getString("Name");
//            Mobile = oneOptionData .getString("Mobile");
//            Problem = oneOptionData .getString("Problem");
//            StudentsEntity entity = new StudentsEntity(ID, Name, Mobile, Problem);
//            list.add(entity);
        }
        return list;
    }



    public static JSONObject UsersDesiresJson;
    public static JSONArray UsersDesiresJsonAray;
    public static JSONObject oneOptionData;
    static ArrayList<StudentsEntity> list = new ArrayList<StudentsEntity>();

}