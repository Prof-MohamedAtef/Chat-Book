package mo.ed.prof.yusor.Dev.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mo.ed.prof.yusor.Dev.Entity.FirebaseChat;
import mo.ed.prof.yusor.R;

/**
 * Created by Prof-Mohamed Atef on 3/21/2019.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHOlder> implements Serializable {

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    Context mContext;
    List<FirebaseChat> feedItemList;
    private String imgUrl;
    FirebaseUser firebaseUser;

    public static String Default_KEY="default";

    public MessageAdapter(Context mContext, List<FirebaseChat> feedItemList, String imgUrl) {
        this.mContext = mContext;
        this.feedItemList = feedItemList;
        this.imgUrl=imgUrl;

    }

    @NonNull
    @Override
    public MessageAdapter.ViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, null);
            RecyclerView.ViewHolder viewHolder = new MessageAdapter.ViewHOlder(view);
            return (MessageAdapter.ViewHOlder) viewHolder;
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, null);
            RecyclerView.ViewHolder viewHolder = new MessageAdapter.ViewHOlder(view);
            return (MessageAdapter.ViewHOlder) viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageAdapter.ViewHOlder holder, final int position) {
        final FirebaseChat feedItem = feedItemList.get(position);
        if (feedItem!=null){
            if (feedItem.getMessage()!=null){
                holder.show_message.setText(feedItem.getMessage());
                if (imgUrl.equals("default")){
                    holder.ProfileImage.setImageResource(R.drawable.logo);
                }else {
                    Picasso.with(mContext).load(imgUrl)
                            .error(R.drawable.logo)
                            .into(holder.ProfileImage);
                }
            }else {
                holder.show_message.setText("");
            }
            if (position==feedItemList.size()-1){
                if (feedItem.isIsseen()){
                    holder.text_seen.setText("Seen");
                }else {
                    holder.text_seen.setText("Delivered");
                }
            }else {
                holder.text_seen.setVisibility(View.GONE);
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

    @Override
    public int getItemViewType(int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if (feedItemList.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else
        {
            return MSG_TYPE_LEFT;
        }
    }

    class ViewHOlder extends RecyclerView.ViewHolder {

        protected RelativeLayout RelativeUserContainer;
        protected CircleImageView ProfileImage;
        protected TextView show_message;
        public TextView text_seen;
//        protected Button btn_approve;
//        protected CircleImageView img_approved;

        public ViewHOlder(View converview) {
            super(converview);
//            this.img_approved=(CircleImageView)converview.findViewById(R.id.img_approved);
            this.show_message = (TextView) converview.findViewById(R.id.show_message);
            this.text_seen= (TextView) converview.findViewById(R.id.text_seen);
            this.ProfileImage=(CircleImageView)converview.findViewById(R.id.profile_image);
        }
    }
}