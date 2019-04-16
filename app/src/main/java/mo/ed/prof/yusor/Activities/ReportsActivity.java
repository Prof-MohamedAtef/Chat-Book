package mo.ed.prof.yusor.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Activities.BillApprove.DisplayBillActivity;
import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.Volley.MakeVolleyRequests;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;
import mo.ed.prof.yusor.helpers.SessionManagement;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ReportsActivity extends AppCompatActivity implements MakeVolleyRequests.OnCompleteListener {

    @BindView(R.id.txt_shareideas)
    EditText txt_SharedIdeas;

    @BindView(R.id.BTN_SEND)
    Button BTN_SEND;
    private String Text_SharedIdeas;
    private VerifyConnection verifyConn;
    private MakeVolleyRequests makeRequest;
    private SessionManagement sessionManagement;
    private HashMap<String, String> user;
    private String ApiToken;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        ButterKnife.bind(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (Locale.getDefault().getLanguage().contentEquals("en")){
            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_back_en));
        }else if (Locale.getDefault().getLanguage().contentEquals("ar")){
            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_back_ar));
        }
        verifyConn = new VerifyConnection(getApplicationContext());
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sessionManagement=new SessionManagement(getApplicationContext());
        user=sessionManagement.getUserDetails();
        if (user!=null) {
            ApiToken = user.get(SessionManagement.KEY_idToken);
        }
        BTN_SEND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyConn.isConnected()){
                    Text_SharedIdeas = txt_SharedIdeas.getText().toString().trim();
                    if (Text_SharedIdeas != null && Text_SharedIdeas.length() > 0) {
                        makeRequest=new MakeVolleyRequests(getApplicationContext(), ReportsActivity.this);
                        makeRequest.sendReport(ApiToken, Text_SharedIdeas);
                    }else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.enter_report), Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.cannot_start_chat), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onComplete(ArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities!=null){
            if (studentsEntities.size()>0){
                for (StudentsEntity studentsEntity:studentsEntities){
                    if (studentsEntity.getException()!=null){
                        Toast.makeText(getApplicationContext(), studentsEntity.getException().toString(), Toast.LENGTH_LONG).show();
                    }else {
                        redirectAfterReporting();
                    }
                }
            }
        }
    }

    private void redirectAfterReporting() {
        Intent intent =new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
        finish();
    }
}