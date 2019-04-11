package mo.ed.prof.yusor.helpers.Designsers;

import android.content.Context;
import android.os.Handler;
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
import com.dd.processbutton.ProcessButton;
import com.dd.processbutton.iml.GenerateProcessButton;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.Volley.JsonParser;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

/**
 * Created by Prof-Mohamed Atef on 3/13/2019.
 */

public class ProgressGenerator {

    private int Done_Key=1;
    private int done=0;
    public static String KEY_PersonName="perso_name";
    public static String KEY_Email="Email";
    public static String KEY_UserName="UserName";
    public static String KEY_Password="password";
    public static String KEY_Gender="Gender";
    public static String KEY_DepartmentID="department_id";
    public static String KEY_ConfirmPass="password_confirmation";
    private String KEY_TokenID="api_token";
    private String KEY_Availability="availability";
    private String KEY_TransactionType="transaction_types_id";
    private String KEY_BookStatus="book_status";
    private String KEY_Price="price";
    private String KEY_BookID="book_id";

    public void startSignIn(final GenerateProcessButton btn_login, final String email, final String password, final  String firebase_uid) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress += 10;
                btn_login.setProgress(mProgress);
                signInStudent(email, password, firebase_uid);
                if (mProgress < 5&&done!=Done_Key) {
                    handler.postDelayed(this, generateDelay());
                }
            }
        }, generateDelay());
    }

    public void PublishBook(final GenerateProcessButton publish_BTN, final String bookID, final String bookPrice, final String sentBookStatus, final String sentAvailability, final String sentTransactionType, final String tokenID) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress += 10;
                publish_BTN.setProgress(mProgress);
                publishBookToServer(bookID,bookPrice,sentBookStatus,sentTransactionType, sentAvailability,tokenID);
                if (mProgress < 5&&done!=Done_Key) {
                    handler.postDelayed(this, generateDelay());
                }
            }
        }, generateDelay());
    }

    private void publishBookToServer(final String bookID, final String bookPrice, final String sentBookStatus, final String sentTransactionType, final String sentAvailability, final String tokenID) {

        final RequestQueue requestQueue  = Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "http://fla4news.com/Yusor/api/v1/create_offer",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.matches("")){
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                JsonParser jsonParser = new JsonParser();
                                ArrayList<StudentsEntity> studentsEntities=new ArrayList<>();
                                if (studentsEntities!=null){
                                    studentsEntities.clear();
                                }
                                studentsEntities = jsonParser.BookAddedJsonParse(response);
                                if (studentsEntities != null) {
                                    done = Done_Key;
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
                hashMap.put(KEY_BookID,bookID);
                hashMap.put(KEY_Price,bookPrice);
                hashMap.put(KEY_BookStatus,sentBookStatus);
                hashMap.put(KEY_TransactionType,sentTransactionType);
//                hashMap.put(KEY_Availability,sentAvailability);
                hashMap.put(KEY_TokenID,tokenID);
                return  hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void addBill(final GenerateProcessButton createBill_btn, final String buyerID, final String bookPrice, final String owner_status , final String buyer_status, final String book_id, final String APIToken) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress += 10;
                createBill_btn.setProgress(mProgress);
                addBill(buyerID,bookPrice,owner_status,buyer_status,book_id,APIToken);
                if (mProgress < 5&&done!=Done_Key) {
                    handler.postDelayed(this, generateDelay());
                }
            }
        }, generateDelay());
    }

    private void addBill(final String buyerID, final String Price, final String owner_Status, final String buyer_status, final String book_id, final String APIToken) {
        final RequestQueue requestQueue  = Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "http://fla4news.com/Yusor/api/v1/create_bill",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.matches("")){
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                JsonParser jsonParser = new JsonParser();
                                ArrayList<StudentsEntity> studentsEntities = jsonParser.returnCreatedBill(response);
                                if (studentsEntities != null) {
                                    done = Done_Key;
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
                hashMap.put("firbase_id",buyerID);
                hashMap.put("TotalAmount",Price);
                hashMap.put("owner_status",owner_Status);
                hashMap.put("buyer_status",buyer_status);
                hashMap.put("book_id",book_id);
                hashMap.put("api_token",APIToken);
                return  hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void approveBill(final GenerateProcessButton createBill_btn,final String billID,final String apiToken) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress += 10;
                createBill_btn.setProgress(mProgress);
                approveBillRequest(billID,apiToken);
                if (mProgress < 5&&done!=Done_Key) {
                    handler.postDelayed(this, generateDelay());
                }
            }
        }, generateDelay());
    }

    private void approveBillRequest(final String billID, final String apiToken) {
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

    public interface OnCompleteListener {
        public void onComplete(ArrayList<StudentsEntity> studentsEntities);
    }

    private OnCompleteListener mListener;
    private Context mContext;
    private int mProgress;

    public ProgressGenerator(OnCompleteListener listener, Context context) {
        mListener = listener;
        this.mContext=context;
    }

    public void startSignUp(final ProcessButton button, final String P_name, final String Email, final String userName, final String Password, final String confirmPassword, final String Gender, final String departmentid, final String firbase_id) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress += 10;
                button.setProgress(mProgress);
                signUpStudent(P_name,Email,userName,Password, confirmPassword,Gender, departmentid, firbase_id);
                if (mProgress < 5&&done!=Done_Key) {
                    handler.postDelayed(this, generateDelay());
                }
            }
        }, generateDelay());
    }

    private Random random = new Random();

    private int generateDelay() {
        return random.nextInt(1000);
    }

    public void signUpStudent(final String personName, final String email, final String userName, final String password, final String confirmPassword ,final String selectedGender, final String departmentID, final String firbase_id) {
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
                                    done = Done_Key;
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
                hashMap.put(KEY_PersonName,personName);
                hashMap.put(KEY_Email,email);
                hashMap.put(KEY_UserName,userName);
                hashMap.put(KEY_Password,password);
                hashMap.put(KEY_ConfirmPass,confirmPassword);
                hashMap.put(KEY_Gender,selectedGender);
                hashMap.put(KEY_DepartmentID,departmentID);
                hashMap.put("firbase_id",firbase_id);
                return  hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }



    public void signInStudent(final String email, final String password, final String firebase_uid) {
//        final ProgressDialog loading = ProgressDialog.show(mContext, mContext.getResources().getString(R.string.loading), mContext.getResources().getString(R.string.uploading), false, false);
        final RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "http://fla4news.com/Yusor/api/v1/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.matches("")){
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                JsonParser jsonParser = new JsonParser();
                                ArrayList<StudentsEntity> studentsEntities = jsonParser.signInJsonParse(response);
                                if (studentsEntities != null) {
                                    done = Done_Key;
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
                hashMap.put(KEY_Email,email);
                hashMap.put(KEY_Password,password);
                hashMap.put("firbase_id", firebase_uid);
                return  hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}