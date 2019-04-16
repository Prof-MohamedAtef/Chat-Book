package mo.ed.prof.yusor.Activities.BillApprove;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.GenerateProcessButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Adapter.Chat.ChatterSpinnerAdapter;
import mo.ed.prof.yusor.Dev.Entity.FirebaseChat;
import mo.ed.prof.yusor.Dev.Entity.FirebaseUsers;
import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Config;
import mo.ed.prof.yusor.helpers.Designsers.ProgressGenerator;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;
import mo.ed.prof.yusor.helpers.SessionManagement;

public class BillsActivity extends AppCompatActivity implements ProgressGenerator.OnProgressCompleteListener {

    private SessionManagement sessionManagement;
    private HashMap<String, String> user;
    private String LoggedEmail;
    private String LoggedUserName;
    private String LoggedProfilePic;
    private String loggedFirebaseUserID;

    @BindView(R.id.chat_spinner)
    Spinner ChatSpinner;

    @BindView(R.id.txt_seller_name)
    TextView txt_seller_name;

    @BindView(R.id.txt_date_time)
    TextView txt_date_time;

    @BindView(R.id.txt_book_name)
    TextView txt_book_name;

    @BindView(R.id.txt_price)
    TextView txt_price;

    @BindView(R.id.create_bill)
    GenerateProcessButton createBill_btn;

    private Intent intent;
    private StudentsEntity studentsEntity;
    private String SellerUserName;
    private Date Now;
    private VerifyConnection verifyConn;
    private ProgressGenerator progressGenerator;
    private String BookSellerFBUi;
    private String Book_Name;
    private String Book_Price;
    private String Book_id;
    private String ApiToken;
    private VerifyConnection verifyConnection;
    private DatabaseReference reference;
    private ArrayList<String> mChatssList;
    private FirebaseUser fUser;
    private String buyerID;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        ButterKnife.bind(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (Locale.getDefault().getLanguage().contentEquals("en")){
            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_back_en));
        }else if (Locale.getDefault().getLanguage().contentEquals("ar")){
            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_back_ar));
        }
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sessionManagement=new SessionManagement(getApplicationContext());
        user=sessionManagement.getUserDetails();
        if (user!=null) {
            LoggedEmail = user.get(SessionManagement.KEY_EMAIL);
            LoggedUserName = user.get(SessionManagement.KEY_NAME);
            LoggedProfilePic = user.get(SessionManagement.KEY_Profile_Pic);
            loggedFirebaseUserID = user.get(SessionManagement.firebase_UID_KEY);
            ApiToken=user.get(SessionManagement.KEY_idToken);
            if (LoggedUserName != null) {
                txt_seller_name.setText(LoggedUserName);
            }
            if (LoggedProfilePic != null) {
//                Picasso.with(getApplicationContext()).load(LoggedProfilePic)
//                        .error(R.drawable.web_hi_res_512)
//                        .into(ProfilePicView);
            }

            intent=getIntent();
            if (intent!=null) {
                studentsEntity = (StudentsEntity) intent.getExtras().getSerializable("feedItem");
                Config.studentEntity=studentsEntity;
                inOnCreate(Config.studentEntity);
            }else if (savedInstanceState!=null){
                studentsEntity= (StudentsEntity) savedInstanceState.getSerializable("studentEntity");
                Config.studentEntity=studentsEntity;
                inOnCreate(Config.studentEntity);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (Config.studentEntity!=null){
            outState.putSerializable("studentEntity", Config.studentEntity);
        }
    }

    private void inOnCreate(StudentsEntity studentEntity) {
        SellerUserName = studentsEntity.getSellerUserName();
        txt_seller_name.setText(SellerUserName);
        Now = Calendar.getInstance().getTime();
        txt_date_time.setText(Now.toString());
        Book_Name = studentsEntity.getBookTitle();
        Book_Price = studentsEntity.getBookPrice();
        txt_book_name.setText(Book_Name);
        txt_price.setText(Book_Price);
        BookSellerFBUi = studentsEntity.getFirebaseUiD();
        Book_id = studentsEntity.getBookID();
        createBill_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyConn = new VerifyConnection(getApplicationContext());
                if (verifyConn.isConnected()) {
                    // i am seller
                    if (Config.billSpinbuyerID != null) {
                        Book_Price = studentsEntity.getBookPrice();
                        Book_id = studentsEntity.getBookID();
                        ApiToken = user.get(SessionManagement.KEY_idToken);
                        progressGenerator = new ProgressGenerator((ProgressGenerator.OnProgressCompleteListener) BillsActivity.this, getApplicationContext());
                        progressGenerator.addBill(createBill_btn, Config.billSpinbuyerID, Book_Price, "1", "0", Book_id, ApiToken);
                    }
                }else {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.cannot_start_chat), Toast.LENGTH_LONG).show();
                }
            }
        });
        verifyConnection = new VerifyConnection(getApplicationContext());
        if (verifyConnection.isConnected()) {
            readChats();
        }

        ChatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Config.billSpinbuyerID = Config.chattingList.get(position).getID();
                Config.billSpinBuyPos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onProgressComplete(ArrayList<StudentsEntity> studentsEntities) {
        //redirect to bills
        if (studentsEntities!=null){
            if (studentsEntities.size()>0){
                for (StudentsEntity studentsEntity:studentsEntities){
                    if (studentsEntity.getException()!=null){
                        Toast.makeText(getApplicationContext(), studentsEntity.getException().toString(), Toast.LENGTH_LONG).show();
                    }else {
                        DisplayBills(studentsEntity);
                    }
                }
            }
        }

    }

    private void DisplayBills(StudentsEntity studentsEntity) {
        if (studentsEntity!=null){
            intent =new Intent(getApplicationContext(),DisplayBillActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle=new Bundle();
            bundle.putSerializable("feedItem",studentsEntity);
            intent.putExtras(bundle);
            getApplicationContext().startActivity(intent);
            finish();
        }
    }

    private CopyOnWriteArrayList<FirebaseUsers> mUsers;

    private void readChats() {
        mChatssList =new ArrayList<>();
        fUser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("yusor-chat").child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChatssList.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    FirebaseChat chat=snapshot.getValue(FirebaseChat.class);
                    String senderID=chat.getSender();
                    String currentUID=fUser.getUid();
                    String receiverID=chat.getReceiver().toString();
                    if (senderID.equals(currentUID)){
                        mChatssList.add(receiverID);
                    }
                    if (receiverID.equals(currentUID)){
                        mChatssList.add(senderID.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mUsers=new CopyOnWriteArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("yusor-chat").child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    FirebaseUsers userServer=snapshot.getValue(FirebaseUsers.class);
                    for (String id: mChatssList){
                        String ID= userServer.getID();
                        if (ID.equals(id)){
                            if (mUsers!=null){
                                if (mUsers.size()!=0){
                                    for (FirebaseUsers fUser:mUsers){
                                        String id_=fUser.getID();
                                        String ID_= userServer.getID();
                                        if (!ID_.equals(id_)){
                                            mUsers.add(userServer);
                                        }
                                    }
                                }else {
                                    mUsers.add(userServer);
                                }
                            }
                        }
                    }
                }
                PopulateUsersList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void PopulateUsersList() {
        ChatterSpinnerAdapter chatterSpinnerAdapter= new ChatterSpinnerAdapter(getApplicationContext(), removeDublicates(mUsers));
        ChatSpinner.setAdapter(chatterSpinnerAdapter);
        ChatSpinner.setSelection(0);
        FirebaseUsers user= removeDublicates(mUsers).get(ChatSpinner.getSelectedItemPosition());
        buyerID= user.getID();
        Config.billSpinbuyerID =buyerID;
        Config.billSpinBuyPos=ChatSpinner.getSelectedItemPosition();
    }

    private List<FirebaseUsers> removeDublicates(CopyOnWriteArrayList<FirebaseUsers> chattingList) {
        HashSet hashSet=new HashSet();
        hashSet.addAll(chattingList);
        chattingList.clear();
        chattingList.addAll(hashSet);
        Config.chattingList= chattingList;
        return  chattingList;
    }
}