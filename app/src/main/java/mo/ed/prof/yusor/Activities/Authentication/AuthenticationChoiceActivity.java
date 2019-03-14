package mo.ed.prof.yusor.Activities.Authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Activities.TaibahRegistrationActivity;
import mo.ed.prof.yusor.R;

public class AuthenticationChoiceActivity extends AppCompatActivity {


    @BindView(R.id.btn_sign_in)
    Button btn_sign_in;

    @BindView(R.id.btn_Sign_up)
    Button btn_Sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_choice);
        ButterKnife.bind(this);
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_Registeration=new Intent(AuthenticationChoiceActivity.this,LoginActivity.class);
                startActivity(intent_Registeration);
            }
        });

        btn_Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_Login=new Intent(AuthenticationChoiceActivity.this,TaibahRegistrationActivity.class);
                startActivity(intent_Login);
            }
        });
    }
}