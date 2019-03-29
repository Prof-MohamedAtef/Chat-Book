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
    private static String firbase_id_STR;
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
    private String billAdded_KEY="bill added";
    private JSONArray billEntityJsonAray;
    private JSONObject oneBillJsonObjectData;
    private JSONArray BookJsonArray;
    private String BuyerID_STR;
    private String OwnerStatus_STR;
    private String BuyerStatus_STR;
    private String BillID_STR;
    private String SellerPName_STR;
    private String SellerUserName_STR;
    private String SellerGender_STR;
    private String SellerEmail_STR;
    private String SellerFireBUiD_STR;
    private JSONArray buyerJsonArray;
    private String BuyerPName_STR;
    private String BuyerUserName_STR;
    private String BuyerGender_STR;
    private String BuyerEmail_STR;
    private String BuyerDepartmentID_STR;
    private String BuyerFireBUiD_STR;
    private String SellerPersonName_STR;
    private String SellerFacultyID_STR;
    private String buyerPersonName_STR;
    private String buyerUserName_STR;
    private String buyerGender_STR;
    private String CreatedAt_STR;
    private String UpdatedAt_STR;
    private JSONArray oneBookDetailsJsonArray;
    private JSONArray userEntityJsonAray;
    private JSONObject oneUserJsonObjectData;
    private JSONArray oneDepartmentJsonArray;
    private String FirebaseUserID_STR;

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
                    firbase_id_STR=oneUserJsonObject.getString("firbase_id");
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
                    studentsEntity= new StudentsEntity(API_Token_STR, PersonName_STR, Email_STR, UserName_STR,Gender_STR,Department_STR,UserID_STR,firbase_id_STR,"");
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
                    firbase_id_STR=oneUserJsonObject.getString("firbase_id");
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
                    studentsEntity= new StudentsEntity(API_Token_STR, PersonName_STR, Email_STR, UserName_STR,Gender_STR,Department_STR,UserID_STR,firbase_id_STR,"");
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
                BookID_STR = oneBookJsonObjectData.getString("id");
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
                                ISBN_STR, AuthorName_STR, Price_STR, BookStatus_STR,Availability_STR,TransactionType_KEY, BookID_STR);
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
        if (MSG_STR.equals(DONE_KEY)) {
            try {
                BookEntityJsonAray = UsersDesiresJson.getJSONArray("data");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.clear();
            for (int i = 0; i < BookEntityJsonAray.length(); i++) {
                // Get the JSON object representing a movie per each loop
                oneBookJsonObjectData = BookEntityJsonAray.getJSONObject(i);
                SellerID_STR = oneBookJsonObjectData.getString(StudentID_KEY);
                BookID_STR = oneBookJsonObjectData.getString(BookID__KEY);
                Price_STR = oneBookJsonObjectData.getString(Price_KEY);
                Availability_STR = oneBookJsonObjectData.getString(Availability_KEY);
                BookStatus_STR = oneBookJsonObjectData.getString(BookStatus_KEY);
                TransactionType_STR = oneBookJsonObjectData.getString(TransactionType_KEY);
                JSONArray authorJsonArray = null;
                try {
                    pivotJsonArray = oneBookJsonObjectData.getJSONArray("book");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int x = 0; x < pivotJsonArray.length(); x++) {
                    JSONObject onePivotJsonObject = pivotJsonArray.getJSONObject(x);
                    BookID_STR = onePivotJsonObject.getString(BookID_KEY);
                    Book_Title_STR = onePivotJsonObject.getString(Book_Title_KEY);
                    Book_Description_STR = onePivotJsonObject.getString(Book_Description_KEY);
                    PublishYear_STR = onePivotJsonObject.getString(PublishYear_KEY);
                    AuthorID_STR = onePivotJsonObject.getString(AuthorID_KEY);
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
                        PersonName_STR = oneStudentJsonObject.getString(PersonName_KEY);
                        UserName_STR = oneStudentJsonObject.getString(UserName_KEY);
                        Gender_STR = oneStudentJsonObject.getString(Gender_KEY);
                        Email_STR = oneStudentJsonObject.getString(Emai_KEY);
                        DepartmentID_STR = oneStudentJsonObject.getString(DepartmentID_KEY);
                        SellerFireBUiD_STR = oneStudentJsonObject.getString("firbase_id");

                        try {
                            AuthorJsonArray = oneBookJsonObjectData.getJSONArray(AUTHOR_Obj_KEY);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int l = 0; l < AuthorJsonArray.length(); l++) {
                            JSONObject oneAuthorJsonObject = AuthorJsonArray.getJSONObject(l);
                            AuthorName_STR = oneAuthorJsonObject.getString(AuthorName_KEY);

                            try {
                                DepartmentJsonArray = oneBookJsonObjectData.getJSONArray(Department_KEY);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int z = 0; z < DepartmentJsonArray.length(); z++) {
                                JSONObject oneDepartmentJsonObject = DepartmentJsonArray.getJSONObject(z);
                                DepartmentName_STR = oneDepartmentJsonObject.getString(DepartmentName_KEY);
                                try {
                                    billEntityJsonAray = oneBookJsonObjectData.getJSONArray("bill");
                                    for (int O = 0; O < billEntityJsonAray.length(); O++) {
                                        JSONObject oneBillJsonObj = billEntityJsonAray.getJSONObject(O);
                                        BillID_STR = oneBillJsonObj.getString("id");
                                        BuyerID_STR = oneBillJsonObj.getString("buyer_id");
                                        OwnerStatus_STR = oneBillJsonObj.getString("owner_status");
                                        BuyerStatus_STR = oneBillJsonObj.getString("buyer_status");
                                        try {
                                            buyerJsonArray = oneBookJsonObjectData.getJSONArray("buyer_student");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        for (int G = 0; G < buyerJsonArray.length(); G++) {
                                            JSONObject oneBuyerJsonObj = buyerJsonArray.getJSONObject(G);
                                            BuyerFireBUiD_STR = oneBuyerJsonObj.getString("firbase_id");
                                        /*

                                         */
                                            studentsEntity = new StudentsEntity(SellerID_STR, BookID_STR, Price_STR, Availability_STR,
                                                    TransactionType_STR, BookID_STR, Book_Title_STR, Book_Description_STR,
                                                    PublishYear_STR, AuthorID_STR, DepartmentID_STR
                                                    , ISBN_STR, PHOTO_STR, StudentID_STR, PersonName_STR, UserName_STR,
                                                    Gender_STR, Email_STR, DepartmentID_STR, DepartmentName_STR, AuthorName_STR,
                                                    PivotID_STR, SellerFireBUiD_STR, BookStatus_STR, BillID_STR, BuyerID_STR, OwnerStatus_STR,
                                                    BuyerStatus_STR, BuyerFireBUiD_STR);
                                            list.add(studentsEntity);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    BillID_STR = null;
                                    BuyerID_STR = null;
                                    OwnerStatus_STR = null;
                                    BuyerStatus_STR = null;
                                    BuyerFireBUiD_STR = null;
                                    studentsEntity = new StudentsEntity(SellerID_STR, BookID_STR, Price_STR, Availability_STR,
                                            TransactionType_STR, BookID_STR, Book_Title_STR, Book_Description_STR,
                                            PublishYear_STR, AuthorID_STR, DepartmentID_STR
                                            , ISBN_STR, PHOTO_STR, StudentID_STR, PersonName_STR, UserName_STR,
                                            Gender_STR, Email_STR, DepartmentID_STR, DepartmentName_STR, AuthorName_STR,
                                            PivotID_STR, SellerFireBUiD_STR, BookStatus_STR, BillID_STR, BuyerID_STR, OwnerStatus_STR,
                                            BuyerStatus_STR, BuyerFireBUiD_STR);
                                    list.add(studentsEntity);

                                }
                            }
                        }
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
        if (MSG_STR.equals(DONE_KEY)&&list.size()==0){
            studentsEntity.setServerMessage("no books on gallery!");
            list.add(studentsEntity);
        }
        return list;
    }

    public ArrayList<StudentsEntity> returnCreatedBill(String response) throws JSONException{
        studentsEntity= new StudentsEntity();
        UsersDesiresJson = new JSONObject(response);
        MSG_STR = UsersDesiresJson.getString("msg");
        if (MSG_STR.equals(billAdded_KEY)){
            try {
                billEntityJsonAray = UsersDesiresJson.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.clear();
            for (int i = 0; i < billEntityJsonAray.length(); i++) {
                oneBillJsonObjectData = billEntityJsonAray.getJSONObject(i);
                BillID_STR=oneBillJsonObjectData.getString("id");
                Price_STR=oneBillJsonObjectData.getString("TotalAmount");
                OwnerStatus_STR=oneBillJsonObjectData.getString("owner_status");
                BuyerStatus_STR=oneBillJsonObjectData.getString("buyer_status");
                StudentID_STR=oneBillJsonObjectData.getString("student_id");



                JSONArray authorJsonArray = null;
                try {
                    BookJsonArray= oneBillJsonObjectData.getJSONArray("book");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int x = 0; x < BookJsonArray.length(); x++) {
                    JSONObject oneBookJsonObject = BookJsonArray.getJSONObject(x);
                    BookID_STR=oneBookJsonObject .getString("id");
                    Book_Title_STR = oneBookJsonObject .getString(Book_Title_KEY);
                    Book_Description_STR = oneBookJsonObject .getString(Book_Description_KEY);
                    PublishYear_STR = oneBookJsonObject .getString(PublishYear_KEY);
                    AuthorID_STR = oneBookJsonObject .getString(AuthorID_KEY);
                    DepartmentID_STR = oneBookJsonObject .getString(KEY_DepartmentID);
                    ISBN_STR = oneBookJsonObject .getString(ISBN_Num_KEY);
                    PHOTO_STR = oneBookJsonObject .getString(PHOTO_KEY);
                    ISBN_STR= oneBookJsonObject .getString("ISBN_num");
                    StudentID_STR= oneBookJsonObject .getString("student_id");
                    try {
                        studentJsonArray= oneBillJsonObjectData.getJSONArray("student");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int k = 0; k < studentJsonArray.length(); k++) {
                        JSONObject oneStudentJsonObjectData = studentJsonArray.getJSONObject(k);
                        BookID_STR = oneStudentJsonObjectData.getString("id");
                        SellerPName_STR = oneStudentJsonObjectData.getString("perso_name");
                        SellerUserName_STR= oneStudentJsonObjectData.getString("UserName");
                        SellerGender_STR= oneStudentJsonObjectData.getString("Gender");
                        SellerEmail_STR= oneStudentJsonObjectData.getString("Email");
                        DepartmentID_STR = oneStudentJsonObjectData.getString("department_id");
                        SellerFireBUiD_STR = oneStudentJsonObjectData.getString("firbase_id");
                        try {
                            buyerJsonArray= oneBillJsonObjectData.getJSONArray("buyer");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int l = 0; l < buyerJsonArray.length(); l++) {
                            JSONObject oneBuyerJsonObjectData = buyerJsonArray.getJSONObject(l);
                            BuyerID_STR = oneBuyerJsonObjectData.getString("id");
                            BuyerPName_STR = oneBuyerJsonObjectData.getString("perso_name");
                            BuyerUserName_STR= oneBuyerJsonObjectData.getString("UserName");
                            BuyerGender_STR= oneBuyerJsonObjectData.getString("Gender");
                            BuyerEmail_STR= oneBuyerJsonObjectData.getString("Email");
                            BuyerDepartmentID_STR = oneBuyerJsonObjectData.getString("department_id");
                            BuyerFireBUiD_STR = oneBuyerJsonObjectData.getString("firbase_id");
                            studentsEntity= new StudentsEntity(BillID_STR, Price_STR,OwnerStatus_STR, BuyerStatus_STR, StudentID_STR, BuyerID_STR,
                                    BookID_STR, Book_Title_STR, Book_Description_STR, PublishYear_STR, AuthorID_STR, DepartmentID_STR, ISBN_STR,
                                    PHOTO_STR, StudentID_STR, BookID_STR, SellerPName_STR, SellerUserName_STR, SellerGender_STR, SellerEmail_STR,
                                    DepartmentID_STR, SellerFireBUiD_STR, BuyerPName_STR,BuyerUserName_STR, BuyerGender_STR, BuyerEmail_STR,
                                    BuyerDepartmentID_STR, BuyerFireBUiD_STR);
                            list.add(studentsEntity);
                        }
                    }
                }
            }
        }else if (MSG_STR.equals( "bill  not added")){
            studentsEntity.setException("bill  not added");
            list.add(studentsEntity);
        }else if (MSG_STR.equals("book Not found")){
            studentsEntity.setException("book Not found");
            list.add(studentsEntity);
        }
        return list;
    }

    public ArrayList<StudentsEntity> getSoldBills(String response) throws JSONException{
        studentsEntity= new StudentsEntity();
        UsersDesiresJson = new JSONObject(response);
        MSG_STR = UsersDesiresJson.getString("msg");
        if (MSG_STR.equals(DONE_KEY)){
            try {
                billEntityJsonAray = UsersDesiresJson.getJSONArray("data");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.clear();
            for (int i = 0; i < billEntityJsonAray.length(); i++) {
                // Get the JSON object representing a movie per each loop
                oneBillJsonObjectData = billEntityJsonAray.getJSONObject(i);
                BillID_STR= oneBillJsonObjectData.getString("id");
                CreatedAt_STR= oneBillJsonObjectData.getString("created_at");
                UpdatedAt_STR= oneBillJsonObjectData.getString("updated_at");
                BookID_STR= oneBillJsonObjectData.getString("book_id");
                BuyerID_STR = oneBillJsonObjectData.getString("buyer_id");
                OwnerStatus_STR = oneBillJsonObjectData.getString("owner_status");
                BuyerStatus_STR= oneBillJsonObjectData.getString("buyer_status");
                StudentID_STR= oneBillJsonObjectData.getString("student_id");
                JSONArray bookJsonArray = null;
                try {
                    bookJsonArray= oneBillJsonObjectData.getJSONArray("book");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int x = 0; x < bookJsonArray.length(); x++) {
                    JSONObject oneAuthorJsonObject = bookJsonArray.getJSONObject(x);
                    Book_Title_STR= oneAuthorJsonObject.getString("title");
                    Book_Description_STR= oneAuthorJsonObject.getString("desc");
                    PublishYear_STR= oneAuthorJsonObject.getString("publish_year");
                    AuthorID_STR= oneAuthorJsonObject.getString(AuthorID_KEY);
                    DepartmentID_STR= oneAuthorJsonObject.getString(DepartmentID_KEY);
                    ISBN_STR= oneAuthorJsonObject.getString(ISBN_Num_KEY);
                    JSONArray studentJsonArray = null;
                    try {
                        studentJsonArray= oneBillJsonObjectData.getJSONArray("student_seller");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int z = 0; z < studentJsonArray.length(); z++) {
                        JSONObject oneStudentSellerJsonObject = studentJsonArray.getJSONObject(z);
                        SellerID_STR = oneStudentSellerJsonObject.getString("id");
                        SellerPersonName_STR = oneStudentSellerJsonObject.getString("perso_name");
                        SellerUserName_STR= oneStudentSellerJsonObject.getString("UserName");
                        SellerGender_STR= oneStudentSellerJsonObject.getString("Gender");
                        SellerEmail_STR= oneStudentSellerJsonObject.getString("Email");
                        SellerFacultyID_STR= oneStudentSellerJsonObject.getString("department_id");
                        SellerFireBUiD_STR= oneStudentSellerJsonObject.getString("firbase_id");
                        try {
                            buyerJsonArray= oneBillJsonObjectData.getJSONArray("student_buyer");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int a = 0; a < studentJsonArray.length(); a++) {
                            JSONObject oneStudentBuyerJsonObject = buyerJsonArray.getJSONObject(a);
                            BuyerID_STR= oneStudentBuyerJsonObject.getString("id");
                            buyerPersonName_STR = oneStudentBuyerJsonObject.getString("perso_name");
                            buyerUserName_STR= oneStudentBuyerJsonObject.getString("UserName");
                            buyerGender_STR= oneStudentBuyerJsonObject.getString("Gender");
                            BuyerEmail_STR= oneStudentBuyerJsonObject.getString("Email");
                            BuyerDepartmentID_STR= oneStudentBuyerJsonObject.getString("department_id");
                            BuyerFireBUiD_STR= oneStudentBuyerJsonObject.getString("firbase_id");
                        }
                    }
                    StudentsEntity studentsEntity= new StudentsEntity(BillID_STR,BookID_STR,BuyerID_STR, OwnerStatus_STR, BuyerStatus_STR, StudentID_STR,
                            Book_Title_STR, Book_Description_STR, PublishYear_STR, AuthorID_STR, DepartmentID_STR, ISBN_STR,
                            SellerID_STR, SellerPersonName_STR, SellerUserName_STR, SellerGender_STR,SellerEmail_STR, SellerFacultyID_STR,
                            SellerFireBUiD_STR, BuyerID_STR, buyerPersonName_STR, buyerUserName_STR, buyerGender_STR, BuyerEmail_STR,
                            BuyerDepartmentID_STR, BuyerFireBUiD_STR,CreatedAt_STR, UpdatedAt_STR,"", "", "");
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

    public ArrayList<StudentsEntity> getMyBooks(String response) throws JSONException {
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
                BookID_STR = oneBookJsonObjectData.getString("id");
                Book_Title_STR = oneBookJsonObjectData.getString(Book_Title_KEY);
                Book_Description_STR = oneBookJsonObjectData.getString(Description_KEY);
                PublishYear_STR = oneBookJsonObjectData.getString(PublishYear_KEY);
                ISBN_STR = oneBookJsonObjectData.getString(ISBN_Num_KEY);
                PHOTO_STR = oneBookJsonObjectData.getString(PHOTO_KEY);
                JSONArray authorJsonArray = null;
                try {
                    oneBookDetailsJsonArray = oneBookJsonObjectData.getJSONArray("bookdetails");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int x = 0; x < oneBookDetailsJsonArray.length(); x++) {
                    JSONObject oneAuthorJsonObject = oneBookDetailsJsonArray.getJSONObject(x);
                    Price_STR = oneAuthorJsonObject.getString("price");
                    BookStatus_STR = oneAuthorJsonObject.getString("book_status");
                    Availability_STR = oneAuthorJsonObject.getString("availability");
                    TransactionType_STR = oneAuthorJsonObject.getString("transaction_types_id");
                    studentsEntity = new StudentsEntity(Book_Title_STR, Book_Description_STR, PublishYear_STR, PHOTO_STR,
                            ISBN_STR, AuthorName_STR, Price_STR, BookStatus_STR, Availability_STR, TransactionType_STR, BookID_STR);
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

    public ArrayList<StudentsEntity> ApproveBill(String response) throws JSONException{
        studentsEntity= new StudentsEntity();
        UsersDesiresJson = new JSONObject(response);
        MSG_STR = UsersDesiresJson.getString("msg");
        list.clear();
        if (MSG_STR.equals(DONE_KEY)){
            studentsEntity.setServerMessage(DONE_KEY);
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

    public ArrayList<StudentsEntity> updateBook(String response) throws JSONException{
        studentsEntity= new StudentsEntity();
        UsersDesiresJson = new JSONObject(response);
        MSG_STR = UsersDesiresJson.getString("msg");
        list.clear();
        if (MSG_STR.equals(DONE_KEY)){
            studentsEntity.setServerMessage(DONE_KEY);
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

    public ArrayList<StudentsEntity> deleteBook(String response) throws JSONException{
        studentsEntity= new StudentsEntity();
        UsersDesiresJson = new JSONObject(response);
        MSG_STR = UsersDesiresJson.getString("msg");
        list.clear();
        if (MSG_STR.equals(DONE_KEY)){
            studentsEntity.setServerMessage(DONE_KEY);
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

    public ArrayList<StudentsEntity> sendReports(String response) throws  JSONException {
        studentsEntity= new StudentsEntity();
        UsersDesiresJson = new JSONObject(response);
        MSG_STR = UsersDesiresJson.getString("msg");
        list.clear();
        if (MSG_STR.equals(DONE_KEY)){
            studentsEntity.setServerMessage(DONE_KEY);
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

    public ArrayList<StudentsEntity> getProfile(String response) throws  JSONException {
        studentsEntity= new StudentsEntity();
        UsersDesiresJson = new JSONObject(response);
        MSG_STR = UsersDesiresJson.getString("msg");
        if (MSG_STR.equals(DONE_KEY)){
            try {
                userEntityJsonAray = UsersDesiresJson.getJSONArray("data");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.clear();
            for (int i = 0; i < userEntityJsonAray.length(); i++) {
                // Get the JSON object representing a movie per each loop
                oneUserJsonObjectData = userEntityJsonAray.getJSONObject(i);
                UserID_STR= oneUserJsonObjectData.getString("id");
                UserName_STR = oneUserJsonObjectData.getString("UserName");
                Gender_STR= oneUserJsonObjectData.getString("Gender");
                Email_STR= oneUserJsonObjectData.getString("Email");
                FirebaseUserID_STR = oneUserJsonObjectData.getString("firbase_id");
                JSONArray authorJsonArray = null;
                try {
                    oneDepartmentJsonArray= oneBookJsonObjectData.getJSONArray("department");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int x = 0; x < oneDepartmentJsonArray.length(); x++) {
                    JSONObject oneAuthorJsonObject = oneDepartmentJsonArray.getJSONObject(x);
                    Department_STR = oneAuthorJsonObject.getString("name");
                    studentsEntity = new StudentsEntity(UserID_STR, UserName_STR, Gender_STR, Email_STR, FirebaseUserID_STR, Department_STR);
                    list.add(studentsEntity);
                }
            }
        }else if (MSG_STR.equals( Email_Already_Taken)){
            studentsEntity.setException(Email_Already_Taken);
            list.add(studentsEntity);
        }else if (MSG_STR.equals(UserName_Already_Taken)){
            studentsEntity.setException(UserName_Already_Taken);
            list.add    (studentsEntity);
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