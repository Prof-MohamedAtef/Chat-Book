package mo.ed.prof.yusor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import mo.ed.prof.yusor.Activities.ChatActivity;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.OptionsEntity;

/**
 * Created by Prof-Mohamed Atef on 2/12/2019.
 */

public class BooksGalleryAdapter extends RecyclerView.Adapter<BooksGalleryAdapter.ViewHOlder> implements Serializable {

    Context mContext;
    ArrayList<OptionsEntity> feedItemList;
    boolean TwoPane;
    public static String BookOwnerID_KEY="BookOwnerID_KEY";

    public BooksGalleryAdapter(Context mContext, ArrayList<OptionsEntity> feedItemList, boolean twoPane) {
        this.mContext = mContext;
        this.feedItemList = feedItemList;
        TwoPane = twoPane;
    }

    @NonNull
    @Override
    public BooksGalleryAdapter.ViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books_gallery_list_item, null);
        RecyclerView.ViewHolder viewHolder = new BooksGalleryAdapter.ViewHOlder(view);
        return (BooksGalleryAdapter.ViewHOlder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BooksGalleryAdapter.ViewHOlder holder, final int position) {
        final OptionsEntity feedItem = feedItemList.get(position);
        if (feedItem != null) {
            if (feedItem.getBookTitle() != null) {
                holder.Title.setText(feedItem.getBookTitle());
                if (feedItem.getBookStatus() != null) {
                    holder.Status.setText(feedItem.getBookStatus());
                    if (feedItem.getBookPrice() != null) {
                        holder.Price.setText(feedItem.getBookPrice());
                        if (feedItem.getBookOwnerID() != null) {
                            holder.StartChat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // go to chat with book owner
                                    // get owner id
                                    String BookOwnerID =feedItem.getBookOwnerID();
                                    Intent intent=new Intent(mContext,ChatActivity.class);
                                    intent.putExtra(BookOwnerID_KEY,BookOwnerID);
                                    mContext.startActivity(intent);
                                }
                            });
                        }
                    } else {
                        holder.Price.setText("");
                    }
                } else {
                    holder.Status.setText("");
                }
            } else {
                holder.Title.setText("");
            }
            Picasso.with(mContext).load(feedItem.getBookImage())
                    .error(R.drawable.logo)
                    .into(holder.Image);
        }
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class ViewHOlder extends RecyclerView.ViewHolder {
        protected TextView Title;
        protected TextView Status;
        protected TextView Price;
        protected CircleImageView Image;
        protected Button StartChat;

        public ViewHOlder(View converview) {
            super(converview);
            this.Title = (TextView) converview.findViewById(R.id.book_title);
            this.Price = (TextView) converview.findViewById(R.id.book_price);
            this.Status = (TextView) converview.findViewById(R.id.book_status);
            this.Image = (CircleImageView) converview.findViewById(R.id.book_image);
            this.StartChat=(Button)converview.findViewById(R.id.start_chat);
        }
    }
}