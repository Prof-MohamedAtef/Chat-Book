package mo.ed.prof.yusor.helpers.Room;

import java.io.Serializable;

/**
 * Created by Prof-Mohamed Atef on 2/5/2019.
 */

public class StudentsEntity implements Serializable {
    public static Object FBAccessToken;
    String BookTitle;
    String BookPrice;
    String BookImage;
    String BookOwnerID;

    String DepartmentID, DepartmentName;

    String UserName, PersonName, Email, Password, Gender;

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
