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

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
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

    public UserAdapter(Context mContext, List<FirebaseUsers> feedItemList) {
        this.mContext = mContext;
        this.feedItemList = feedItemList;

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


        public ViewHOlder(View converview) {
            super(converview);
            this.UserName= (TextView) converview.findViewById(R.id.UserName);
            this.ProfileImage=(CircleImageView)converview.findViewById(R.id.profile_image);
            this.RelativeUserContainer=(RelativeLayout)converview.findViewById(R.id.user_container);
        }
    }
}