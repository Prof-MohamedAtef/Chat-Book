package mo.ed.prof.yusor.Activities.Authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.dd.processbutton.iml.GenerateProcessButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Activities.MainActivity;
import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Designsers.ProgressGenerator;
import mo.ed.prof.yusor.helpers.Firebase.AuthenticationHandler.FirebaseUserHandler;
import mo.ed.prof.yusor.helpers.Firebase.FirebaseEntites;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;
import mo.ed.prof.yusor.helpers.SessionManagement;

public class LoginActivity extends AppCompatActivity implements ProgressGenerator.OnCompleteListener{


    @BindView(R.id.email_signin)
    EditText email_signin;

    @BindView(R.id.password_signing)
    EditText password_signing;

    @BindView(R.id.btn_signup)
    Button btn_signup;

    @BindView(R.id.btn_login)
    GenerateProcessButton btn_login;

    @BindView(R.id.btn_reset_password)
    Button btn_reset_password;

    String email, password , LoggedLocation1;
    SessionManagement sessionManagement;
    HashMap<String, String> user;
    String LoggedEmail;
    private String URL_Login="URL";
    private ArrayList<StudentsEntity> feedItemList;
    private VerifyConnection verifyConnection;
    private ProgressGenerator progressGenerator;
    private String PersonName;
    private String Email;
    private String UserName;
    private String selectedGender;
    private String mToken;
    private String UserID;
    private FirebaseUserHandler firebaseUserHandler;
    private DatabaseReference mDatabase;
    private String Users_KEY ="users";
    private FirebaseEntites firebaseEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTheme(R.style.ArishTheme);
        ButterKnife.bind(this);
        verifyConnection=new VerifyConnection(getApplicationContext());
        sessionManagement = new SessionManagement(getApplicationContext());
        user = sessionManagement.getUserDetails();
        if (mDatabase==null){
            FirebaseDatabase database= FirebaseDatabase.getInstance();
            mDatabase=database.getReference(Users_KEY);
//            mDatabase.keepSynced(true);
        }
        if (user != null) {
            LoggedEmail = user.get(SessionManagement.KEY_EMAIL);
            if (LoggedEmail != null || LoggedLocation1 != null) {
                Intent intent_postsRedirect = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_postsRedirect);
            }
        }


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, TaibahRegistrationActivity.class));
            }
        });

        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, ResetPassActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login.setEnabled(false);
                email = email_signin.getText().toString();
                password = password_signing.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
                    return;
                }


                if (email.length()>0&&password.length()>0){
                    if (verifyConnection.isConnected()){
                        progressGenerator = new ProgressGenerator((ProgressGenerator.OnCompleteListener)LoginActivity.this, getApplicationContext());
                        progressGenerator.startSignIn(btn_login, email, password);
                    }
                }
            }
        });
    }

    public void ResetPass(View view) {
//        Intent intent_ResetPass=new Intent(this,ResetPassActivity.class);
//        startActivity(intent_ResetPass);
    }

    @Override
    public void onComplete(ArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities!=null){
            if (studentsEntities.size()>0){
                for (StudentsEntity studentsEntity:studentsEntities){
                    if (studentsEntity.getException()!=null){
                        Toast.makeText(getApplicationContext(), studentsEntity.getException().toString(), Toast.LENGTH_LONG).show();
                        btn_login.setEnabled(true);
                        btn_login.setText(getApplicationContext().getResources().getString(R.string.Sign_in));
                    }else {
                        SharedPrefThenGalleryHomeRedirect(studentsEntities);
                    }
                }
            }
        }
    }

    private void SharedPrefThenGalleryHomeRedirect(ArrayList<StudentsEntity> studentsEntities) {
        for (StudentsEntity studentsEntity: studentsEntities){
            PersonName= studentsEntity.getPersonName();
            Email=studentsEntity.getEmail();
            UserName=studentsEntity.getUserName();
            selectedGender=studentsEntity.getGender();
            mToken=studentsEntity.getAPI_TOKEN();
            UserID=studentsEntity.getUserID();
//            DepartmentName=studentsEntity.getDepartmentName();
            //send authenticated user to firebase database
            firebaseUserHandler =new FirebaseUserHandler(UserID,mToken,selectedGender,UserName,Email,PersonName);
            firebaseEntities=new FirebaseEntites(mDatabase);
            firebaseEntities.AddUser(mDatabase,firebaseUserHandler);
        }
        sessionManagement.createYusorLoginSession(mToken,PersonName,Email,UserName,selectedGender, "DepartmentName",UserID);
        sessionManagement.createLoginSessionType("EP");
        Intent intent_create=new Intent(this,MainActivity.class);
        startActivity(intent_create);
        finish();
    }
}