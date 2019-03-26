package mo.ed.prof.yusor.Adapter.Bills;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

/**
 * Created by Prof-Mohamed Atef on 3/25/2019.
 */

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.ViewHOlder> implements Serializable {

    Context mContext;
    List<StudentsEntity> feedItemList;
    Cursor mCursor;

    public BillsAdapter(Context mContext, List<StudentsEntity> feedItemList) {
        this.mContext = mContext;
        this.feedItemList = feedItemList;
    }

    @NonNull
    @Override
    public ViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_list_item, null);
        RecyclerView.ViewHolder viewHolder = new ViewHOlder(view);
        return (ViewHOlder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHOlder holder, final int position) {
        final StudentsEntity feedItem = feedItemList.get(position);
        if (feedItem != null) {
            if (feedItem.getCreatedAt()!=null){
                holder.txt_created_at.setText(feedItem.getCreatedAt());
                if (feedItem.getUpdatedAt()!=null){
                    holder.txt_updated_at.setText(feedItem.getUpdatedAt());
                }else {
                    holder.txt_updated_at.setText("");
                }
            }else {
                holder.txt_created_at.setText("");
            }
            if (feedItem.getOwnerStatus()!=null&&feedItem.getBuyerStatus()!=null){
                if (feedItem.getOwnerStatus().equals("1")&&feedItem.getBuyerStatus().equals("0")){
                    holder.bill_status.setText("Pending");
                    holder.bill_status.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.img_approved.setVisibility(View.GONE);
                    holder.Done.setVisibility(View.GONE);
                    holder.btn_approve.setVisibility(View.VISIBLE);
                }else if (feedItem.getOwnerStatus().equals("1")&&feedItem.getBuyerStatus().equals("1")){
                    holder.bill_status.setText("Completed");
                    holder.bill_status.setTextColor(mContext.getResources().getColor(R.color.green));
                    holder.btn_approve.setVisibility(View.GONE);
                    holder.img_approved.setVisibility(View.VISIBLE);
                    holder.Done.setVisibility(View.VISIBLE);
                }
            }
            holder.btn_approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Approval("1", );
                    holder.img_approved.setVisibility(View.VISIBLE);
                    holder.Done.setVisibility(View.VISIBLE);
                    Picasso.with(mContext).load(R.drawable.ic_action_approved)
                            .error(R.drawable.logo)
                            .into(holder.img_approved);
                    holder.btn_approve.setVisibility(View.GONE);
                }
            });
            if (feedItem.getBillID()!=null){
                holder.bill_number.setText(feedItem.getBillID());
                if (feedItem.getSellerPersonName()!=null){
                    holder.txt_seller_name.setText(feedItem.getBillID());
                    if (feedItem.getBuyerPName()!=null){
                        holder.txt_buyer_name.setText(feedItem.getBuyerPName());
                        if (feedItem.getBookTitle()!=null){
                            holder.txt_book_name.setText(feedItem.getBookTitle());
                            if (feedItem.getBookPrice()!=null){
                                holder.txt_price.setText(feedItem.getBookPrice());
                            }else {
                                holder.txt_price.setText("");
                            }
                        }else {
                            holder.txt_book_name.setText("");
                        }
                    }else {
                        holder.txt_buyer_name.setText("");
                    }
                }else {
                    holder.txt_seller_name.setText("");
                }
            }else {
                holder.bill_number.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        int size=0;
        if (feedItemList!=null){
            size=(null != feedItemList ? feedItemList.size() : 0);
        }if (mCursor!=null){
            size=(null != mCursor ? mCursor.getCount() : 0);
        }
        return size;
    }

    class ViewHOlder extends RecyclerView.ViewHolder {

        private final TextView txt_seller_name;
        private final TextView txt_buyer_name;
        private final TextView txt_book_name;
        private final TextView txt_price;
        private final TextView txt_created_at;
        private final TextView txt_updated_at;
        private final CircleImageView img_approved;
        private final TextView Done;
        private final Button btn_approve;
        TextView bill_status, bill_number;

        public ViewHOlder(View converview) {
            super(converview);

            this.bill_status=(TextView)converview.findViewById(R.id.bill_status);
            this.bill_number=(TextView)converview.findViewById(R.id.bill_number_val);
            this.txt_seller_name=(TextView)converview.findViewById(R.id.txt_seller_name);
            this.txt_buyer_name=(TextView)converview.findViewById(R.id.txt_buyer_name);
            this.txt_book_name=(TextView)converview.findViewById(R.id.txt_book_name);
            this.txt_price=(TextView)converview.findViewById(R.id.txt_price);
            this.txt_updated_at=(TextView)converview.findViewById(R.id.updated_at);
            this.txt_created_at=(TextView)converview.findViewById(R.id.created_at);
            this.img_approved=(CircleImageView)converview.findViewById(R.id.img_approved);
            this.Done= (TextView) converview.findViewById(R.id.txt_done);
            this.btn_approve=(Button)converview.findViewById(R.id.btn_approve);
        }
    }
}