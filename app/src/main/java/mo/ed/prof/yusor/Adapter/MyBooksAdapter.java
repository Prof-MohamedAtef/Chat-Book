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
import java.util.HashMap;

import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;
import mo.ed.prof.yusor.helpers.SessionManagement;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Prof-Mohamed Atef on 3/26/2019.
 */

public class MyBooksAdapter extends RecyclerView.Adapter<MyBooksAdapter.ViewHOlder> implements Serializable {

    String firebaseUiD;
    HashMap<String, String> user;
    SessionManagement sessionManagement;
    Context mContext;
    ArrayList<StudentsEntity> feedItemList;
    boolean TwoPane;
    public static String SellerUserName_KEY ="SellerUserName_KEY";
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
    private Intent intent;
    private String PivotID;
    private String SellerUserName;
    private String SellerID;
    private String BookID;
    private String BookName;
    private String BookDescription;
    private String PublishYear;
    private String AuthorName;
    private String ISBN;
    private String Price;
    private String Transaction;
    private String SellerEmail;
    private String SellerFacultyName;
    private String BookFireBUiD;

    public MyBooksAdapter(Context mContext, ArrayList<StudentsEntity> feedItemList) {
        this.mContext = mContext;
        this.feedItemList = feedItemList;
        sessionManagement=new SessionManagement(getApplicationContext());
        user=sessionManagement.getUserDetails();
        if (user!=null){
            firebaseUiD=user.get(SessionManagement.firebase_UID_KEY);
        }
    }

    @NonNull
    @Override
    public MyBooksAdapter.ViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mybooks_list_item, null);
        RecyclerView.ViewHolder viewHolder = new MyBooksAdapter.ViewHOlder(view);
        return (MyBooksAdapter.ViewHOlder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyBooksAdapter.ViewHOlder holder, final int position) {
        final StudentsEntity feedItem = feedItemList.get(position);
        if (feedItem != null) {
            if (feedItem.getBookImage()!=null){
                Picasso.with(mContext).load(feedItem.getBookImage())
                        .error(R.drawable.logo)
                        .into(holder.book_photo);
            }
            if (feedItem.getPublishYear()!=null){
                holder.PublishYear.setText(feedItem.getPublishYear());
            }else {
                holder.PublishYear.setText("");
            }
            if (feedItem.getISBN_NUM()!=null){
                holder.ISBN.setText(feedItem.getISBN_NUM());
            }else {
                holder.ISBN.setText("");
            }
            if (feedItem.getBookDescription()!=null){
                holder.desc.setText(feedItem.getBookDescription());
            }else {
                holder.desc.setText("");
            }
            if (feedItem.getBookPrice()!=null){
                holder.price.setText(feedItem.getBookPrice());
            }else {
                holder.price.setText("");
            }
            if (feedItem.getAvailability()!=null){
                if (feedItem.getAvailability().equals("")){
                    holder.available_in_store.setText("");
                }else if (feedItem.getAvailability().equals("")){
                    holder.available_in_store.setText("");
                }
            }
            if (feedItem.getBookStatus()!=null){
                holder.book_status.setText(feedItem.getBookStatus());
            }else {
                holder.book_status.setText("");
            }
            if (feedItem.getTransactionType()!=null){
                holder.transaction_type.setText(feedItem.getTransactionType());
            }else {
                holder.transaction_type.setText("");
            }
            if (feedItem.getBookTitle() != null) {
                holder.BookName.setText(feedItem.getBookTitle());
                if (feedItem.getAuthorTitle() != null) {
                    holder.AuthorName.setText(feedItem.getAuthorTitle());
                    if (feedItem.getBookPrice() != null) {
                        holder.price.setText(feedItem.getBookPrice());
                    } else {
                        holder.price.setText("");
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
        private final ImageView book_photo;
        private final TextView PublishYear;
        private final TextView ISBN;
        private final TextView desc;
        private final TextView price;
        private final TextView available_in_store;
        private final TextView book_status;
        private final TextView transaction_type;
        protected TextView BookName;
        protected TextView AuthorName;

        public ViewHOlder(View converview) {
            super(converview);
            this.book_photo=(ImageView)converview.findViewById(R.id.book_photo);
            this.BookName = (TextView) converview.findViewById(R.id.book_name);
            this.AuthorName= (TextView) converview.findViewById(R.id.author_name);
            this.PublishYear = (TextView) converview.findViewById(R.id.publish_year);
            this.ISBN = (TextView) converview.findViewById(R.id.num_isbn);
            this.desc=(TextView)converview.findViewById(R.id.desc);
            this.price=(TextView) converview.findViewById(R.id.price);
            this.available_in_store=(TextView)converview.findViewById(R.id.available_in_store);
            this.book_status=(TextView)converview.findViewById(R.id.book_status);
            this.transaction_type=(TextView)converview.findViewById(R.id.transaction_type);
        }
    }
}