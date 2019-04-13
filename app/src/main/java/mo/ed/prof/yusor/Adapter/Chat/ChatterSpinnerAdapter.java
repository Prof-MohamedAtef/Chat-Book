package mo.ed.prof.yusor.Adapter.Chat;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mo.ed.prof.yusor.Dev.Entity.FirebaseUsers;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;


/**
 * Created by Prof-Mohamed Atef on 1/28/2019.
 */
public class ChatterSpinnerAdapter extends BaseAdapter {

    private Context mContext;
    List<FirebaseUsers> feedItemList;

    public ChatterSpinnerAdapter(Context applicationContext, List<FirebaseUsers> firebaseUsers) {
        mContext=applicationContext;
        feedItemList=firebaseUsers;
    }

    @Override
    public int getCount() {
        return feedItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return feedItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(mContext);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(feedItemList.get(position).getUserName().toString());
        txt.setTextColor(Color.parseColor("#FFFFFF"));
        txt.setBackgroundResource(R.color.blue);
        return  txt;
    }

    TextView txt;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        txt = new TextView(mContext);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(10, 10, 10, 10);
        txt.setTextSize(16);
        txt.setText(feedItemList.get(position).getUserName().toString());
        txt.setTextColor(Color.parseColor("#FFFFFF"));
        txt.setBackgroundResource(R.color.blue);
        return  txt;
    }
}