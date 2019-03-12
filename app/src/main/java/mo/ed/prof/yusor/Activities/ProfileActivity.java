package mo.ed.prof.yusor.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.SessionManagement;

public class ProfileActivity extends AppCompatActivity {

    private SessionManagement sessionManagement;
    private HashMap<String, String> user;
    private String LoggedEmail;
    private String LoggedUserName;
    private String LoggedProfilePic;
    private String TokenID;
    private TextView EmailText;
    private TextView UserNameText;
    private ImageView ProfilePicView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        EmailText=(TextView)findViewById(R.id.Email);
        UserNameText=(TextView)findViewById(R.id.UserName);
        ProfilePicView=(ImageView)findViewById(R.id.profile_image);
        sessionManagement=new SessionManagement(getApplicationContext());
        user=sessionManagement.getUserDetails();
        if (user!=null){
            LoggedEmail = user.get(SessionManagement.KEY_EMAIL);
            LoggedUserName=user.get(SessionManagement.KEY_NAME);
            LoggedProfilePic=user.get(SessionManagement.KEY_Profile_Pic);
            TokenID=user.get(SessionManagement.KEY_idToken);
            if (LoggedEmail!=null){
                EmailText.setText(LoggedEmail);
            }
            if (LoggedUserName!=null){
                UserNameText.setText(LoggedUserName);
            }
            if (LoggedProfilePic!=null){
                Picasso.with(getApplicationContext()).load(LoggedProfilePic)
                        .error(R.drawable.web_hi_res_512)
                        .into(ProfilePicView);
            }
        }
    }
}
