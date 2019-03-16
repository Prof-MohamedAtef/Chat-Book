package mo.ed.prof.yusor.helpers.Room;

import java.io.Serializable;

/**
 * Created by Prof-Mohamed Atef on 2/5/2019.
 */

public class StudentsEntity implements Serializable {
    public static Object FBAccessToken;
    String ISBN_NUM;
    String BookPhoto;
    String BookDescription;
    String PublishYear;
    String BookID;
    String BookTitle;
    String BookPrice;
    String BookImage;
    String BookOwnerID;

    String FacultyName;
    String FacultyID;

    public StudentsEntity(String book_title_str, String book_description_str, String publishYear_str, String photo_str, String isbn_str, String authorName_str, String department_str) {
        this.BookTitle=book_title_str;
        this.BookDescription=book_description_str;
        this.PublishYear=publishYear_str;
        this.BookPhoto=photo_str;
        this.ISBN_NUM=isbn_str;
        this.AuthorTitle=authorName_str;
        this.DepartmentName=department_str;
    }

    public String getFacultyName() {
        return FacultyName;
    }

    public void setFacultyName(String facultyName) {
        FacultyName = facultyName;
    }

    public String getFacultyID() {
        return FacultyID;
    }

    public void setFacultyID(String facultyID) {
        FacultyID = facultyID;
    }

    String AuthorTitle;

    String AuthorID;

    public String getAuthorID() {
        return AuthorID;
    }

    public void setAuthorID(String authorID) {
        AuthorID = authorID;
    }

    public String getAuthorTitle() {
        return AuthorTitle;
    }

    public void setAuthorTitle(String authorTitle) {
        AuthorTitle = authorTitle;
    }

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bookID) {
        BookID = bookID;
    }

    String Exception;

    public String getException() {
        return Exception;
    }

    public void setException(String exception) {
        Exception = exception;
    }

    String DepartmentID, DepartmentName;

    String UserName, PersonName, Email, Password, Gender;

    String API_TOKEN;

    public String getAPI_TOKEN() {
        return API_TOKEN;
    }

    public void setAPI_TOKEN(String API_TOKEN) {
        this.API_TOKEN = API_TOKEN;
    }

    public StudentsEntity(){

    }

    //Registration Constructor
    public StudentsEntity(String api_token_str, String personName_str, String email_str, String userName_str, String gender_str, String department_str) {
        this.API_TOKEN=api_token_str;
        this.PersonName=personName_str;
        this.Email=email_str;
        this.UserName=userName_str;
        this.Gender=gender_str;
        this.DepartmentName="NULL_DEPARTMENT";
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String departmentID) {
        DepartmentID = departmentID;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public StudentsEntity(String DepartmentID, String DepartmentName){
        this.DepartmentID=DepartmentID;
        this.DepartmentName=DepartmentName;
    }

    public String getBookOwnerID() {
        return BookOwnerID;
    }

    public void setBookOwnerID(String bookOwnerID) {
        BookOwnerID = bookOwnerID;
    }

    public String getBookStatus() {
        return BookStatus;
    }

    public void setBookStatus(String bookStatus) {
        BookStatus = bookStatus;
    }

    String BookStatus;

    public String getBookTitle() {
        return BookTitle;
    }

    public void setBookTitle(String bookTitle) {
        BookTitle = bookTitle;
    }

    public String getBookPrice() {
        return BookPrice;
    }

    public void setBookPrice(String bookPrice) {
        BookPrice = bookPrice;
    }

    public String getBookImage() {
        return BookImage;
    }

    public void setBookImage(String bookImage) {
        BookImage = bookImage;
    }
}
