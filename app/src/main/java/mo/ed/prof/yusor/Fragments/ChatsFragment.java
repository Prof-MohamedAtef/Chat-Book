package mo.ed.prof.yusor.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import mo.ed.prof.yusor.Dev.Adapter.ChatAdapter;
import mo.ed.prof.yusor.Dev.Entity.FirebaseChat;
import mo.ed.prof.yusor.Dev.Entity.FirebaseUsers;
import mo.ed.prof.yusor.Dev.Notification.Token;
import mo.ed.prof.yusor.R;

/**
 * Created by Prof-Mohamed Atef on 3/21/2019.
 */

public class ChatsFragment extends Fragment{

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    List<FirebaseUsers> mChat;
    private ChatAdapter chattedUsersAdapter;



    private static final String ARG_SECTION_NUMBER = "section_number";
    private FirebaseUser fUser;
    private ArrayList<String> mChatssList;
    private DatabaseReference reference;
    private CopyOnWriteArrayList<FirebaseUsers> mUsers;
    private List<FirebaseUsers> UniqueChatList;

    public static ChatsFragment newInstance(int sectionNum) {
        ChatsFragment _chatsFragment =new ChatsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNum);
        _chatsFragment.setArguments(args);
        return _chatsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_user_chatted, container, false);
        recyclerView=(RecyclerView)mainView.findViewById(R.id.recycler_view);
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
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateToken(FirebaseInstanceId.getInstance().getToken());
        return mainView;

    }

    private void readChats() {
        mUsers=new CopyOnWriteArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("yusor-chat").child("Users");
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
        chattedUsersAdapter =new ChatAdapter(getActivity(),removeDublicates(mUsers), true);
        chattedUsersAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        chattedUsersAdapter.setHasStableIds(true);
        recyclerView.setAdapter(chattedUsersAdapter);
    }

    private List<FirebaseUsers> removeDublicates(CopyOnWriteArrayList<FirebaseUsers> chattingList) {
        HashSet hashSet=new HashSet();
        hashSet.addAll(chattingList);
        chattingList.clear();
        chattingList.addAll(hashSet);
        return  chattingList;
    }

    private void updateToken(String token){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1=new Token(token);
        reference.child(fUser.getUid()).setValue(token1);

    }
}
