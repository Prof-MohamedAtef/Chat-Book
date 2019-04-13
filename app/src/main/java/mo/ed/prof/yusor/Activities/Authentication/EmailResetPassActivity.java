package mo.ed.prof.yusor.Activities.Authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Activities.MainActivity;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.Volley.MakeVolleyRequests;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

public class EmailResetPassActivity extends AppCompatActivity implements MakeVolleyRequests.OnCompleteListener{


    @BindView(R.id.email)
    EditText inputEmail;


    @BindView(R.id.btn_reset_password)
    Button BTN_RESTPASS;

    @BindView(R.id.btn_back)
    Button btn_back;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private FirebaseAuth auth;
    private MakeVolleyRequests makeVolleyRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        BTN_RESTPASS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), getResources().getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    makeVolleyRequest=new MakeVolleyRequests(getApplicationContext(), (MakeVolleyRequests.OnCompleteListener) EmailResetPassActivity.this);
                    makeVolleyRequest.sendResetEmail(email);
                }

                progressBar.setVisibility(View.VISIBLE);
                // via mysql


                /*

                 */
//                firebase
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(EmailResetPassActivity.this, getResources().getString(R.string.reset_instructions_sent), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(EmailResetPassActivity.this, getResources().getString(R.string.reset_instructions_sent_failed), Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
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
                        EmailSent();
                    }
                }
            }
        }
    }

    private void EmailSent() {
        Intent intent_create = new Intent(EmailResetPassActivity.this, ResetCodeActivity.class);
        startActivity(intent_create);
        finish();
    }
}
