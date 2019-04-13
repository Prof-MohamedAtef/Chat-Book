package mo.ed.prof.yusor.Activities.Authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Activities.MainActivity;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.Volley.MakeVolleyRequests;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

public class ResetCodeActivity extends AppCompatActivity implements MakeVolleyRequests.OnCompleteListener{


    @BindView(R.id.code)
    EditText codeEdit;

    @BindView(R.id.btn_verify_reset_code)
    Button verifyResetCodeBTN;

    @BindView(R.id.btn_back)
    Button backBTN;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private MakeVolleyRequests makeVolleyRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_code);
        ButterKnife.bind(this);

        verifyResetCodeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code= codeEdit.getText().toString().trim();

                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(getApplication(), getResources().getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    makeVolleyRequest=new MakeVolleyRequests(getApplicationContext(), (MakeVolleyRequests.OnCompleteListener) ResetCodeActivity.this);
                    makeVolleyRequest.sendResetCode(code);
                }
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                        CodeVerified(studentsEntities);
                    }
                }
            }
        }
    }

    private void CodeVerified(ArrayList<StudentsEntity> studentsEntities) {
        if (studentsEntities!=null){
            if (studentsEntities.size()>0){
                for (StudentsEntity studentsEntity:studentsEntities){
                    String studentID= studentsEntity.getStudentID();
                    Intent intent_create = new Intent(ResetCodeActivity.this, PasswordResetActivity.class);
                    intent_create.putExtra("studentID", studentID);
                    startActivity(intent_create);
                    finish();
                }
            }
        }
    }
}
