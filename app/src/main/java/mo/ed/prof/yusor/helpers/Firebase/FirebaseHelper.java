package mo.ed.prof.yusor.helpers.Firebase;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Prof-Mohamed Atef on 3/19/2019.
 */

public class FirebaseHelper extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("data");
//        databaseReference.keepSynced(true);
    }
}
