package mo.ed.prof.yusor.helpers;

import java.io.Serializable;

/**
 * Created by Prof-Mohamed Atef on 2/5/2019.
 */

public class OptionsEntity implements Serializable {
    public static Object FBAccessToken;
    String BookTitle;
    String BookPrice;
    String BookImage;
    String BookOwnerID;

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
