package mo.ed.prof.yusor.Adapter;

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
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

/**
 * Created by Prof-Mohamed Atef on 2/12/2019.
 */

public class SimilarBooksAdapter extends RecyclerView.Adapter<SimilarBooksAdapter.ViewHOlder> implements Serializable {

    Context mContext;
    ArrayList<StudentsEntity> feedItemList;
    boolean TwoPane;

    public SimilarBooksAdapter(Context mContext, ArrayList<StudentsEntity> feedItemList, boolean twoPane) {
        this.mContext = mContext;
        this.feedItemList = feedItemList;
        TwoPane = twoPane;
    }

    @NonNull
    @Override
    public SimilarBooksAdapter.ViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.similar_books_list_item, null);
        RecyclerView.ViewHolder viewHolder = new SimilarBooksAdapter.ViewHOlder(view);
        return (SimilarBooksAdapter.ViewHOlder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarBooksAdapter.ViewHOlder holder, final int position) {
        final StudentsEntity feedItem = feedItemList.get(position);
        if (feedItem!=null){
            if (feedItem.getBookTitle()!=null) {
                holder.Title.setText(feedItem.getBookTitle());
                if (feedItem.getBookPrice() != null) {
                    holder.Price.setText(feedItem.getBookPrice());
                } else {
                    holder.Price.setText("");
                }
            }
            else {
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
        protected TextView Price;
        protected CircleImageView Image;

        public ViewHOlder(View converview) {
            super(converview);
            this.Title = (TextView) converview.findViewById(R.id.book_title);
            this.Price= (TextView) converview.findViewById(R.id.book_price);
            this.Image =(CircleImageView)converview.findViewById(R.id.book_image);
        }
    }
}