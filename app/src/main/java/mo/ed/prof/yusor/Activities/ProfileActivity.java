package mo.ed.prof.yusor.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Fragments.BooksGalleryFragment;
import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.Volley.MakeVolleyRequests;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;
import mo.ed.prof.yusor.helpers.SessionManagement;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ProfileActivity extends AppCompatActivity implements MakeVolleyRequests.OnCompleteListener{

    private SessionManagement sessionManagement;
    private HashMap<String, String> user;
    private String TokenID;

    @BindView(R.id.txt_profile_name)
    TextView txt_profile_name;

    @BindView(R.id.txt_gender)
    TextView txt_gender;

    @BindView(R.id.txt_email)
    TextView txt_email;

    @BindView(R.id.txt_major)
    TextView txt_major;
    private VerifyConnection verifyConn;
    private MakeVolleyRequests makeRequest;
    private String UserName;
    private String Gender;
    private String Email;
    private String FirebaseUserid;
    private String DepartmentName;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow_red));
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sessionManagement = new SessionManagement(getApplicationContext());
        user = sessionManagement.getUserDetails();
        if (user != null) {
            TokenID = user.get(SessionManagement.KEY_idToken);
        }
        verifyConn = new VerifyConnection(getApplicationContext());
        if (verifyConn.isConnected()) {
            makeRequest = new MakeVolleyRequests(getApplicationContext(), ProfileActivity.this);
            makeRequest.getProfile(TokenID);

        }
    }

    @Override
    public void onComplete(ArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities != null) {
            if (studentsEntities.size() > 0) {
                for (StudentsEntity studentsEntity : studentsEntities) {
                    if (studentsEntity.getException() != null) {
                        Toast.makeText(getApplicationContext(), studentsEntity.getException().toString(), Toast.LENGTH_LONG).show();
                    } else {
                        DisplayUserData(studentsEntities);
                    }
                }
            }
        }
    }

    private void DisplayUserData(ArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities!=null){
            if (studentsEntities.size()>0){
                for (StudentsEntity studentsEntity:studentsEntities){
                    display(studentsEntity);
                }
            }
        }else {
            user = sessionManagement.getUserDetails();
            if (user != null) {
                UserName = user.get(SessionManagement.KEY_UserName);
                Gender = user.get(SessionManagement.KEY_Gender);
                Email = user.get(SessionManagement.KEY_EMAIL);
                DepartmentName = user.get(SessionManagement.KEY_DepartmentName);
                StudentsEntity studentsEntity=new StudentsEntity();
                studentsEntity.setUserName(UserName);
                studentsEntity.setGender(Gender);
                studentsEntity.setEmail(Email);
                studentsEntity.setDepartmentName(DepartmentName);
                display(studentsEntity);
            }
        }
    }

    private void display(StudentsEntity studentsEntity) {
        if (studentsEntity.getUserName()!=null){
            UserName= studentsEntity.getUserName();
            txt_profile_name.setText(UserName);
        }
        if (studentsEntity.getGender()!=null){
            Gender= studentsEntity.getGender();
            if (Gender.equals("m")){
                txt_gender.setText(getResources().getString(R.string.male));
            }else if (Gender.equals("f")){
                txt_gender.setText(getResources().getString(R.string.female));
            }

        }
        if (studentsEntity.getEmail()!=null){
            Email= studentsEntity.getEmail();
            txt_email.setText(Email);
        }
        if (studentsEntity.getDepartmentName()!=null){
            DepartmentName=studentsEntity.getDepartmentName();
            txt_major.setText(DepartmentName);
        }
    }
}
