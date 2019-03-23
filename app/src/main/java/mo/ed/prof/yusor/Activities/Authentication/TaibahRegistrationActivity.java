package mo.ed.prof.yusor.Activities.Authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.dd.processbutton.iml.GenerateProcessButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Activities.MainActivity;
import mo.ed.prof.yusor.Adapter.FacultiesSpinnerAdapter;
import mo.ed.prof.yusor.GenericAsyncTasks.RetrieveDepartmentsAsyncTask;
import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Config;
import mo.ed.prof.yusor.helpers.Designsers.ProgressGenerator;
import mo.ed.prof.yusor.helpers.Firebase.AuthenticationHandler.FirebaseUserHandler;
import mo.ed.prof.yusor.helpers.Firebase.FirebaseEntites;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;
import mo.ed.prof.yusor.helpers.SessionManagement;

public class TaibahRegistrationActivity extends AppCompatActivity implements RetrieveDepartmentsAsyncTask.OnDepartmentsRetrievalTaskCompleted,
ProgressGenerator.OnCompleteListener{

    private final String LOG_TAG = TaibahRegistrationActivity.class.getSimpleName();

    String URL="http://fla4news.com/Yusor/api/v1/departments";


    private String Category;

    @BindView(R.id.Edit_first_name)
    EditText Edit_first_name;

    @BindView(R.id.Edit_last_name)
    EditText Edit_last_name;

    @BindView(R.id.Edit_email)
    EditText Edit_email;

    @BindView(R.id.email_type)
    TextView email_type;

    @BindView(R.id.Edit_Username)
    EditText Edit_Username;

    @BindView(R.id.Edit_password)
    EditText Edit_password;

    @BindView(R.id.Edit_confirmedPassword)
    EditText Edit_confirmedPassword;

    @BindView(R.id.radioGenderGroup)
    RadioGroup radioGenderGroup;

    @BindView(R.id.Faculties_spinner)
    Spinner Faculties_spinner;

    @BindView(R.id.btnUpload)
    GenerateProcessButton btnUpload;

//    @BindView(R.id.btnRegister)
//    ActionProcessButton actionProcessButtonRegister;
    private String FirstName;
    private String LastName;
    private String Email;
    private String UserName;
    private String Password;
    private RadioButton checkedRadioButtion;
    private String selectedGender;
    private String DepartmentName;
    private String PersonName;
    private ProgressGenerator progressGenerator;
    private SessionManagement sessionManagement;
    private String DepartmentID;

    VerifyConnection verifyConnection;
    private String ConfirmPassword;
    private String EmailConst;
    private String FinalEmail;
    private String mToken;
    private String UserID;
    private FirebaseUserHandler firebaseUserHandler;
    private FirebaseEntites firebaseEntities;
    private DatabaseReference mDatabase;
    private String Users_KEY ="users";
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    private String FirebaseUiD;

    @Override
    protected void onResume() {
        super.onResume();
        if (verifyConnection.isConnected()){
            RetrieveDepartmentsAsyncTask retrieveDepartmentsAsyncTask= new RetrieveDepartmentsAsyncTask(this, getApplicationContext());
            retrieveDepartmentsAsyncTask.execute(URL);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taibah_auth);
        setTheme(R.style.ArishTheme);
        ButterKnife.bind(this);
        verifyConnection=new VerifyConnection(getApplicationContext());
        sessionManagement= new SessionManagement(getApplicationContext());
        firebaseAuth= FirebaseAuth.getInstance();
        if (mDatabase==null){
            FirebaseDatabase database= FirebaseDatabase.getInstance();
            mDatabase=database.getReference(Users_KEY);
//            mDatabase.keepSynced(true);
        }


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpload.setEnabled(false);
                FirstName= Edit_first_name.getText().toString();
                LastName=Edit_last_name.getText().toString();
                PersonName=FirstName+" "+LastName;
                EmailConst=email_type.getText().toString().trim();
                Email=Edit_email.getText().toString().trim();
                FinalEmail=Email+EmailConst;
                Config.FinalEmail=FinalEmail;
                UserName=Edit_Username.getText().toString().trim();
                Config.UserName=UserName;
                Password=Edit_password.getText().toString().trim();
                Config.Password=Password;
                ConfirmPassword=Edit_confirmedPassword.getText().toString();
                checkedRadioButtion=(RadioButton)radioGenderGroup.findViewById(radioGenderGroup.getCheckedRadioButtonId());
                selectedGender= checkedRadioButtion.getText().toString();
                if (verifyConnection.isConnected()){
                    signUpFirebase();
                }
            }
        });

        Faculties_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DepartmentName = Config.FacultiesList.get(position).getDepartmentName();
                DepartmentID = Config.FacultiesList.get(position).getDepartmentID();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onDepartmentsRetrievalApiTaskCompleted(ArrayList<StudentsEntity> result) {
        if (result.size() > 0) {
            Config.FacultiesList=result;
            FacultiesSpinnerAdapter customSpinnerAdapterFaculties = new FacultiesSpinnerAdapter(getApplicationContext(), result);
            Faculties_spinner.setAdapter(customSpinnerAdapterFaculties);
        }
    }


    @Override
    public void onComplete(ArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities!=null){
            if (studentsEntities.size()>0){
                for (StudentsEntity studentsEntity:studentsEntities){
                    if (studentsEntity.getException()!=null){
                        Toast.makeText(getApplicationContext(), studentsEntity.getException().toString(), Toast.LENGTH_LONG).show();
                    }else {
                        DoneSignUp(studentsEntities);
                    }
                }
            }
        }
    }

    private void signUpFirebase() {
        firebaseAuth.createUserWithEmailAndPassword(Config.FinalEmail,Config.Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                            String userID=firebaseUser.getUid();
                            Config.FirebaseUserID=userID;
                            reference=FirebaseDatabase.getInstance().getReference("yusor-chat").child("Users").child(userID);
                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("id",userID);
                            hashMap.put("userName",Config.FinalEmail);
                            hashMap.put("imageUrl","default");
                            hashMap.put("status","offline");
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        progressGenerator = new ProgressGenerator((ProgressGenerator.OnCompleteListener)TaibahRegistrationActivity.this, getApplicationContext());
                                        progressGenerator.startSignUp(btnUpload, PersonName, FinalEmail, UserName, Password, ConfirmPassword, selectedGender, DepartmentID,Config.FirebaseUserID );
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                            Log.e(LOG_TAG, "Error ******** Error reason : "+ task.getException());
                        }
                    }
                });
    }

    private void DoneSignUp(ArrayList<StudentsEntity> studentsEntities) {
        for (StudentsEntity studentsEntity: studentsEntities){
            PersonName= studentsEntity.getPersonName();
            Email=studentsEntity.getEmail();
            UserName=studentsEntity.getUserName();
            selectedGender=studentsEntity.getGender();
            mToken=studentsEntity.getAPI_TOKEN();
            UserID=studentsEntity.getUserID();
            FirebaseUiD=studentsEntity.getFirebaseUiD();
//            DepartmentName=studentsEntity.getDepartmentName();
            //send authenticated user to firebase database
//            firebaseUserHandler =new FirebaseUserHandler(UserID,mToken,selectedGender,show_message,Email,PersonName);
//            firebaseEntities=new FirebaseEntites(mDatabase);
//            firebaseEntities.AddUser(mDatabase,firebaseUserHandler);
        }
        sessionManagement.createYusorLoginSession(mToken,PersonName,Email,UserName,selectedGender, DepartmentName,UserID,FirebaseUiD);
        sessionManagement.createLoginSessionType("EP");
        Intent intent_create=new Intent(TaibahRegistrationActivity.this,MainActivity.class);
        startActivity(intent_create);
        finish();
    }
}