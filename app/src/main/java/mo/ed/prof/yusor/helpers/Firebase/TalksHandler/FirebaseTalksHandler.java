package mo.ed.prof.yusor.helpers.Firebase.TalksHandler;

/**
 * Created by Prof-Mohamed Atef on 3/20/2019.
 */

public class FirebaseTalksHandler {

    public FirebaseTalksHandler(){}

    public FirebaseTalksHandler(String seller){
        this.Seller=seller;
    }

    String Seller;

    public String getSeller() {
        return Seller;
    }

    public void setSeller(String seller) {
        Seller = seller;
    }
}
