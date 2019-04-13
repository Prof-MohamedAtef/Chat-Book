package mo.ed.prof.yusor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import mo.ed.prof.yusor.Activities.BillApprove.BookDetailActivity;
import mo.ed.prof.yusor.Dev.MessageActivity;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Config;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;
import mo.ed.prof.yusor.helpers.SessionManagement;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Prof-Mohamed Atef on 2/12/2019.
 */

public class BooksGalleryAdapter extends RecyclerView.Adapter<BooksGalleryAdapter.ViewHOlder> implements Serializable, Filterable {

    String firebaseUiD;
    HashMap<String, String> user;
    SessionManagement sessionManagement;
    Context mContext;
    ArrayList<StudentsEntity> feedItemList;
    ArrayList<StudentsEntity> feedItemListFull;
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
    private String BaseImage;

    public BooksGalleryAdapter(Context mContext, ArrayList<StudentsEntity> feedItemList, boolean twoPane) {
        this.mContext = mContext;
        this.feedItemList = feedItemList;
        TwoPane = twoPane;
        sessionManagement=new SessionManagement(getApplicationContext());
        user=sessionManagement.getUserDetails();
        if (user!=null){
            firebaseUiD=user.get(SessionManagement.firebase_UID_KEY);
        }
        this.feedItemListFull=new ArrayList<>(feedItemList);
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
        StudentsEntity feedItem=null;
        feedItem = feedItemList.get(position);
        if (feedItem != null) {
            if (feedItem.getBookTitle() != null) {
                holder.BookName.setText(feedItem.getBookTitle());
                if (feedItem.getAuthorTitle() != null) {
                    holder.AuthorName.setText(feedItem.getAuthorTitle());
                    if (feedItem.getBookPrice() != null) {
                        holder.Price.setText(feedItem.getBookPrice());
                        if (feedItem.getBookImage()!=null){
                            BaseImage= Config.IMAGEBaseUrl+feedItem.getBookImage();
                            Picasso.with(mContext).load(BaseImage)
                                    .error(R.drawable.logo)
                                    .into(holder.Image);
                        }
                        if (feedItem.getBookOwnerID() != null) {
                            PivotID=feedItem.getPivotID();
                            SellerUserName =feedItem.getSellerUserName();
                            SellerID=feedItem.getBookOwnerID();
                            BookID=feedItem.getBookID();
                            BookName=feedItem.getBookTitle();
                            BookDescription=feedItem.getBookDescription();
                            PublishYear=feedItem.getPublishYear();
                            AuthorName=feedItem.getAuthorTitle();
                            ISBN=feedItem.getISBN_NUM();
                            Price=feedItem.getBookPrice();
                            Transaction=feedItem.getTransactionType();
                            SellerEmail=feedItem.getSellerEmail();
                            SellerFacultyName=feedItem.getDepartmentName();
                            BookFireBUiD= feedItem.getSellerFirebaseUid();
                            if (BookFireBUiD.equals(firebaseUiD)){
                                holder.BTN_StartChat.setVisibility(View.GONE);
                            }else {
                                holder.BTN_StartChat.setVisibility(View.VISIBLE);
                                final StudentsEntity finalFeedItem = feedItem;
                                holder.BTN_StartChat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // go to chat with book owner
                                        // get owner id
                                        intent=new Intent(mContext,MessageActivity.class);
                                        intent.putExtra("userid", finalFeedItem.getSellerFirebaseUid());
                                        intent.putExtra(PivotID_KEY,PivotID);
                                        intent.putExtra(SellerUserName_KEY,SellerUserName);
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

                            final StudentsEntity finalFeedItem1 = feedItem;
                            holder.BTN_Details.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    intent=new Intent(mContext,BookDetailActivity.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putSerializable("feedItem", finalFeedItem1);
                                    intent.putExtras(bundle);
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

    @Override
    public Filter getFilter() {
        return BooksFilter;
    }

    private Filter BooksFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<StudentsEntity> filteredList=new ArrayList<>();
            if (constraint==null || constraint.length()==0){
                filteredList.addAll(feedItemListFull);
            }else {
                String filterPattern=constraint.toString().toLowerCase().trim();
                for (StudentsEntity entity:feedItemListFull){
                    if (entity.getBookTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(entity);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            feedItemList.clear();
            feedItemList.addAll((ArrayList<StudentsEntity>)results.values);
            notifyDataSetChanged();
        }
    };

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