package mo.ed.prof.yusor.Adapter.Chat;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import mo.ed.prof.yusor.Activities.ChatActivity;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Firebase.ChatHandler.FirebaseChatHandler;

/**
 * Created by Prof-Mohamed Atef on 3/19/2019.
 */

public class MessageAdapter extends ArrayAdapter<FirebaseChatHandler>{

    Context mContext;
    List<FirebaseChatHandler> chatMessages;
    public MessageAdapter(Context context, int item_message, List<FirebaseChatHandler> chatMessages) {
        super(context, item_message, chatMessages);
        this.mContext=context;
        this.chatMessages=chatMessages;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        ImageView mImageViewPhoto = convertView.findViewById(R.id.image_view_photo);
        TextView mTextViewMessage = convertView.findViewById(R.id.text_view_message);
        TextView mTextViewName = convertView.findViewById(R.id.text_view_name);

        if (chatMessages.size()>0){
            FirebaseChatHandler message = getItem(position);

            if (message!=null){

                boolean isPhoto = message.getPhotoURL() != null;
                if (isPhoto) {
                    mTextViewMessage.setVisibility(View.GONE);
                    mImageViewPhoto.setVisibility(View.VISIBLE);
//            Picasso.with(mContext).load(message.getBookImage())
//                    .error(R.drawable.logo)
//                    .into(holder.Image);
                } else {
                    mTextViewMessage.setVisibility(View.VISIBLE);
                    mImageViewPhoto.setVisibility(View.GONE);
                    mTextViewMessage.setText(message.getMessageText());
                }
                mTextViewName.setText(message.getUserName());
            }
        }
        return convertView;
    }
}
