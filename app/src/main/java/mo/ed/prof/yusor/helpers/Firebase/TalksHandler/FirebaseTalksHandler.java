package mo.ed.prof.yusor.helpers.Firebase.TalksHandler;

/**
 * Created by Prof-Mohamed Atef on 3/20/2019.
 */

public class FirebaseTalksHandler {

    public FirebaseTalksHandler(){}

    String SellerName, SellerID, BuyerName, BuyerID;

    public FirebaseTalksHandler(String sellerName, String sellerID, String buyerName, String buyerID) {
        SellerName = sellerName;
        SellerID = sellerID;
        BuyerName = buyerName;
        BuyerID = buyerID;
    }

    public String getSellerName() {
        return SellerName;
    }

    public void setSellerName(String sellerName) {
        SellerName = sellerName;
    }

    public String getSellerID() {
        return SellerID;
    }

    public void setSellerID(String sellerID) {
        SellerID = sellerID;
    }

    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    public String getBuyerID() {
        return BuyerID;
    }

    public void setBuyerID(String buyerID) {
        BuyerID = buyerID;
    }
}
