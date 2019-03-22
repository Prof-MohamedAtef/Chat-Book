package mo.ed.prof.yusor.helpers;

import android.content.res.Resources;
import android.net.Uri;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

/**
 * Created by Prof-Mohamed Atef on 2/6/2019.
 */

public class Config {
    public static boolean TwoPane;
    public static int Year;
    public static int Month;
    public static int Day;
    public static String imageBitmap;
    public static String image_name;
    public static String currentImagePAth;
    public static String selectedImagePath;
    public static File StorageDir;
    public static ArrayList<StudentsEntity> FacultiesList;
    public static ArrayList<StudentsEntity> BooksList;
    public static String BookID;
    public static int BookPosition;
    public static int AuthPosition;
    public static ArrayList<StudentsEntity> AuthList;
    public static String AuthorTitle;
    public static String AuthorID;
    public static Uri ImageFileUri;
    public static boolean Author_Edit;
    public static String FacultyName;
    public static String FacultyID;
    public static int FacultyPosition;
    public static String NextNewBook;
    public static String ExistingBook;
    public static String BookExistence;
    public static String BookName;
    public static String ISBN_Number;
    public static String PublishYear;
    public static String BookDescription;
    public static String URL_Completed="http://fla4news.com/Yusor/";
    public static boolean Buyer;
    public static String SellerID;
    public static String FinalEmail;
    public static String Password;
    public static String UserName;

    // public static final String[] Faculties={
//            Resources.getSystem().getResourceName(R.string.faculty_computer_sciences_engineering).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_Business_Administration_in_yanboa).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_society_in_3ola).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_society_in_badr).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_society).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_society_in_El3ahd).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_society_in_7nakia).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_dentistry).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_Education).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_Engineering).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_Engineering_in_Yanboa).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_family_sciences).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_Law).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_medicine).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_Pharmacy).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_Sciences).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_Sciences_literatures).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_sciences_computers_in_yanboa).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_sciences_in_yanboa).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_Society_in_Khaibar).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_literature_and_human_sciences_in_yanboa).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_Nursing).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of__medical_rehab_sciences).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_applied_medical_sciences).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_applied_medical_sciences_in_yanboa).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_applied_medical_sciences_in_al_ola).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_business_administration_yanboa).toString(),
//            Resources.getSystem().getResourceName(R.string.faculty_of_business_administration).toString()
//    };
}