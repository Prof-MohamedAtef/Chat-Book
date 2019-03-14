package mo.ed.prof.yusor.helpers.Designsers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dd.processbutton.ProcessButton;

import org.json.JSONException;

import java.util.ArrayList;
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

    public void start(final ProcessButton button, final String P_name, final String Email, final String userName, final String Password, final String confirmPassword, final String Gender, final String departmentid) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress += 10;
                button.setProgress(mProgress);
                signUpStudent(P_name,Email,userName,Password, confirmPassword,Gender, departmentid);
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

    public void signUpStudent(final String personName, final String email, final String userName, final String password, final String confirmPassword ,final String selectedGender, final String departmentID) {
//        final ProgressDialog loading = ProgressDialog.show(mContext, mContext.getResources().getString(R.string.loading), mContext.getResources().getString(R.string.uploading), false, false);
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
                                ArrayList<StudentsEntity> studentsEntities = jsonParser.jsonParse(response);
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
                    NetworkAbortedDialouge();
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
                return  hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void NetworkAbortedDialouge() {
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getString(R.string.Opps));
        builder.setMessage(mContext.getString(R.string.no_internet));
        builder.setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog Dialogue=builder.create();
        Dialogue.show();
    }
}