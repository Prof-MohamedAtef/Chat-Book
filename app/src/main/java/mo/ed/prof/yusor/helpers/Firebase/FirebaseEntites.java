package mo.ed.prof.yusor.helpers.Firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import mo.ed.prof.yusor.helpers.Firebase.AuthenticationHandler.FirebaseUserHandler;
import mo.ed.prof.yusor.helpers.Firebase.ChatHandler.FirebaseChatHandler;

/**
 * Created by Prof-Mohamed Atef on 3/19/2019.
 */

public class FirebaseEntites {

    private final String LOG_TAG = FirebaseEntites.class.getSimpleName();


    private String currentUserID;
    DatabaseReference mDatabase;
    private String currentMessageID;

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

    public void AddMessage(DatabaseReference mDatabase,String messaging_key, FirebaseChatHandler firebaseChatHandler,String buyer_id, String seller_id) {
//        mDatabase.child(messaging_key).child(buyer_id+"buyer").setValue(firebaseChatHandler);
//        mDatabase.child(messaging_key).child(buyer_id+"buyer");
//        String key= mDatabase.child(messaging_key).child(buyer_id+"buyer").push().getKey();
//        firebaseChatHandler.setMessageID(key);
        String key=mDatabase.push().getKey();
        mDatabase.child(key).setValue(firebaseChatHandler);
//        currentMessageID=mDatabase.push().getKey();
//        mDatabase.child(currentMessageID).setValue(firebaseChatHandler);
        MessageChangeListener(mDatabase, messaging_key);
    }

    private void MessageChangeListener(DatabaseReference mDatabase, String currentMessageID) {
        mDatabase.child(currentMessageID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseChatHandler firebaseChatHandler=dataSnapshot.getValue(FirebaseChatHandler.class);
                if (firebaseChatHandler==null){
                    Log.e(LOG_TAG, "User data is null!");
                    return;
                }
                Log.e(LOG_TAG, "options data is changed!" + firebaseChatHandler.getMessageText()+" By " +firebaseChatHandler.getUserName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(LOG_TAG, "Failed to read options", databaseError.toException());
            }
        });
    }
}
