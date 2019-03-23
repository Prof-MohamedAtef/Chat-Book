package mo.ed.prof.yusor.Dev.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mo.ed.prof.yusor.Activities.Authentication.TaibahRegistrationActivity;
import mo.ed.prof.yusor.Dev.Entity.FirebaseChat;
import mo.ed.prof.yusor.Dev.Entity.FirebaseUsers;
import mo.ed.prof.yusor.Dev.MessageActivity;
import mo.ed.prof.yusor.R;

/**
 * Created by Prof-Mohamed Atef on 3/22/2019.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHOlder> implements Serializable {

    private final String LOG_TAG = ChatAdapter.class.getSimpleName();
    private final boolean isChat;
    Context mContext;
    List<FirebaseUsers> feedItemList;
    public static String Default_KEY="default";
    String mLastMessage;

    public ChatAdapter(Context mContext, List<FirebaseUsers> feedItemList, boolean is_chat) {
        this.mContext = mContext;
        this.feedItemList = feedItemList;
        this.isChat=is_chat;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, null);
        RecyclerView.ViewHolder viewHolder = new ChatAdapter.ViewHOlder(view);
        return (ChatAdapter.ViewHOlder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatAdapter.ViewHOlder holder, final int position) {
        final FirebaseUsers feedItem = feedItemList.get(position);
        if (feedItem != null) {
            if (feedItem.getUserName()!=null){
                holder.UserName.setText(feedItem.getUserName());
                if (feedItem.getImageUrl().equals(Default_KEY)){
                    holder.ProfileImage.setImageResource(R.drawable.logo);
                }else if (feedItem.getImageUrl()!=null){
                    Picasso.with(mContext).load(feedItem.getImageUrl())
                            .error(R.drawable.logo)
                            .into(holder.ProfileImage);
                }
                /*if (feedItem.getApproved().equals("none")){
                    holder.btn_approve.setVisibility(View.VISIBLE);
                }else {
                    holder.img_approved.setVisibility(View.VISIBLE);
                    Picasso.with(mContext).load(R.drawable.ic_action_approved)
                            .error(R.drawable.logo)
                            .into(holder.img_approved);
                    holder.btn_approve.setVisibility(View.GONE);
                    holder.Done.setVisibility(View.VISIBLE);
                }*/
            }else{
                holder.UserName.setText("");
            }
            /*holder.btn_approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //send approval to server
                    // if sent to mysql successfully {
                    // push "approved" to approved node on firebase yusor-chat/Users childs
                    // if (done){
                    Approval("approved", feedItem.getID());
                    holder.img_approved.setVisibility(View.VISIBLE);
                    holder.Done.setVisibility(View.VISIBLE);
                    Picasso.with(mContext).load(R.drawable.ic_action_approved)
                            .error(R.drawable.logo)
                            .into(holder.img_approved);
                    holder.btn_approve.setVisibility(View.GONE);
                    //}
                    // }
                }
            });*/

            holder.RelativeUserContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, MessageActivity.class);
                    intent.putExtra("userid",feedItem.getID());
                    mContext.startActivity(intent);
                }
            });

            if (isChat){
                lastMessage(feedItem.getID(), holder.last_msg);
            }else {
                holder.last_msg.setVisibility(View.GONE);
            }

            if (isChat){
                if (feedItem.getStatus().equals("online")){
                    holder.img_on.setVisibility(View.VISIBLE);
                    holder.img_off.setVisibility(View.GONE);
                }else {
                    holder.img_on.setVisibility(View.GONE);
                    holder.img_off.setVisibility(View.VISIBLE);
                }
            }else {
                holder.img_on.setVisibility(View.GONE);
                holder.img_off.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        int size=0;
        if (feedItemList!=null){
            size=(null != feedItemList ? feedItemList.size() : 0);
        }
        return size;
    }

    class ViewHOlder extends RecyclerView.ViewHolder {

        protected TextView last_msg;
        private final CircleImageView img_off;
        private final CircleImageView img_on;
        protected RelativeLayout RelativeUserContainer;
        protected CircleImageView ProfileImage;
        protected CircleImageView img_approved;
        protected TextView UserName;
        protected TextView Done;
        protected Button btn_approve;


        public ViewHOlder(View converview) {
            super(converview);
            this.UserName= (TextView) converview.findViewById(R.id.UserName);
            this.Done= (TextView) converview.findViewById(R.id.txt_done);
            this.ProfileImage=(CircleImageView)converview.findViewById(R.id.profile_image);
            this.RelativeUserContainer=(RelativeLayout)converview.findViewById(R.id.user_container);
            this.img_off=(CircleImageView)converview.findViewById(R.id.img_off);
            this.img_on=(CircleImageView)converview.findViewById(R.id.img_on);
            this.img_approved=(CircleImageView)converview.findViewById(R.id.img_approved);
            this.last_msg= (TextView) converview.findViewById(R.id.last_msg);
            this.btn_approve=(Button)converview.findViewById(R.id.btn_approve);
        }
    }

    private void Approval(String status, String anotherUID){
        try {
            FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("yusor-chat").child("Users").child(anotherUID);
            HashMap<String, Object> hashMap=new HashMap<>();
            hashMap.put("approved", status);
            reference.updateChildren(hashMap);
        }catch (Exception e){
            Log.e(LOG_TAG, "Error ******** Error in Firebase Approval Method: ");
        }
    }

    private void lastMessage(final String userID, final TextView last_msg){
        mLastMessage="default";
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("yusor-chat").child("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    FirebaseChat chat=snapshot.getValue(FirebaseChat.class);
                    if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userID) ||
                            chat.getReceiver().equals(userID) && chat.getSender().equals(firebaseUser.getUid())){
                        mLastMessage=chat.getMessage();
                    }
                }

                switch (mLastMessage) {
                    case "default":
                        last_msg.setText("No Message");
                        break;
                    default:
                        last_msg.setText(mLastMessage);
                        break;
                }
                mLastMessage="default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}