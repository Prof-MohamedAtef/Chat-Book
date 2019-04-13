package mo.ed.prof.yusor.Activities.Authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dd.processbutton.iml.GenerateProcessButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.Volley.MakeVolleyRequests;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

public class PasswordResetActivity extends AppCompatActivity implements MakeVolleyRequests.OnCompleteListener{

    @BindView(R.id.pass1)
    EditText pass1;

    @BindView(R.id.pass2)
    EditText pass2;

    @BindView(R.id.btn_reset_password)
    GenerateProcessButton ResetBTN;
    private MakeVolleyRequests makeVolleyRequest;
    private String studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        ButterKnife.bind(this);

        Intent intent= getIntent();
        if (intent!=null){
            studentID= intent.getStringExtra("studentID");
        }

        ResetBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass_1= pass1.getText().toString().trim();
                String pass_2= pass2.getText().toString().trim();
                if (TextUtils.isEmpty(pass_1)||TextUtils.isEmpty(pass_2)) {
                    if (TextUtils.isEmpty(pass_1)) {
                        Toast.makeText(getApplication(), getResources().getString(R.string.enter_pass_1), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(pass_2)) {
                        Toast.makeText(getApplication(), getResources().getString(R.string.enter_pass_2), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else {
                    makeVolleyRequest=new MakeVolleyRequests(getApplicationContext(), (MakeVolleyRequests.OnCompleteListener) PasswordResetActivity.this);
                    makeVolleyRequest.ResetPassConfirmation(pass_1, pass_2, studentID);
                }
            }
        });
    }

    @Override
    public void onComplete(ArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities != null) {
            if (studentsEntities.size() > 0) {
                for (StudentsEntity studentsEntity : studentsEntities) {
                    if (studentsEntity.getException() != null) {
                        Toast.makeText(getApplicationContext(), studentsEntity.getException().toString(), Toast.LENGTH_LONG).show();
                    } else {
                        PassResetDone(studentsEntities);
                    }
                }
            }
        }
    }

    private void PassResetDone(ArrayList<StudentsEntity> studentsEntities) {
        Intent intent_create = new Intent(PasswordResetActivity.this, LoginActivity.class);
        startActivity(intent_create);
        finish();
    }
}
