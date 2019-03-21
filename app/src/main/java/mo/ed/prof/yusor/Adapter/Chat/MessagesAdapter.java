package mo.ed.prof.yusor.Adapter.Chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.io.Serializable;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Firebase.ChatHandler.FirebaseChatHandler;

/**
 * Created by Prof-Mohamed Atef on 3/20/2019.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHOlder> implements Serializable {

    Context mContext;
    ArrayList<FirebaseChatHandler> feedItemList;

    public MessagesAdapter(Context mContext, ArrayList<FirebaseChatHandler> feedItemList) {
        this.mContext = mContext;
        this.feedItemList = feedItemList;
    }

    @NonNull
    @Override
    public ViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, null);
        RecyclerView.ViewHolder viewHolder = new ViewHOlder(view);
        return (ViewHOlder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHOlder holder, final int position) {
        final FirebaseChatHandler feedItem = feedItemList.get(position);
        if (feedItem!=null){
            if (feedItem.getUserName()!=null){
                holder.MessengerUserName.setText(feedItem.getUserName());
                if (feedItem.getMessageText()!=null){
                    holder.MessageText.setText(feedItem.getMessageText());
                    if (feedItem.getNow()!=null){
                        holder.TimeText.setText(feedItem.getNow());
                        if (feedItem.getPhotoURL()!=null) {
                            Picasso.with(mContext).load(feedItem.getPhotoURL())
                                    .error(R.drawable.logo)
                                    .into(holder.Image);
                        }
                    }else {
                        holder.TimeText.setText("");
                    }
                }else {
                    holder.MessageText.setText("");
                }
            }else {
                holder.MessengerUserName.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class ViewHOlder extends RecyclerView.ViewHolder {
        protected TextView TimeText;
        protected TextView MessengerUserName;
        protected TextView MessageText;
        protected CircleImageView Image;

        public ViewHOlder(View converview) {
            super(converview);
            this.MessengerUserName = (TextView) converview.findViewById(R.id.messenger_username);
            this.MessageText = (TextView) converview.findViewById(R.id.message_text);
            this.TimeText = (TextView) converview.findViewById(R.id.time_text);
            this.Image =(CircleImageView)converview.findViewById(R.id.ProfileImage_edit);
        }
    }
}