package mo.ed.prof.yusor.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.SessionManagement;
import java.util.HashMap;

public class Activity_Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__splash);
        setTheme(R.style.ArishTheme);
        sessionManagement=new SessionManagement(getApplicationContext());
        user =sessionManagement.getUserDetails();
        if (user!=null){
            LoggedUserName=user.get(SessionManagement.KEY_NAME);
            LoggedEmail=user.get(SessionManagement.KEY_EMAIL);
        }
        mProgressDialog = new ProgressDialog(this);
    }


    ProgressDialog mProgressDialog;
    SessionManagement sessionManagement;
    HashMap<String, String> user;
    String LoggedEmail, LoggedUserName;
    @Override
    protected void onResume() {
        super.onResume();
        if (LoggedEmail != null&&LoggedUserName!=null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {hideProgressDialog();
                    startActivity(new Intent(Activity_Splash.this, MainActivity.class));
                    finish();
                }
            },2000);
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Activity_Splash.this, TaibahAuthActivity.class));
                    hideProgressDialog();
                    finish();
                }
            },2000);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        showProgressDialog();
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
}