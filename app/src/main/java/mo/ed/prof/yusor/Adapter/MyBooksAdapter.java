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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import mo.ed.prof.yusor.Activities.Book.EditBookActivity;
import mo.ed.prof.yusor.Activities.MainActivity;
import mo.ed.prof.yusor.Dev.MessageActivity;
import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.Volley.MakeVolleyRequests;
import mo.ed.prof.yusor.helpers.Config;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;
import mo.ed.prof.yusor.helpers.SessionManagement;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Prof-Mohamed Atef on 3/26/2019.
 */

public class MyBooksAdapter extends RecyclerView.Adapter<MyBooksAdapter.ViewHOlder> implements Serializable, MakeVolleyRequests.OnCompleteListener{

    private final VerifyConnection verifyConnection;
    String ApiToken;
    String firebaseUiD;
    HashMap<String, String> user;
    SessionManagement sessionManagement;
    Context mContext;
    ArrayList<StudentsEntity> feedItemList;
    private MakeVolleyRequests makeVolleyRequest;
    private String BookID;
    private String BaseImage;

    public MyBooksAdapter(Context mContext, ArrayList<StudentsEntity> feedItemList) {
        this.mContext = mContext;
        this.feedItemList = feedItemList;
        sessionManagement=new SessionManagement(getApplicationContext());
        user=sessionManagement.getUserDetails();
        if (user!=null){
            firebaseUiD=user.get(SessionManagement.firebase_UID_KEY);
            ApiToken = user.get(SessionManagement.KEY_idToken);
        }
        verifyConnection=new VerifyConnection(mContext);
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
                BaseImage= Config.IMAGEBaseUrl+feedItem.getBookImage();
                Picasso.with(mContext).load(BaseImage)
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
            // 1 sold -- 0 onshow
            if (feedItem.getAvailability()!=null){
                if (feedItem.getAvailability().equals("1")){
                    holder.available_in_store.setText(mContext.getResources().getString(R.string.sold));
                }else if (feedItem.getAvailability().equals("0")){
                    holder.available_in_store.setText(mContext.getResources().getString(R.string.onshow));
                }
            }
            if (feedItem.getBookStatus()!=null){
                if (feedItem.getBookStatus().equals("1")){
                    holder.book_status.setText(mContext.getResources().getString(R.string._new));
                }else if (feedItem.getBookStatus().equals("2")){
                    holder.book_status.setText(mContext.getResources().getString(R.string.Intermediate));
                }else if (feedItem.getBookStatus().equals("3")){
                    holder.book_status.setText(mContext.getResources().getString(R.string.not_bad));
                }
            }else {
                holder.book_status.setText("");
            }
            if (feedItem.getTransactionType()!=null){
                if (feedItem.getTransactionType().equals("1")){
                    holder.transaction_type.setText(mContext.getResources().getString(R.string.sale));
                }else if (feedItem.getTransactionType().equals("2")){
                    holder.transaction_type.setText(mContext.getResources().getString(R.string.exchange));
                }else if (feedItem.getTransactionType().equals("3")){
                    holder.transaction_type.setText(mContext.getResources().getString(R.string.gift));
                }
            }else {
                holder.transaction_type.setText("");
            }
            if (feedItem.getBookTitle() != null) {
                holder.BookName.setText(feedItem.getBookTitle());
                if (feedItem.getBookPrice() != null) {
                    holder.price.setText(feedItem.getBookPrice());
                } else {
                    holder.price.setText("");
                }
            } else {
                holder.BookName.setText("");
            }
            holder.Linear_EditBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (verifyConnection.isConnected()){
                        Intent intent=new Intent(mContext, EditBookActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("feedItem",feedItem);
                        intent.putExtras(bundle);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.cannot_start_chat), Toast.LENGTH_LONG).show();
                    }
                }
            });

            holder.btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (verifyConnection.isConnected()){
                        makeVolleyRequest=new MakeVolleyRequests(mContext,MyBooksAdapter.this);
                        BookID= feedItem.getBookID();
                        makeVolleyRequest.removeBook(ApiToken,BookID);
                    }else {
                        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.cannot_start_chat), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    @Override
    public void onComplete(ArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities!=null){
            Intent intent=new Intent(mContext,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (studentsEntities.size()>0){
                for (StudentsEntity studentsEntity:studentsEntities){
                    if (studentsEntity.getException()!=null){
                        Toast.makeText(mContext, studentsEntity.getException().toString(),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(mContext, studentsEntity.getServerMessage().toString(),Toast.LENGTH_SHORT).show();
                        mContext.startActivity(intent);
                    }
                }
            }
        }
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
        private final LinearLayout Linear_EditBook;
        private final Button btn_remove;
        protected TextView BookName;

        public ViewHOlder(View converview) {
            super(converview);
            this.book_photo=(ImageView)converview.findViewById(R.id.book_photo);
            this.BookName = (TextView) converview.findViewById(R.id.book_name);
            this.PublishYear = (TextView) converview.findViewById(R.id.publish_year);
            this.ISBN = (TextView) converview.findViewById(R.id.num_isbn);
            this.desc=(TextView)converview.findViewById(R.id.desc);
            this.price=(TextView) converview.findViewById(R.id.price);
            this.available_in_store=(TextView)converview.findViewById(R.id.available_in_store);
            this.book_status=(TextView)converview.findViewById(R.id.book_status);
            this.transaction_type=(TextView)converview.findViewById(R.id.transaction_type);
            this.Linear_EditBook=(LinearLayout)converview.findViewById(R.id.Linear_EditBook);
            this.btn_remove=(Button)converview.findViewById(R.id.btn_remove);
        }
    }
}