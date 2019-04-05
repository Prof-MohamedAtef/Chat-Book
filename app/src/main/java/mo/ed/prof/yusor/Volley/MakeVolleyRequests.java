package mo.ed.prof.yusor.Volley;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
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
    private String KEY_BOOKTitle="title";
    private String KEY_BOOKDescription="desc";
    private String KEY_AuthorID="author_id";
    private String KEY_PublishYear="publish_year";
    private String KEY_DepartID="department_id";
    private String KEY_ISBN="ISBN_num";
    private String KEY_AuthorName="name";
    private String KEY_PHOTO="photo";
    private String KEY_BOOKID="book_id";
    private String KEY_APIKEY="api_token";


    public MakeVolleyRequests(Context context, OnCompleteListener onCompleteListener){
        this.mContext=context;
        this.mListener= (OnCompleteListener) onCompleteListener;
    }

    private String KEY_BOOKNAME="book_name";

    public void searchSuggestedBooks(final String BookID, final String BookName, final String tokenID) {
        final RequestQueue requestQueue  = Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "http://fla4news.com/Yusor/api/v1/similar_books",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.matches("")){
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                JsonParser jsonParser = new JsonParser();
                                ArrayList<StudentsEntity> studentsEntities = jsonParser.getSimilarBooks(response);
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
                hashMap.put(KEY_BOOKID,BookID);
                hashMap.put(KEY_BOOKNAME,BookName);
                hashMap.put(KEY_APIKEY,tokenID);
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

    public void sendBookDetails(final String bookName, final String bookDescription, final String authorID, final String publishYear, final String facultyID, final String isbn_num, final String authorName, final String photo, final String api_token) {
        final RequestQueue requestQueue  = Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "http://fla4news.com/Yusor/api/v1/add_book",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.matches("")){
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                JsonParser jsonParser = new JsonParser();
                                ArrayList<StudentsEntity> studentsEntities = jsonParser.parseAddedBooksJsonDetails(response);
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
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
//                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put(KEY_BOOKTitle,bookName);
                hashMap.put(KEY_BOOKDescription,bookDescription);
                if (authorID!=null&&authorID.length()>0){
                    hashMap.put(KEY_AuthorID,authorID);
                }else if (authorName!=null&&authorName.length()>0){
                    hashMap.put(KEY_AuthorName,authorName);
                }
                hashMap.put(KEY_PublishYear,publishYear);
                hashMap.put(KEY_DepartID,facultyID);
                hashMap.put(KEY_ISBN,isbn_num);
//                hashMap.put(KEY_PHOTO,photo);
                hashMap.put("api_token",api_token);
                return  hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private OnCompleteListener mListener;

    public void getAllBooksForSale(final String api_token) {
        final RequestQueue requestQueue  = Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "http://fla4news.com/Yusor/api/v1/books_student",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.matches("")){
//                            Toast.makeText(mContext, mContext.getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                            ArrayList<StudentsEntity> studentsEntities=new ArrayList<>();
                            studentsEntities.clear();
                            mListener.onComplete(studentsEntities);
                        }else {
                            try {
                                JsonParser jsonParser = new JsonParser();
                                ArrayList<StudentsEntity> studentsEntities = jsonParser.parseAllBooksForSale(response);
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
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
//                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put(KEY_APIKEY,api_token);
                return  hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getSoldBills(final String apiToken) {
        final RequestQueue requestQueue  = Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "http://fla4news.com/Yusor/api/v1/Bills_sold",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.matches("")){
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                JsonParser jsonParser = new JsonParser();
                                ArrayList<StudentsEntity> studentsEntities = jsonParser.getSoldBills(response);
                                if (studentsEntities != null) {
                                    if (studentsEntities.size() > 0) {
                                        mListener.onComplete(studentsEntities);
                                    }else {
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
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
//                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put(KEY_APIKEY,apiToken);
                return  hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getAllBooksForUser(final String apiToken) {
        final RequestQueue requestQueue  = Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "http://fla4news.com/Yusor/api/v1/Mybooks",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.matches("")){
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                JsonParser jsonParser = new JsonParser();
                                ArrayList<StudentsEntity> studentsEntities = jsonParser.getMyBooks(response);
                                if (studentsEntities != null) {
                                    if (studentsEntities.size() > 0) {
                                        mListener.onComplete(studentsEntities);
                                    }else if (studentsEntities.size()==0){
                                        Toast.makeText(mContext,mContext.getResources().getString(R.string.no_books_exist),Toast.LENGTH_LONG).show();
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
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
//                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("api_token",apiToken);
                return  hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void ApproveBill(final String billID, final String apiToken) {
        final RequestQueue requestQueue  = Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "http://fla4news.com/Yusor/api/v1/update_buyer_atatus",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.matches("")){
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                JsonParser jsonParser = new JsonParser();
                                ArrayList<StudentsEntity> studentsEntities = jsonParser.ApproveBill(response);
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
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
//                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("api_token",apiToken);
                hashMap.put("bill_id",billID);
                return  hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void updateBookDetails(final String bookName, final String bookDescription, final String author_id, final String publishYear, final String facultyID,
                                  final String isbn_number, final String apiToken, final String bookID, final String bookStatus, final String availability,
                                  final String transactionTypesId, final String price , final String authorTitle) {
        //http://fla4news.com/Yusor/api/v1/update_book
        final RequestQueue requestQueue  = Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "http://fla4news.com/Yusor/api/v1/update_book",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.matches("")){
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                JsonParser jsonParser = new JsonParser();
                                ArrayList<StudentsEntity> studentsEntities = jsonParser.updateBook(response);
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
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
//                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("title",bookName);
                hashMap.put("desc",bookDescription);
                hashMap.put("author_id",author_id);
                hashMap.put("publish_year",publishYear);
                hashMap.put("department_id",facultyID);
                hashMap.put("ISBN_num",isbn_number);
                hashMap.put("api_token",apiToken);
                hashMap.put("book_id",bookID);
                hashMap.put("book_status",bookStatus);
                hashMap.put("availability",availability);
                hashMap.put("transaction_types_id",transactionTypesId);
                hashMap.put("price",price);
                hashMap.put("name",authorTitle);
                return  hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void removeBook(final String apiToken, final String bookID) {
        final RequestQueue requestQueue  = Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "http://fla4news.com/Yusor/api/v1/delete_book",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.matches("")){
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                JsonParser jsonParser = new JsonParser();
                                ArrayList<StudentsEntity> studentsEntities = jsonParser.deleteBook(response);
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
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
//                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("api_token",apiToken);
                hashMap.put("book_id",bookID);
                return  hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void sendReport(final String apiToken, final String text_sharedIdeas) {
        final RequestQueue requestQueue  = Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "http://fla4news.com/Yusor/api/v1/reports",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.matches("")){
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                JsonParser jsonParser = new JsonParser();
                                ArrayList<StudentsEntity> studentsEntities = jsonParser.sendReports(response);
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
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
//                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("api_token",apiToken);
                hashMap.put("text",text_sharedIdeas);
                return  hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getProfile(final String tokenID) {
        final RequestQueue requestQueue  = Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "http://fla4news.com/Yusor/api/v1/profile",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.matches("")){
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                JsonParser jsonParser = new JsonParser();
                                ArrayList<StudentsEntity> studentsEntities = jsonParser.getProfile(response);
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
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
//                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("api_token",tokenID);
                return  hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public interface OnCompleteListener {
        public void onComplete(ArrayList<StudentsEntity> studentsEntities);
    }
}
