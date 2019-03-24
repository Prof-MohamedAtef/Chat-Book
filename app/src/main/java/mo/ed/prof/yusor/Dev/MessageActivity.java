package mo.ed.prof.yusor.Dev;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import mo.ed.prof.yusor.Activities.Chat.ChatHistoryActivity;
import mo.ed.prof.yusor.Dev.Adapter.MessageAdapter;
import mo.ed.prof.yusor.Dev.Entity.FirebaseApproval;
import mo.ed.prof.yusor.Dev.Entity.FirebaseChat;
import mo.ed.prof.yusor.Dev.Entity.FirebaseUsers;
import mo.ed.prof.yusor.Dev.Notification.Client;
import mo.ed.prof.yusor.Dev.Notification.Data;
import mo.ed.prof.yusor.Dev.Notification.MyResponse;
import mo.ed.prof.yusor.Dev.Notification.Sender;
import mo.ed.prof.yusor.Dev.Notification.Token;
import mo.ed.prof.yusor.Listeners.APIService;
import mo.ed.prof.yusor.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    private final String LOG_TAG = MessageActivity.class.getSimpleName();

    @BindView(R.id.btn_approve)
    Button btn_approve;

    @BindView(R.id.img_approved)
    CircleImageView img_approved;

    @BindView(R.id.txt_done)
    TextView txt_done;

    @BindView(R.id.profile_picture)
    CircleImageView profileImage;

    @BindView(R.id.UserName)
    TextView userName;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    Intent intent;
    ValueEventListener seenListener;

    @BindView(R.id.btn_send)
    ImageButton btnSend;

    @BindView(R.id.text_send)
    EditText sendEditText;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    MessageAdapter mMessagesAdapter;
    List<FirebaseChat> firebaseChatList;
    private LinearLayoutManager mLayoutManager;
    private String calledPersonID;
    private String userID;

    APIService apiService;

    boolean notify=false;
    private AbstractList<FirebaseApproval> firebaseApprovalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);
        ButterKnife.bind(this);
        android.support.v7.widget.Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, ChatHistoryActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        intent=getIntent();
        if (intent!=null) {
            userID = intent.getStringExtra("userid");
        }

        apiService= Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify=true;
                String message=sendEditText.getText().toString();
                if (!message.equals("")){
                    sendMessage(firebaseUser.getUid(),userID,message);
                }else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.enter_message), Toast.LENGTH_LONG).show();
                }
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("yusor-chat").child("Users").child(userID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseUsers user=dataSnapshot.getValue(FirebaseUsers.class);
                if (user!=null){
                    userName.setText(user.getUserName());
                    if (user.getImageUrl().equals("default")){
                        profileImage.setImageResource(R.drawable.logo);
                    }else {
                        Picasso.with(getApplicationContext()).load(user.getImageUrl())
                                .error(R.drawable.logo)
                                .into(profileImage);
                    }
                }
                readMessages(firebaseUser.getUid(),userID,user.getImageUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        seenMessage(firebaseUser.getUid(), userID);
//        verifyBookApproval(firebaseUser.getUid(),userID);
    }


//    private void Approval(String myID){
//        try {
//            FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("yusor-chat").child("Chats").child(myID);
//            HashMap<String, Object> hashMap=new HashMap<>();
//            hashMap.put("approved", status);
//            reference.updateChildren(hashMap);
//        }catch (Exception e){
//            Log.e(LOG_TAG, "Error ******** Error in Firebase Approval Method: ");
//        }
//    }

    private void setApprovalStatus(boolean senderApproval, final String myID, final String calledPersonID) {
        if (!senderApproval){
            img_approved.setVisibility(View.GONE);
            txt_done.setVisibility(View.GONE);
            Picasso.with(getApplicationContext()).load(R.drawable.ic_action_approved)
                    .error(R.drawable.logo)
                    .into(img_approved);
            btn_approve.setVisibility(View.VISIBLE);

            btn_approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //send approval to server
                    // if sent to mysql successfully {
                    // push "approved" to approved node on firebase yusor-chat/Users childs
                    // if (done){
//                    setTrueApproval(myID,calledPersonID);
//                    createBookApproval(firebaseUser.getUid(),userID);
                }
            });
        }else {
            img_approved.setVisibility(View.VISIBLE);
            txt_done.setVisibility(View.VISIBLE);
            Picasso.with(getApplicationContext()).load(R.drawable.ic_action_approved)
                    .error(R.drawable.logo)
                    .into(img_approved);
            btn_approve.setVisibility(View.GONE);
        }
    }

    private void setTrueApproval(final String myID, final String calledPersonID) {
        reference=FirebaseDatabase.getInstance().getReference("yusor-chat").child("Chats");
        seenListener=reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    FirebaseChat chat=snapshot.getValue(FirebaseChat.class);
                    if (chat.getReceiver().equals(calledPersonID) && chat.getSender().equals(myID)){
                        HashMap<String, Object> hashMap=new HashMap<>();
                        hashMap.put("sender_approve", true);
                        snapshot.getRef().updateChildren(hashMap);
                        img_approved.setVisibility(View.VISIBLE);
                        txt_done.setVisibility(View.VISIBLE);
                        Picasso.with(getApplicationContext()).load(R.drawable.ic_action_approved)
                                .error(R.drawable.logo)
                                .into(img_approved);
                        btn_approve.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void createBookApproval(final String myID, final String calledPersonID){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put("sender",myID);
        hashMap.put("receiver", calledPersonID);
        hashMap.put("senderApprove", false);
        hashMap.put("receiverApprove", false);
        reference.child("yusor-chat").child("Book-Conversation").push().setValue(hashMap);
    }

    private void seenMessage(final String myID, final String calledPersonID){
        reference=FirebaseDatabase.getInstance().getReference("yusor-chat").child("Chats");
        seenListener=reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    FirebaseChat chat=snapshot.getValue(FirebaseChat.class);
                    if (chat.getReceiver().equals(myID) && chat.getSender().equals(calledPersonID)){
                        HashMap<String, Object> hashMap=new HashMap<>();
                        hashMap.put("isseen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void verifyBookApproval(final String myID, final String calledPersonID){
        firebaseApprovalList=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("yusor-chat").child("Book-Conversation");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                firebaseApprovalList.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    FirebaseApproval chat=snapshot.getValue(FirebaseApproval.class);
                    if (chat.getReceiver().equals(myID)&&chat.getSender().equals(calledPersonID)||
                            chat.getReceiver().equals(calledPersonID)&&chat.getSender().equals(myID)){
                        firebaseApprovalList.add(chat);
                    }
                }
                boolean senderApprove=false;
                for (FirebaseApproval approval:firebaseApprovalList){
                    senderApprove= approval.isSenderApprove();
                }

//                setApprovalStatus(senderApprove, myID, calledPersonID);

                createBookApproval(firebaseUser.getUid(),userID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //receiverApprove
    private void receiverApprove(final String myID, final String calledPersonID){
        reference=FirebaseDatabase.getInstance().getReference("yusor-chat").child("Book-Conversation");
        seenListener=reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    FirebaseChat chat=snapshot.getValue(FirebaseChat.class);
                    if (chat.getReceiver().equals(myID) && chat.getSender().equals(calledPersonID)){
                        HashMap<String, Object> hashMap=new HashMap<>();
                        hashMap.put("receiverApprove", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void senderApprove(final String myID, final String calledPersonID){
        reference=FirebaseDatabase.getInstance().getReference("yusor-chat").child("Book-Conversation");
        seenListener=reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    FirebaseChat chat=snapshot.getValue(FirebaseChat.class);
                    if (chat.getReceiver().equals(myID) && chat.getSender().equals(calledPersonID)){
                        HashMap<String, Object> hashMap=new HashMap<>();
                        hashMap.put("senderApprove", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void sendMessage(String sender, final String receiver, String message){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);
//        hashMap.put("receiver_approve", false);
//        hashMap.put("sender_approve", false);

        reference.child("yusor-chat").child("Chats").push().setValue(hashMap);
        sendEditText.setText("");


        final String msg=message;

        reference=FirebaseDatabase.getInstance().getReference("yusor-chat").child("Users").child(sender);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseUsers user=dataSnapshot.getValue(FirebaseUsers.class);
                if (notify){
                    sendNotification(receiver, user.getUserName(),msg);
                }
                notify=false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendNotification(String receiver, final String userName, final String msg) {
        DatabaseReference tokens=FirebaseDatabase.getInstance().getReference("Tokens");

        Query query=tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Token token=snapshot.getValue(Token.class);
                    Data data=new Data(firebaseUser.getUid(), String.valueOf(R.drawable.logo), userName+": "+msg,"New Message",
                            userID);
                    Sender sender=new Sender(data, token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code()==200){
                                        if (response.body().success!=1){
                                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMessages(final String myID, final String calledPersonID, final String imageurl){
        firebaseChatList=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("yusor-chat").child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                firebaseChatList.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    FirebaseChat chat=snapshot.getValue(FirebaseChat.class);
                    if (chat.getReceiver().equals(myID)&&chat.getSender().equals(calledPersonID)||
                    chat.getReceiver().equals(calledPersonID)&&chat.getSender().equals(myID)){
                        firebaseChatList.add(chat);
                    }
                }
                PopulateChat(firebaseChatList, imageurl);
//                boolean senderApprove=false;
//                for (FirebaseChat chat:firebaseChatList){
//                    senderApprove= chat.getSender_approve();
//                }
//                setApprovalStatus(senderApprove, myID, calledPersonID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void PopulateChat(List<FirebaseChat> chat, String imageurl) {
        mMessagesAdapter=new MessageAdapter(MessageActivity.this, chat, imageurl);
        recyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mMessagesAdapter.setHasStableIds(true);
        recyclerView.setAdapter(mMessagesAdapter);
    }

    private void currentUser(String userID){
        SharedPreferences.Editor editor=getSharedPreferences("PREFS", MODE_PRIVATE).edit();
        editor.putString("currentuser", userID);
        editor.apply();
    }

    private void Status(String status){
        reference=FirebaseDatabase.getInstance().getReference("yusor-chat").child("Users").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put("status", status);

        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Status("online");
        currentUser(userID);
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(seenListener);
        Status("offline");
        currentUser("none");
    }
}