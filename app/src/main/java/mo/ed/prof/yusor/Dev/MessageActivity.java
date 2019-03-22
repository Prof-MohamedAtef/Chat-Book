package mo.ed.prof.yusor.Dev;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import mo.ed.prof.yusor.Adapter.Chat.MessagesAdapter;
import mo.ed.prof.yusor.Dev.Adapter.MessageAdapter;
import mo.ed.prof.yusor.Dev.Entity.FirebaseChat;
import mo.ed.prof.yusor.Dev.Entity.FirebaseUsers;
import mo.ed.prof.yusor.R;

public class MessageActivity extends AppCompatActivity {

    @BindView(R.id.profile_picture)
    CircleImageView profileImage;

    @BindView(R.id.UserName)
    TextView userName;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    Intent intent;

    @BindView(R.id.btn_send)
    ImageButton btnSend;

    @BindView(R.id.text_send)
    EditText sendEditText;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    MessageAdapter mMessagesAdapter;
    List<FirebaseChat> firebaseChatList;

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
                finish();
            }
        });

        intent=getIntent();
        final String userID=intent.getStringExtra("userid");
        reference= FirebaseDatabase.getInstance().getReference("yusor-chat").child("Users").child(userID);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=sendEditText.getText().toString();
                if (!message.equals("")){
                    sendMessage(firebaseUser.getUid(),userID,message);
                }else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.enter_message), Toast.LENGTH_LONG).show();
                }
            }
        });




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
                readMessages(user.getID(),userID,user.getImageUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("yusor-chat").child("Chats").push().setValue(hashMap);
        sendEditText.setText("");
    }

    private void readMessages(final String myID, final String userID, final String imageurl){
        firebaseChatList=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("yusor-chat").child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                firebaseChatList.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    FirebaseChat chat=snapshot.getValue(FirebaseChat.class);
                    if (chat.getReceiver().equals(myID)&&chat.getSender().equals(userID)||
                    chat.getReceiver().equals(userID)&&chat.getSender().equals(myID)){
                        firebaseChatList.add(chat);
                    }
                    mMessagesAdapter=new MessageAdapter(MessageActivity.this, firebaseChatList, imageurl);
                    recyclerView.setAdapter(mMessagesAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
