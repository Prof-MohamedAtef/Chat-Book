package mo.ed.prof.yusor.helpers.Room;

import java.io.Serializable;

/**
 * Created by Prof-Mohamed Atef on 2/5/2019.
 */

public class StudentsEntity implements Serializable {
    public static Object FBAccessToken;
    String ProfilePhoto;

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
    }

    String PivotID;
    String SellerEmail;
    String SellerGender;
    String SellerUserName;
    String SellerDepartmentID;
    String SellerID;

    public String getPivotID() {
        return PivotID;
    }

    public void setPivotID(String pivotID) {
        PivotID = pivotID;
    }

    public String getSellerEmail() {
        return SellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        SellerEmail = sellerEmail;
    }

    public String getSellerGender() {
        return SellerGender;
    }

    public void setSellerGender(String sellerGender) {
        SellerGender = sellerGender;
    }

    public String getSellerUserName() {
        return SellerUserName;
    }

    public void setSellerUserName(String sellerUserName) {
        SellerUserName = sellerUserName;
    }

    public String getSellerDepartmentID() {
        return SellerDepartmentID;
    }

    public void setSellerDepartmentID(String sellerDepartmentID) {
        SellerDepartmentID = sellerDepartmentID;
    }

    public String getSellerID() {
        return SellerID;
    }

    public void setSellerID(String sellerID) {
        SellerID = sellerID;
    }

    public StudentsEntity(String sellerID_str, String bookID_str, String price_str, String availability_str, String transactionType_str,
                          String bookID_str1, String book_title_str, String book_description_str, String publishYear_str,
                          String authorID_str, String departmentID_str, String isbn_str, String photo_str, String studentID_str,
                          String personName_str, String userName_str, String gender_str, String email_str, String departmentID_str1,
                          String department_name, String author_name, String pivot_id_str) {
        this.PivotID=pivot_id_str;
        this.BookOwnerID=sellerID_str;
        this.BookID=bookID_str;
        this.BookPrice=price_str;
        this.Availability=availability_str;
        this.TransactionType=transactionType_str;
        this.BookTitle=book_title_str;
        this.BookDescription=book_description_str;
        this.PublishYear=publishYear_str;
        this.AuthorID=authorID_str;
        this.DepartmentID=departmentID_str;
        this.ISBN_NUM=isbn_str;
        this.BookImage=photo_str;
        this.PersonName=personName_str;
        this.SellerUserName=userName_str;
        this.SellerGender=gender_str;
        this.SellerEmail=email_str;
        this.SellerDepartmentID=departmentID_str1;
        this.AuthorTitle=author_name;
        this.DepartmentName=department_name;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    String UserID;
    String TransactionType;
    String Availability;
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

    //suggest Books
    public StudentsEntity(String book_title_str, String book_description_str, String publishYear_str, String photo_str, String isbn_str, String authorName_str, String price_str, String BookStatus, String Book_Availability, String Transaction_Type) {
        this.BookTitle=book_title_str;
        this.BookDescription=book_description_str;
        this.PublishYear=publishYear_str;
        this.BookPhoto=photo_str;
        this.ISBN_NUM=isbn_str;
        this.AuthorTitle=authorName_str;
        this.BookPrice=price_str;
        this.BookStatus=BookStatus;
        this.Availability=Book_Availability;
        this.TransactionType=Transaction_Type;
    }

    String DoneStatus;

    public String getDoneStatus() {
        return DoneStatus;
    }

    public void setDoneStatus(String doneStatus) {
        DoneStatus = doneStatus;
    }

    public StudentsEntity(String msgStr) {
        this.Exception=msgStr;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public String getAvailability() {
        return Availability;
    }

    public void setAvailability(String availability) {
        Availability = availability;
    }

    public String getISBN_NUM() {
        return ISBN_NUM;
    }

    public void setISBN_NUM(String ISBN_NUM) {
        this.ISBN_NUM = ISBN_NUM;
    }

    public String getBookPhoto() {
        return BookPhoto;
    }

    public void setBookPhoto(String bookPhoto) {
        BookPhoto = bookPhoto;
    }

    public String getBookDescription() {
        return BookDescription;
    }

    public void setBookDescription(String bookDescription) {
        BookDescription = bookDescription;
    }

    public String getPublishYear() {
        return PublishYear;
    }

    public void setPublishYear(String publishYear) {
        PublishYear = publishYear;
    }

    //added book details
    public StudentsEntity(String book_title_str, String book_id,String book_description_str, String publishYear_str, String photo_str, String isbn_str, String details, String details1) {
        this.BookTitle=book_title_str;
        this.BookID=book_id;
        this.BookDescription=book_description_str;
        this.PublishYear=publishYear_str;
        this.BookImage=photo_str;
        this.ISBN_NUM=isbn_str;
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
    public StudentsEntity(String api_token_str, String personName_str, String email_str, String userName_str, String gender_str, String department_str, String user_id,String x1,String x2) {
        this.API_TOKEN=api_token_str;
        this.PersonName=personName_str;
        this.Email=email_str;
        this.UserName=userName_str;
        this.Gender=gender_str;
        this.DepartmentName="NULL_DEPARTMENT";
        this.UserID=user_id;
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


    public StudentsEntity(String authorID, String authorTitle,String x, String y){
        this.AuthorID=authorID;
        this.AuthorTitle=authorTitle;
    }



    public StudentsEntity(String BookID, String BookName,String x){
        this.BookID=BookID;
        this.BookTitle=BookName;
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
