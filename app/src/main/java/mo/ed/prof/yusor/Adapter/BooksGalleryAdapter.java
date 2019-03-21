package mo.ed.prof.yusor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import mo.ed.prof.yusor.Activities.ChatActivity;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Config;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

/**
 * Created by Prof-Mohamed Atef on 2/12/2019.
 */

public class BooksGalleryAdapter extends RecyclerView.Adapter<BooksGalleryAdapter.ViewHOlder> implements Serializable {

    Context mContext;
    ArrayList<StudentsEntity> feedItemList;
    boolean TwoPane;
    public static String BookOwnerID_KEY="BookOwnerID_KEY";
    public static String BookID_KEY="BookID_KEY";
    public static String BookName_KEY="BookName_KEY";
    public static String SellerFacultyName_KEY="SellerFacultyName_KEY";
    public static String SellerEmail_KEY="SellerEmail_KEY";
    public static String Transaction_KEY="Transaction_KEY";
    public static String Price_KEY="Price_KEY";
    public static String ISBN_KEY="ISBN_KEY";
    public static String AuthorName_KEY="AuthorName_KEY";
    public static String PublishYear_KEY="PublishYear_KEY";
    public static String BookDescription_KEY="BookDescription_KEY";
    public static String BookSellerID_KEY="BookSellerID_KEY";
    public static String PivotID_KEY="PivotID_KEY";

    public BooksGalleryAdapter(Context mContext, ArrayList<StudentsEntity> feedItemList, boolean twoPane) {
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
        final StudentsEntity feedItem = feedItemList.get(position);
        if (feedItem != null) {
            if (feedItem.getBookTitle() != null) {
                holder.BookName.setText(feedItem.getBookTitle());
                if (feedItem.getAuthorTitle() != null) {
                    holder.AuthorName.setText(feedItem.getAuthorTitle());
                    if (feedItem.getBookPrice() != null) {
                        holder.Price.setText(feedItem.getBookPrice());
                        if (feedItem.getBookImage()!=null){
                            Picasso.with(mContext).load(feedItem.getBookImage())
                                    .error(R.drawable.logo)
                                    .into(holder.Image);
                        }
                        if (feedItem.getBookOwnerID() != null) {
                            holder.BTN_StartChat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // go to chat with book owner
                                    // get owner id
                                    String PivotID=feedItem.getPivotID();
                                    String SellerUserName =feedItem.getSellerUserName();
                                    String SellerID=feedItem.getBookOwnerID();
                                    String BookID=feedItem.getBookID();
                                    String BookName=feedItem.getBookTitle();
                                    String BookDescription=feedItem.getBookDescription();
                                    String PublishYear=feedItem.getPublishYear();
                                    String AuthorName=feedItem.getAuthorTitle();
                                    String ISBN=feedItem.getISBN_NUM();
                                    String Price=feedItem.getBookPrice();
                                    String Transaction=feedItem.getTransactionType();
                                    String SellerEmail=feedItem.getSellerEmail();
                                    String SellerFacultyName=feedItem.getDepartmentName();
                                    Intent intent=new Intent(mContext,ChatActivity.class);
                                    intent.putExtra(PivotID_KEY,PivotID);
                                    intent.putExtra(BookOwnerID_KEY,SellerUserName);
                                    intent.putExtra(BookSellerID_KEY,SellerID);
                                    intent.putExtra(BookID_KEY,BookID);
                                    intent.putExtra(BookName_KEY,BookName);
                                    intent.putExtra(BookDescription_KEY,BookDescription);
                                    intent.putExtra(PublishYear_KEY,PublishYear);
                                    intent.putExtra(AuthorName_KEY,AuthorName);
                                    intent.putExtra(ISBN_KEY,ISBN);
                                    intent.putExtra(Price_KEY,Price);
                                    intent.putExtra(Transaction_KEY,Transaction);
                                    intent.putExtra(SellerEmail_KEY,SellerEmail);
                                    intent.putExtra(SellerFacultyName_KEY,SellerFacultyName);

                                    mContext.startActivity(intent);
                                    Config.Buyer=true;
                                }
                            });
                        }
                    } else {
                        holder.Price.setText("");
                    }
                } else {
                    holder.AuthorName.setText("");
                }
            } else {
                holder.BookName.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class ViewHOlder extends RecyclerView.ViewHolder {
        protected Button BTN_Details;
        protected TextView BookName;
        protected TextView AuthorName;
        protected TextView Price;
        protected ImageView Image;
        protected Button BTN_StartChat;

        public ViewHOlder(View converview) {
            super(converview);

            this.BookName = (TextView) converview.findViewById(R.id.book_name);
            this.AuthorName= (TextView) converview.findViewById(R.id.author_name);
            this.Price = (TextView) converview.findViewById(R.id.book_price);
            this.Image = (ImageView) converview.findViewById(R.id.book_image);
            this.BTN_Details=(Button)converview.findViewById(R.id.details_btn);
            this.BTN_StartChat=(Button)converview.findViewById(R.id.start_chat_btn);
        }
    }
}