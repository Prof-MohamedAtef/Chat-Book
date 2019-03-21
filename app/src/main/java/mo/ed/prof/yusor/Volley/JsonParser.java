package mo.ed.prof.yusor.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

import static mo.ed.prof.yusor.helpers.Designsers.ProgressGenerator.KEY_DepartmentID;
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
    private static String book_Not_found_KEY="book Not found";
    private static String USER_ID_KEY="id";
    private static String UserID_STR;
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
    private String BookID_KEY="id";
    private String ID_STR;
    private String TitleRequired_KEY="The title field is required.";
    private String AuthorNameError_KEY="The name has already been taken.";
    private String Pivot_KEY="pivot";
    private JSONArray pivotJsonArray;
    private String Price_KEY="price";
    private String Price_STR;
    private String BookStatus_KEY="book_status";
    private String BookStatus_STR;
    private String Availability_KEY="availability";
    private String Availability_STR;
    private String TransactionType_KEY="transaction_types_id";
    private String TransactionType_STR;
    private String TokenTitle_KEY="The title has already been taken.";
    private String StudentID_KEY="student_id";
    private String SellerID_STR;
    private String BookID_STR;
    private String BookID__KEY="book_id";
    private String Book_Array="book";
    private JSONArray bookJsonArray;
    private String Book_Description_KEY="desc";
    private String DepartmentID_STR;
    private String Student_Array="student";
    private JSONArray studentJsonArray;
    private String Student_KEY_ID="id";
    private String StudentID_STR;
    private String PersonName_KEY="perso_name";
    private String UserName_KEY="UserName";
    private String Gender_KEY="Gender";
    private String Emai_KEY="Email";
    private String DepartmentID_KEY="department_id";
    private JSONObject oneAuthorJsonObjectData;
    private JSONArray AuthorJsonArray;
    private String AuthorName_KEY="name";
    private JSONArray DepartmentJsonArray;
    private String DepartmentName_STR;
    private String DepartmentName_KEY="name";
    private String PivotID_STR;

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
                    UserID_STR=oneUserJsonObject.getString(USER_ID_KEY);
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
                    studentsEntity= new StudentsEntity(API_Token_STR, PersonName_STR, Email_STR, UserName_STR,Gender_STR,Department_STR,UserID_STR,"","");
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

    public static ArrayList<StudentsEntity> BookAddedJsonParse(String UsersDesires)
            throws JSONException {
        studentsEntity= new StudentsEntity();
        UsersDesiresJson = new JSONObject(UsersDesires);
        MSG_STR = UsersDesiresJson.getString("msg");
        if (MSG_STR.equals(DONE_KEY)){
            studentsEntity.setDoneStatus(MSG_STR);
            list.add(studentsEntity);
        }else if (MSG_STR.equals( book_Not_found_KEY)){
            studentsEntity.setException(MSG_STR);
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
                    UserID_STR=oneUserJsonObject.getString(USER_ID_KEY);
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
                    studentsEntity= new StudentsEntity(API_Token_STR, PersonName_STR, Email_STR, UserName_STR,Gender_STR,Department_STR,UserID_STR,"","");
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
                ISBN_STR = oneBookJsonObjectData.getString(ISBN_Num_KEY);
                PHOTO_STR = oneBookJsonObjectData.getString(PHOTO_KEY);
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

    public ArrayList<StudentsEntity> getSimilarBooks(String response) throws JSONException {
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
                ISBN_STR = oneBookJsonObjectData.getString(ISBN_Num_KEY);
                PHOTO_STR = oneBookJsonObjectData.getString(PHOTO_KEY);
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
                        pivotJsonArray = oneBookJsonObjectData.getJSONArray(Pivot_KEY);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int j = 0; j < pivotJsonArray.length(); j++) {
                        JSONObject onePivotJsonObject = pivotJsonArray.getJSONObject(j);
                        Price_STR = onePivotJsonObject.getString(Price_KEY);
                        BookStatus_STR = onePivotJsonObject.getString(BookStatus_KEY);
                        Availability_STR = onePivotJsonObject.getString(Availability_KEY);
                        TransactionType_STR = onePivotJsonObject.getString(TransactionType_KEY);
                        studentsEntity= new StudentsEntity(Book_Title_STR, Book_Description_STR, PublishYear_STR, PHOTO_STR,
                                ISBN_STR, AuthorName_STR, Price_STR, BookStatus_STR,Availability_STR,TransactionType_KEY);
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

    public ArrayList<StudentsEntity> parseAddedBooksJsonDetails(String response) throws JSONException {
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
                ISBN_STR = oneBookJsonObjectData.getString(ISBN_Num_KEY);
//                PHOTO_STR = oneBookJsonObjectData.getString(PHOTO_KEY);
                ID_STR=oneBookJsonObjectData.getString(BookID_KEY);
                studentsEntity= new StudentsEntity(Book_Title_STR, ID_STR, Book_Description_STR, PublishYear_STR, PHOTO_STR, ISBN_STR,"details","details1");
                list.add(studentsEntity);
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
        }else if (MSG_STR.equals(TitleRequired_KEY)){
            studentsEntity.setException(Invalid_Format);
            list.add(studentsEntity);
        } else if (MSG_STR.equals(AuthorNameError_KEY)){
            studentsEntity.setException(Invalid_Format);
            list.add(studentsEntity);
        }else if (MSG_STR.equals(TokenTitle_KEY)){
            studentsEntity.setException(TokenTitle_KEY);
            list.add(studentsEntity);
        }
        return list;
    }

    public ArrayList<StudentsEntity> parseAllBooksForSale(String response) throws JSONException {
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
                SellerID_STR=oneBookJsonObjectData.getString(StudentID_KEY);
                BookID_STR=oneBookJsonObjectData.getString(BookID__KEY);
                Price_STR=oneBookJsonObjectData.getString(Price_KEY);
                Availability_STR=oneBookJsonObjectData.getString(Availability_KEY);
                BookStatus_STR=oneBookJsonObjectData.getString(BookStatus_KEY);
                TransactionType_STR=oneBookJsonObjectData.getString(TransactionType_KEY);
                JSONArray authorJsonArray = null;
                try {
                    pivotJsonArray= oneBookJsonObjectData.getJSONArray(Pivot_KEY);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int x = 0; x < pivotJsonArray.length(); x++) {
                    JSONObject onePivotJsonObject =pivotJsonArray.getJSONObject(x);
                    PivotID_STR = onePivotJsonObject.getString(BookID_KEY);
                    Book_Title_STR = onePivotJsonObject.getString(Book_Title_KEY);
                    Book_Description_STR= onePivotJsonObject.getString(Book_Description_KEY);
                    PublishYear_STR= onePivotJsonObject.getString(PublishYear_KEY);
                    AuthorID_STR= onePivotJsonObject.getString(AuthorID_KEY);
                    DepartmentID_STR = onePivotJsonObject.getString(KEY_DepartmentID);
                    ISBN_STR = onePivotJsonObject.getString(ISBN_Num_KEY);
                    PHOTO_STR = onePivotJsonObject.getString(PHOTO_KEY);
                    try {
                        studentJsonArray = oneBookJsonObjectData.getJSONArray(Student_Array);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int j = 0; j < studentJsonArray.length(); j++) {
                        JSONObject oneStudentJsonObject = studentJsonArray.getJSONObject(j);
                        StudentID_STR = oneStudentJsonObject.getString(Student_KEY_ID);
                        PersonName_STR= oneStudentJsonObject.getString(PersonName_KEY);
                        UserName_STR= oneStudentJsonObject.getString(UserName_KEY);
                        Gender_STR= oneStudentJsonObject.getString(Gender_KEY);
                        Email_STR= oneStudentJsonObject.getString(Emai_KEY);
                        DepartmentID_STR= oneStudentJsonObject.getString(DepartmentID_KEY);

                        try {
                            AuthorJsonArray= oneBookJsonObjectData.getJSONArray(AUTHOR_Obj_KEY);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int l = 0; l < AuthorJsonArray.length(); l++) {
                            JSONObject oneAuthorJsonObject = AuthorJsonArray.getJSONObject(l);
                            AuthorName_STR= oneAuthorJsonObject.getString(AuthorName_KEY);

                            try {
                                DepartmentJsonArray= oneBookJsonObjectData.getJSONArray(Department_KEY);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int z = 0; z< DepartmentJsonArray.length(); z++) {
                                JSONObject oneDepartmentJsonObject = DepartmentJsonArray.getJSONObject(z);
                                DepartmentName_STR= oneDepartmentJsonObject.getString(DepartmentName_KEY);
                            }
                        }
                            studentsEntity= new StudentsEntity(SellerID_STR, BookID_STR, Price_STR, Availability_STR,
                                    TransactionType_STR, BookID_STR, Book_Title_STR, Book_Description_STR,
                                    PublishYear_STR,AuthorID_STR, DepartmentID_STR
                        ,ISBN_STR,PHOTO_STR, StudentID_STR,PersonName_STR, UserName_STR,
                                    Gender_STR, Email_STR, DepartmentID_STR,DepartmentName_STR,AuthorName_STR,PivotID_STR);
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