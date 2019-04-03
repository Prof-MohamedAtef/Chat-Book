package mo.ed.prof.yusor.Activities.Authentication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

import bolts.Task;
import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.R;

public class ResetPassActivity extends AppCompatActivity {


    @BindView(R.id.email)
    EditText inputEmail;


    @BindView(R.id.btn_reset_password)
    Button BTN_RESTPASS;

    @BindView(R.id.btn_back)
    Button btn_back;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private FirebaseAuth auth;


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
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPassActivity.this, getResources().getString(R.string.reset_instructions_sent), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ResetPassActivity.this, getResources().getString(R.string.reset_instructions_sent_failed), Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
}
