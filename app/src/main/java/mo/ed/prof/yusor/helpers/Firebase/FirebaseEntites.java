package mo.ed.prof.yusor.helpers.Firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import mo.ed.prof.yusor.helpers.Firebase.AuthenticationHandler.FirebaseUserHandler;

/**
 * Created by Prof-Mohamed Atef on 3/19/2019.
 */

public class FirebaseEntites {

    private final String LOG_TAG = FirebaseEntites.class.getSimpleName();


    private String currentUserID;
    DatabaseReference mDatabase;
    public FirebaseEntites(DatabaseReference databaseReference){
        this.mDatabase=databaseReference;
    }

    public void AddUser(DatabaseReference databaseReference, FirebaseUserHandler firebaseUserHandler){
        currentUserID=databaseReference.push().getKey();
        databaseReference.child(currentUserID).setValue(firebaseUserHandler);
        UserChangeListener(currentUserID);
    }


    private void UserChangeListener(final String articleID) {

        mDatabase.child(articleID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseUserHandler firebaseUserHandler=dataSnapshot.getValue(FirebaseUserHandler.class);
                if (firebaseUserHandler ==null){
                    Log.e(LOG_TAG, "User data is null!");
                    return;
                }
                Log.e(LOG_TAG, "options data is changed!" + firebaseUserHandler.getUserID()+ firebaseUserHandler.getUSerName()+ firebaseUserHandler.getAPI_TOKEN()+ firebaseUserHandler.getGender()+ firebaseUserHandler.getPersonName()+ firebaseUserHandler.getEmail() );
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(LOG_TAG, "Failed to read options", databaseError.toException());
            }
        });
//        mDatabase.keepSynced(true);
    }
}
