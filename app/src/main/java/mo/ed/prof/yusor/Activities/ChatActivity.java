package mo.ed.prof.yusor.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import mo.ed.prof.yusor.Adapter.Chat.MessagesAdapter;
import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Config;
import mo.ed.prof.yusor.helpers.Firebase.ChatHandler.FirebaseChatHandler;
import mo.ed.prof.yusor.helpers.Firebase.FirebaseEntites;
import mo.ed.prof.yusor.helpers.Firebase.TalksHandler.FirebaseTalksHandler;
import mo.ed.prof.yusor.helpers.SessionManagement;

import static com.facebook.FacebookSdk.getApplicationContext;
import static mo.ed.prof.yusor.Adapter.BooksGalleryAdapter.AuthorName_KEY;
import static mo.ed.prof.yusor.Adapter.BooksGalleryAdapter.BookDescription_KEY;
import static mo.ed.prof.yusor.Adapter.BooksGalleryAdapter.BookID_KEY;
import static mo.ed.prof.yusor.Adapter.BooksGalleryAdapter.BookName_KEY;
import static mo.ed.prof.yusor.Adapter.BooksGalleryAdapter.BookOwnerID_KEY;
import static mo.ed.prof.yusor.Adapter.BooksGalleryAdapter.BookSellerID_KEY;
import static mo.ed.prof.yusor.Adapter.BooksGalleryAdapter.ISBN_KEY;
import static mo.ed.prof.yusor.Adapter.BooksGalleryAdapter.PivotID_KEY;
import static mo.ed.prof.yusor.Adapter.BooksGalleryAdapter.Price_KEY;
import static mo.ed.prof.yusor.Adapter.BooksGalleryAdapter.PublishYear_KEY;
import static mo.ed.prof.yusor.Adapter.BooksGalleryAdapter.SellerEmail_KEY;
import static mo.ed.prof.yusor.Adapter.BooksGalleryAdapter.SellerFacultyName_KEY;
import static mo.ed.prof.yusor.Adapter.BooksGalleryAdapter.Transaction_KEY;
import static mo.ed.prof.yusor.helpers.Config.TwoPane;

public class ChatActivity extends AppCompatActivity {

    private final String LOG_TAG = ChatActivity.class.getSimpleName();
    private String Messages_KEY="messages";
    private String FacultyName;
    private String TransactionType;
    private String Price;
    private String SellerEmail;
    private String BookDescription;
    private String PublishYear;
    private String AuthorName;
    private String ISBN;
    private String BookOwnerID;
    private String BookID;
    private String BookName;
    FirebaseChatHandler firebaseChatHandler;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mMessagesDatabase;
    private FirebaseStorage mFirebaseStorage;
    private EditText mEditTextMessage;
    private LinearLayout mButtonSend;
    private int DEFAULT_MSG_LENGTH_LIMIT=500;
    private MessagesAdapter mMessageAdapter;
    private SessionManagement sessionManagement;
    private HashMap<String, String> user;
    private String UserName;
    private FirebaseEntites firebaseEntities;
    private String UserID;
    private String SellerID;
    public static String BuyerPivotSellerMessagingKey;
    private String Buyer="buyer";
    private String Seller="seller";
    private String SellerPivotBuyerMessagingKey;
    private FirebaseDatabase database;
    private String PivotID;
    private String BuyerDirectSellerMessageingKey;
    private String SellerDirectBuyerMessageingKey;
    private String KEY;
    private Date Now;
    private String MessageText_STR;
    private DatabaseReference ThoughtsRef;
    private String BuyerID;
    private ArrayList<FirebaseChatHandler> FirebaseBuyerChatList;
    private ArrayList<FirebaseChatHandler> chatMessages;
    private VerifyConnection verifyConnection;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private String currentNodeKey;
    private String Now_STR;
    private String Talks_KEY="talks";
    private DatabaseReference mTalksDatabase;
    private FirebaseTalksHandler firebaseTalksHandler;
    private String UsersTalkedKey;
    private String UsersIdsTalkedKey;
    private FirebaseDatabase mTalksRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setTheme(R.style.AppTheme);
        sessionManagement=new SessionManagement(getApplicationContext());
        user=sessionManagement.getUserDetails();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        FirebaseBuyerChatList=new ArrayList<>();
        if (user!=null) {
            UserName= user.get(SessionManagement.KEY_UserName);
            UserID= user.get(SessionManagement.KEY_userID);
        }

        Intent intent=getIntent();
        PivotID = intent.getExtras().getString(PivotID_KEY);
        BookOwnerID = intent.getExtras().getString(BookOwnerID_KEY);
        BookID = intent.getExtras().getString(BookID_KEY);
        Config.BookID=BookID;
        SellerID= intent.getExtras().getString(BookSellerID_KEY);
        Config.SellerID=SellerID;
        BookName= intent.getExtras().getString(BookName_KEY);
        Config.BookName=BookName;
        BookDescription= intent.getExtras().getString(BookDescription_KEY);
        PublishYear= intent.getExtras().getString(PublishYear_KEY);
        AuthorName= intent.getExtras().getString(AuthorName_KEY);
        ISBN= intent.getExtras().getString(ISBN_KEY);
        Price= intent.getExtras().getString(Price_KEY);
        TransactionType= intent.getExtras().getString(Transaction_KEY);
        SellerEmail= intent.getExtras().getString(SellerEmail_KEY);
        FacultyName= intent.getExtras().getString(SellerFacultyName_KEY);
        firebaseChatHandler=new FirebaseChatHandler();
        BuyerDirectSellerMessageingKey=UserID+Buyer+"_"+SellerID+Seller;
        SellerDirectBuyerMessageingKey=SellerID+Seller+"_"+UserID+Buyer;
        BuyerPivotSellerMessagingKey =UserID+Buyer+PivotID+PivotID_KEY+SellerID+Seller;
        SellerPivotBuyerMessagingKey =SellerID+Seller+PivotID+PivotID_KEY+UserID+Buyer;
        /*
        Talks Keys
         */
        UsersTalkedKey="Users";
        UsersIdsTalkedKey=UserID;

        if (mTalksDatabase==null){
            mTalksRef=FirebaseDatabase.getInstance();
            mTalksDatabase=mTalksRef.getReference().child(Talks_KEY);
        }

        if (mMessagesDatabase ==null){
            database= FirebaseDatabase.getInstance();
            mMessagesDatabase =database.getReference().child(Messages_KEY);
            if (Config.Buyer){
//                .child(BuyerPivotSellerMessagingKey).child(UserID+Buyer);
//                mMessagesDatabase =database.getReference();
                mMessagesDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(BuyerDirectSellerMessageingKey).exists()){
                            if (dataSnapshot.child(BuyerPivotSellerMessagingKey).exists()){
                                if (dataSnapshot.child(BuyerPivotSellerMessagingKey).child(UserID+Buyer).exists()){
                                    //do nothing
                                }else {
                                    firebaseChatHandler.setWelcomeMessage(UserName+" started the Conversation");
//                                    mMessagesDatabase.child(BuyerDirectSellerMessageingKey).child(BuyerPivotSellerMessagingKey).child(UserID+Buyer).setValue(firebaseChatHandler);
                                }
                            }else {
                                firebaseChatHandler.setWelcomeMessage(UserName+" started the Conversation");
//                                mMessagesDatabase.child(BuyerDirectSellerMessageingKey).child(BuyerPivotSellerMessagingKey).child(UserID+Buyer).setValue(firebaseChatHandler);
                            }
                        }else {
                            firebaseChatHandler.setWelcomeMessage(UserName+" started the Conversation");
//                            mMessagesDatabase.child(BuyerDirectSellerMessageingKey).child(BuyerPivotSellerMessagingKey).child(UserID+Buyer).setValue(firebaseChatHandler);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }else {
                mMessagesDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(SellerDirectBuyerMessageingKey).exists()){
                            if (dataSnapshot.child(SellerPivotBuyerMessagingKey).exists()){
                                if (dataSnapshot.child(SellerPivotBuyerMessagingKey).child(SellerID+Seller).exists()){
                                    //do nothing
                                }else {
                                    firebaseChatHandler.setWelcomeMessage(UserName+" started the Conversation");
//                                    mMessagesDatabase.child(SellerDirectBuyerMessageingKey).child(SellerPivotBuyerMessagingKey).child(SellerID+Seller).setValue(firebaseChatHandler);
                                }
                            }else {
                                firebaseChatHandler.setWelcomeMessage(UserName+" started the Conversation");
//                                mMessagesDatabase.child(SellerDirectBuyerMessageingKey).child(SellerPivotBuyerMessagingKey).child(SellerID+Seller).setValue(firebaseChatHandler);
                            }
                        }else {
                            firebaseChatHandler.setWelcomeMessage(UserName+" started the Conversation");
//                            mMessagesDatabase.child(SellerDirectBuyerMessageingKey).child(SellerPivotBuyerMessagingKey).child(SellerID+Seller).setValue(firebaseChatHandler);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

//            mDatabase.child(messaging_key).child(buyer_id+"buyer");
//            String key= mDatabase.child(messaging_key).child(buyer_id+"buyer").push().getKey();

            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseStorage = FirebaseStorage.getInstance();

//            mMessagesDatabase.keepSynced(true);
        }


        mEditTextMessage = findViewById(R.id.editTextMessage);
        mButtonSend = findViewById(R.id.buttonSend);

        chatMessages = new ArrayList<FirebaseChatHandler>();
        mEditTextMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mButtonSend.setClickable(true);
                } else {
                    mButtonSend.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mEditTextMessage.setFilters(new InputFilter[] {new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Now= Calendar.getInstance().getTime();
                user=sessionManagement.getUserDetails();
                if (user!=null) {
                    UserName= user.get(SessionManagement.KEY_UserName);
                    UserID= user.get(SessionManagement.KEY_userID);
                }
                if (mEditTextMessage.getText().length()>0){
                    if (UserName!=null){
                        if (UserID!=null){
                            if (Config.SellerID!=null){
                                if (Config.BookID!=null){
                                    if (Config.BookName!=null){
                                        firebaseChatHandler= new FirebaseChatHandler(mEditTextMessage.getText().toString(),Now.toString(), UserName, UserID, Config.SellerID,null, Config.BookID, Config.BookName);
                                        firebaseChatHandler.setWelcomeMessage(null);
                                        firebaseTalksHandler=new FirebaseTalksHandler(SellerID);
                                        firebaseEntities=new FirebaseEntites(mMessagesDatabase);
                                        firebaseEntities=new FirebaseEntites(mTalksDatabase,"");
//                if (mMessagesDatabase!=null){
//                    database= FirebaseDatabase.getInstance();
//                    mMessagesDatabase =database.getReference().child(Messages_KEY).child(BuyerDirectSellerMessageingKey).child(BuyerPivotSellerMessagingKey).child(UserID+Buyer);
//                }else {
//                    database= FirebaseDatabase.getInstance();
//                    mMessagesDatabase =database.getReference().child(Messages_KEY).child(BuyerDirectSellerMessageingKey).child(BuyerPivotSellerMessagingKey).child(UserID+Buyer);
//                }
                                        if (Config.Buyer){
                                            if (mMessagesDatabase!=null){
                                                database= FirebaseDatabase.getInstance();
                                                mMessagesDatabase =database.getReference().child(Messages_KEY).child(BuyerDirectSellerMessageingKey).child(BuyerPivotSellerMessagingKey).child(UserID+Buyer);
                                                currentNodeKey= mMessagesDatabase.getKey();
                                            }else {
                                                database= FirebaseDatabase.getInstance();
                                                mMessagesDatabase =database.getReference().child(Messages_KEY).child(BuyerDirectSellerMessageingKey).child(BuyerPivotSellerMessagingKey).child(UserID+Buyer);
                                                currentNodeKey= mMessagesDatabase.getKey();
                                            }
                                            if (mTalksDatabase!=null){
                                                mTalksRef=FirebaseDatabase.getInstance();
                                                mTalksDatabase=mTalksRef.getReference().child(Talks_KEY).child(UsersTalkedKey).child(UsersIdsTalkedKey);
                                            }else {
                                                mTalksRef=FirebaseDatabase.getInstance();
                                                mTalksDatabase=mTalksRef.getReference().child(Talks_KEY).child(UsersTalkedKey).child(UsersIdsTalkedKey);
                                            }
                                            firebaseEntities.AddMessage(mMessagesDatabase, BuyerDirectSellerMessageingKey,firebaseChatHandler,UserID,SellerID);
                                            firebaseEntities.AddTalk(mTalksDatabase, firebaseTalksHandler);
//                    MessageChangeListener(mMessagesDatabase, BuyerDirectSellerMessageingKey);
                                        }else {
                                            if (mMessagesDatabase!=null){
                                                database= FirebaseDatabase.getInstance();
                                                mMessagesDatabase =database.getReference().child(Messages_KEY).child(SellerDirectBuyerMessageingKey).child(SellerPivotBuyerMessagingKey).child(SellerID+Seller);
                                                currentNodeKey= mMessagesDatabase.getKey();
                                            }else {
                                                database= FirebaseDatabase.getInstance();
                                                mMessagesDatabase =database.getReference().child(Messages_KEY).child(SellerDirectBuyerMessageingKey).child(SellerPivotBuyerMessagingKey).child(SellerID+Seller);
                                                currentNodeKey= mMessagesDatabase.getKey();
                                            }
                                            firebaseEntities.AddMessage(mMessagesDatabase, SellerDirectBuyerMessageingKey,firebaseChatHandler,UserID,SellerID);
//                    MessageChangeListener(mMessagesDatabase, currentNodeKey);
                                        }

                                        mEditTextMessage.setText("");
                                        FetchChatFromFirebase();
                                    }else {
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_bookname), Toast.LENGTH_LONG).show();
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_bookid), Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_sellerid), Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_userid), Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_username), Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.enter_message), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void MessageChangeListener(DatabaseReference mMessagesDatabase, String currentNodeKey_) {
        mMessagesDatabase.addValueEventListener(new ValueEventListener() {
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

    @Override
    protected void onResume() {
        super.onResume();
        verifyConnection=new VerifyConnection(getApplicationContext());
        if (verifyConnection.isConnected()){
            FetchChatFromFirebase();
        }
    }

    public List<FirebaseChatHandler> FetchChatFromFirebase() {
        database= FirebaseDatabase.getInstance();
        mMessagesDatabase =database.getReference().child(Messages_KEY);
        if (Config.Buyer){
            ThoughtsRef=mMessagesDatabase.child(BuyerDirectSellerMessageingKey)
                    .child(BuyerPivotSellerMessagingKey).child(UserID+Buyer);
        }else {
            ThoughtsRef=mMessagesDatabase.child(SellerDirectBuyerMessageingKey)
                    .child(SellerPivotBuyerMessagingKey).child(SellerID+Seller);
        }
        if (Config.Buyer){
            ValueEventListener valueEventListener=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    FirebaseChatHandler firebaseChatHandler=new FirebaseChatHandler();
                    FirebaseBuyerChatList.clear();
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        KEY=ds.getKey();
                        MessageText_STR = ds.child("messageText").getValue(String.class);
                        Now_STR = ds.child("now").getValue(String.class);
                        SellerID= ds.child("sellerID").getValue(String.class);
                        BuyerID= ds.child("buyerID").getValue(String.class);
                        BookID= ds.child("bookID").getValue(String.class);
                        BookName= ds.child("bookName").getValue(String.class);
                        UserName= ds.child("userName").getValue(String.class);
                        Log.d(LOG_TAG, MessageText_STR+ "by:  / " + UserName+ " for / " + BookName+ " with / " + BookID+ " from / " + BuyerID + " to / " + SellerID);
                        firebaseChatHandler=new FirebaseChatHandler(KEY, MessageText_STR, SellerID, BuyerID, BookID, BookName
                                , UserName,Now_STR, "");
                        FirebaseBuyerChatList.add(firebaseChatHandler);
                    }
                    if (FirebaseBuyerChatList.size()>0){
                        PopulateFirebaseList(FirebaseBuyerChatList);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            };
            ThoughtsRef.addListenerForSingleValueEvent(valueEventListener);
        }else{
            ValueEventListener valueEventListener=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    FirebaseChatHandler firebaseChatHandler=new FirebaseChatHandler();
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        KEY=ds.getKey();
                        MessageText_STR = ds.child("messageText").getValue(String.class);
                        Now_STR = ds.child("now").getValue(String.class);
                        SellerID= ds.child("sellerID").getValue(String.class);
                        BuyerID= ds.child("buyerID").getValue(String.class);
                        BookID= ds.child("bookID").getValue(String.class);
                        BookName= ds.child("bookName").getValue(String.class);
                        UserName= ds.child("userName").getValue(String.class);
                        Log.d(LOG_TAG, MessageText_STR+ "by:  / " + UserName+ " for / " + BookName+ " with / " + BookID+ " from / " + BuyerID + " to / " + SellerID);
                        firebaseChatHandler=new FirebaseChatHandler(KEY, MessageText_STR, SellerID, BuyerID, BookID, BookName
                                , UserName,Now_STR,"");
                        FirebaseBuyerChatList.add(firebaseChatHandler);
                    }
                    if (FirebaseBuyerChatList.size()>0){
                        PopulateFirebaseList(FirebaseBuyerChatList);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            };
            ThoughtsRef.addListenerForSingleValueEvent(valueEventListener);
        }

        return FirebaseBuyerChatList;
    }

    private void PopulateFirebaseList(ArrayList<FirebaseChatHandler> firebaseChatList) {
        mMessageAdapter=new MessagesAdapter(getApplicationContext(),firebaseChatList);
        mLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mMessageAdapter.setHasStableIds(true);
        recyclerView.setAdapter(mMessageAdapter);
        mMessageAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.chat_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Get the ID of the clicked item */
        int id = item.getItemId();
        /* Settings menu item clicked */
        if (id == R.id.call_icon) {
            //implement call function
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
