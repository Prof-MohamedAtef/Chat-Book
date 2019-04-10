package mo.ed.prof.yusor.Dev.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mo.ed.prof.yusor.Dev.Entity.FirebaseChat;
import mo.ed.prof.yusor.Dev.MessageActivity;
import mo.ed.prof.yusor.Dev.Entity.FirebaseUsers;
import mo.ed.prof.yusor.R;

/**
 * Created by Prof-Mohamed Atef on 3/21/2019.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHOlder> implements Serializable {

    Context mContext;
    List<FirebaseUsers> feedItemList;
    public static String Default_KEY="default";
    private boolean isChat;
    String mLastMessage;

    public UserAdapter(Context mContext, List<FirebaseUsers> feedItemList, boolean is_chat) {
        this.mContext = mContext;
        this.feedItemList = feedItemList;
        this.isChat=is_chat;

    }

    @NonNull
    @Override
    public ViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, null);
        RecyclerView.ViewHolder viewHolder = new ViewHOlder(view);
        return (ViewHOlder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHOlder holder, final int position) {
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
            }else{
                holder.UserName.setText("");
            }
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

        protected RelativeLayout RelativeUserContainer;
        protected CircleImageView ProfileImage;
        protected TextView UserName;
        protected CircleImageView img_off;
        protected CircleImageView img_on;
        protected TextView last_msg;


        public ViewHOlder(View converview) {
            super(converview);
            this.UserName= (TextView) converview.findViewById(R.id.UserName);
            this.last_msg= (TextView) converview.findViewById(R.id.last_msg);
            this.ProfileImage=(CircleImageView)converview.findViewById(R.id.profile_image);
            this.RelativeUserContainer=(RelativeLayout)converview.findViewById(R.id.user_container);
            this.img_off=(CircleImageView)converview.findViewById(R.id.img_off);
            this.img_on=(CircleImageView)converview.findViewById(R.id.img_on);
        }
    }

    private void lastMessage(final String loopedUserID, final TextView last_msg){
        mLastMessage="default";
        final FirebaseUser firebaseLoggedinUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("yusor-chat").child("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    FirebaseChat chat=snapshot.getValue(FirebaseChat.class);
                    if (chat.getReceiver().equals(firebaseLoggedinUser.getUid()) && chat.getSender().equals(loopedUserID) ||
                            chat.getReceiver().equals(loopedUserID) && chat.getSender().equals(firebaseLoggedinUser.getUid())){
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