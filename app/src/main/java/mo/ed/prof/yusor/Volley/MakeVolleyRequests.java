package mo.ed.prof.yusor.Volley;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

/**
 * Created by Prof-Mohamed Atef on 3/15/2019.
 */

public class MakeVolleyRequests {

    Context mContext;

    public MakeVolleyRequests(Context context, OnCompleteListener onCompleteListener){
        this.mContext=context;
        this.mListener= (OnCompleteListener) onCompleteListener;
    }

    private String KEY_BOOKNAME="book_name";

    public void searchSuggestedBooks(final String BookName) {
        final RequestQueue requestQueue  = Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "http://fla4news.com/Yusor/api/v1/books",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.matches("")){
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                JsonParser jsonParser = new JsonParser();
                                ArrayList<StudentsEntity> studentsEntities = jsonParser.parseBooksJsontoBeAdded(response);
                                if (studentsEntities != null) {
                                    if (studentsEntities.size() > 0) {
                                        mListener.onComplete(studentsEntities);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                loading.dismiss();
                //Showing toast
                if (error!=null){
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put(KEY_BOOKNAME,BookName);
                return  hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getPreviousAddedBooks() {
        final RequestQueue requestQueue  = Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "http://fla4news.com/Yusor/api/v1/register",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.matches("")){
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                JsonParser jsonParser = new JsonParser();
                                ArrayList<StudentsEntity> studentsEntities = jsonParser.signUpJsonParse(response);
                                if (studentsEntities != null) {
                                    if (studentsEntities.size() > 0) {
                                        mListener.onComplete(studentsEntities);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                loading.dismiss();
                //Showing toast
                if (error!=null){
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();
//                hashMap.put(KEY_BOOKNAME,personName);
                return  hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }




    private OnCompleteListener mListener;

    public interface OnCompleteListener {
        public void onComplete(ArrayList<StudentsEntity> studentsEntities);
    }
}
